package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.DetailKendaraanAdapter;
import com.example.muara_mbaduk.data.adapter.DetailWisatawanAdapter;
import com.example.muara_mbaduk.data.dto.DataOrder;
import com.example.muara_mbaduk.data.dto.PackageOrder;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.data.remote.PaymentServiceApi;
import com.example.muara_mbaduk.model.Errors;
import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;
import com.example.muara_mbaduk.model.request.CheckoutTicketRequest;
import com.example.muara_mbaduk.model.response.PaymentCheckoutResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembeliActivity extends AppCompatActivity {

    private RecyclerView recyclerView, kendaraanRecycleView;
    private RadioGroup radioGroup;
    private RadioButton bankRadioButton, bayarDitempatRadioButton, bniRadioButton, bcaRadioButton, briRadioButton;
    ImageView imageRadioBank, imageRadioBayarDitempat;
    private LinearLayout bankLinearLayout, bayarDitempatLinearLayout;
    private RelativeLayout bniRelativeLayout, briRelativeLayout, bcaRelativeLayout;
    private RadioGroup bankRadioGroup;
    private Dialog bankPaymentDialog;
    private ImageView bniImage, briImage, bcaImage;
    private TextView totalBayarTextView;
    private String paymentMetode;
    private Button berikutnyaBtn;
    private boolean isCamping;
    private String date;
    private UserModel userModel;
    RealmHelper realmHelper;
    private PaymentServiceApi paymentServiceApi;
    Toolbar toolbar;
    View view;
    DetailWisatawanAdapter detailWisatawanAdapter;
    DetailKendaraanAdapter detailKendaraanAdapter;
    PackageOrder packageOrder;


    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);
        initComponents();
        iniFunction();
        initDialog();
        dialogAction();
        paymentServiceApi = RetrofitClient.getInstance().create(PaymentServiceApi.class);
        paymentMetode = "";
        date = "";
        realmHelper = new RealmHelper(Realm.getDefaultInstance());
        SharedPreferences sh = getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        userModel = realmHelper.findByJwt(jwt);

        Bundle bundle = getIntent().getExtras();
        int total_bayar = bundle.getInt("total_bayar");
        isCamping = bundle.getBoolean("iscamping");
        date = bundle.getString("date");
        totalBayarTextView.setText("Rp." + total_bayar);
        view = findViewById(R.id.detail_relative_layout);
        DataOrder myObject = (DataOrder) bundle.getSerializable("wisatawan");
        packageOrder = (PackageOrder) bundle.getSerializable("package");
        detailWisatawanAdapter = new DetailWisatawanAdapter(myObject);
        recyclerView.setAdapter(detailWisatawanAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        int size = getIntent().getExtras().getInt("size");

        bankLinearLayout.setOnClickListener(v -> {
            bankRadioButton.setSelected(true);
            bayarDitempatRadioButton.setSelected(false);
            imageRadioBank.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            imageRadioBayarDitempat.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bankPaymentDialog.show();
        });
        bayarDitempatLinearLayout.setOnClickListener(v -> {
            bankRadioButton.setSelected(false);
            bayarDitempatRadioButton.setSelected(true);
            imageRadioBank.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            imageRadioBayarDitempat.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            paymentMetode = bayarDitempatRadioButton.getText().toString();
            System.out.println(paymentMetode);
        });
        detailKendaraanAdapter = new DetailKendaraanAdapter(size, myObject);
        LinearLayoutManager linearLayoutKendaraan  = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        kendaraanRecycleView.setAdapter(detailKendaraanAdapter);
        kendaraanRecycleView.setLayoutManager(linearLayoutKendaraan);
        bankPaymentDialog.setOnDismissListener(dialog -> {
            if(briRadioButton.isChecked()){
                paymentMetode = briRadioButton.getText().toString();
            } else if (bniRadioButton.isChecked()) {
                paymentMetode = bniRadioButton.getText().toString();
            } else if (bcaRadioButton.isChecked()) {
                paymentMetode = bcaRadioButton.getText().toString();
            }
            System.out.println(paymentMetode);
        });
    }

    private void iniFunction() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // menampilkan tombol kembali di toolbar
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        berikutnyaBtn.setOnClickListener(v -> {
            DataOrder dataOrder = detailWisatawanAdapter.getDataOrder();
            DataOrder dataOrder1 = detailKendaraanAdapter.getDataOrder();
            List<TiketPurchaseRequest> deque = dataOrder.getDeque();
            List<TiketPurchaseRequest> dataMobil = dataOrder1.getDataMobil();
            List<TiketPurchaseRequest> dataMotor = dataOrder1.getDataMotor();

            List<TiketPurchaseRequest> allTiket = new ArrayList<>();
            allTiket.addAll(deque);
            allTiket.addAll(dataMobil);
            allTiket.addAll(dataMotor);
            // TODO: 5/18/23 validasi data detail wisatawan
            boolean isValid = false;
            for (TiketPurchaseRequest request : deque) {
                if (!request.getName().equalsIgnoreCase("belum ada nama") && !request.getIdentity().equalsIgnoreCase("belum ada identitas")) {
                    isValid = true;
                }
            }
            for (TiketPurchaseRequest request : dataMobil) {
                if (!request.getIdentity().equalsIgnoreCase("belum ada plat nomor")) {
                    isValid = true;
                } else {
                    isValid = false;
                }
            }
            for (TiketPurchaseRequest request : dataMotor) {
                if (!request.getIdentity().equalsIgnoreCase("belum ada plat nomor")) {
                    isValid = true;
                } else {
                    isValid = false;
                }
            }
            if (isValid) {
                ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("Harap tunggu sebentar", this);
                progresIndicator.show();
                if (paymentMetode.equalsIgnoreCase("")) {
                    Toast.makeText(this, "Harap pilih metode pembayaran", Toast.LENGTH_SHORT).show();
                } else {
                    CheckoutTicketRequest checkoutTicketRequest = new CheckoutTicketRequest(userModel.getId(),
                            date,
                            isCamping,
                            paymentMetode.equalsIgnoreCase("Bayar di tempat") ? null : paymentMetode.toLowerCase(),
                            packageOrder.getPackagePurchaseRequests(),
                            allTiket);
                    if (paymentMetode.equalsIgnoreCase("Bayar di tempat")) {
                        Call<PaymentCheckoutResponse> responseCall = paymentServiceApi.checkoutCash(RetrofitClient.getApiKey(), checkoutTicketRequest);
                        responseCall.enqueue(new Callback<PaymentCheckoutResponse>() {
                            @Override
                            public void onResponse(Call<PaymentCheckoutResponse> call, Response<PaymentCheckoutResponse> response) {
                                progresIndicator.dismiss();
                                if (response.isSuccessful()) {
                                    Snackbar snackbar = Snackbar.make(view, "Berhasil Membuat Transaksi", Snackbar.LENGTH_SHORT);
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundAppBar));
                                    snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                    snackbar.show();
                                    final Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //close all activity after 2 seconds
                                            Intent intent = new Intent(DetailPembeliActivity
                                                    .this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 2000);
                                } else {
                                    String errors = null;
                                    try {
                                        errors = response.errorBody().string();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Errors generateErrors = UtilMethod.generateErrors(errors);
                                    Snackbar snackbar = Snackbar.make(view, generateErrors.getErrors().getMessage(), Snackbar.LENGTH_SHORT);
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PaymentCheckoutResponse> call, Throwable t) {
                                progresIndicator.dismiss();
                                Snackbar snackbar = UtilMethod.genereateErrorsSnackbar(view, getApplicationContext(), t.getMessage());
                                snackbar.show();
                                Log.e("error", "onFailure: " + t.getMessage());
                            }
                        });
                    } else {
                        Call<PaymentCheckoutResponse> responseCall = paymentServiceApi.checkoutBank(RetrofitClient.getApiKey(), checkoutTicketRequest);
                        responseCall.enqueue(new Callback<PaymentCheckoutResponse>() {
                            @Override
                            public void onResponse(Call<PaymentCheckoutResponse> call, Response<PaymentCheckoutResponse> response) {
                                progresIndicator.dismiss();
                                if (response.isSuccessful()) {
                                    Snackbar snackbar = Snackbar.make(view, "Berhasil Membuat Transaksi", Snackbar.LENGTH_SHORT);
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundAppBar));
                                    snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                    snackbar.show();
                                    final Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //close all activity after 2 seconds
                                            Intent intent = new Intent(DetailPembeliActivity
                                                    .this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 2000);
                                } else {
                                    String errors = null;
                                    try {
                                        errors = response.errorBody().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Errors generateErrors = UtilMethod.generateErrors(errors);
                                    Snackbar snackbar = Snackbar.make(view, generateErrors.getErrors().getMessage(), Snackbar.LENGTH_SHORT);
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                    snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                    snackbar.show();
                                }
                            }
                            @Override
                            public void onFailure(Call<PaymentCheckoutResponse> call, Throwable t) {
                                Log.e("error", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                }
            } else {
                System.out.println("not valid request");
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDialog() {
        bankPaymentDialog = new Dialog(this);
        bankPaymentDialog.setContentView(R.layout.payment_dialog);
        bankRadioGroup = bankPaymentDialog.findViewById(R.id.payment_radioGroup);
        bniRelativeLayout = bankPaymentDialog.findViewById(R.id.bni_linearLayout);
        bcaRelativeLayout = bankPaymentDialog.findViewById(R.id.bca_linearLayout);
        briRelativeLayout = bankPaymentDialog.findViewById(R.id.bri_linearLayout);
        bcaRadioButton = bankPaymentDialog.findViewById(R.id.bca_radioButton);
        bniRadioButton = bankPaymentDialog.findViewById(R.id.bni_radioButton);
        briRadioButton = bankPaymentDialog.findViewById(R.id.bri_radioButton);
        briImage = bankPaymentDialog.findViewById(R.id.bri_image);
        bcaImage = bankPaymentDialog.findViewById(R.id.bca_image);
        bniImage = bankPaymentDialog.findViewById(R.id.bni_image);
        bniImage.setImageDrawable(getDrawable(R.drawable.checked_drawable));
        briImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
        bcaImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
        bankPaymentDialog.setCancelable(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void dialogAction(){
        bniRelativeLayout.setOnClickListener(v -> {
            bniImage.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            briImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bcaImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bniRadioButton.setChecked(true);
            bcaRadioButton.setChecked(false);
            briRadioButton.setChecked(false);
        });
        briRelativeLayout.setOnClickListener(v -> {
            briImage.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            bniImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bcaImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            briRadioButton.setChecked(true);
            bcaRadioButton.setChecked(false);
            bniRadioButton.setChecked(false);
        });
        bcaRelativeLayout.setOnClickListener(v -> {
            bcaImage.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            briImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bniImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bcaRadioButton.setChecked(true);
            bniRadioButton.setChecked(false);
            briRadioButton.setChecked(false);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // cek apakah tombol kembali diklik
            onBackPressed(); // navigasi kembali ke activity induk
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initComponents() {
        radioGroup = findViewById(R.id.radio_group);
        bankRadioButton = findViewById(R.id.radio_button_bank);
        imageRadioBank = findViewById(R.id.checked_bank_image);
        bayarDitempatRadioButton = findViewById(R.id.radio_button_bayar_ditempat);
        imageRadioBayarDitempat = findViewById(R.id.checked_bayar_ditempat_image);
        bankLinearLayout = findViewById(R.id.radio_layout_1);
        bayarDitempatLinearLayout = findViewById(R.id.radio_layout_2);
        toolbar = findViewById(R.id.detail_ticket_activity_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.detail_wisatawan_recycleview);
        kendaraanRecycleView = findViewById(R.id.detail_kendaraan_recycleview);
        totalBayarTextView = findViewById(R.id.jumlah_bayar_textView);
        berikutnyaBtn = findViewById(R.id.btn_berikutnya);
    }
}