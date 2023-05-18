package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.DetailKendaraanAdapter;
import com.example.muara_mbaduk.data.adapter.DetailWisatawanAdapter;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.data.remote.PaymentServiceApi;
import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;
import com.example.muara_mbaduk.model.request.CheckoutTicketRequest;
import com.example.muara_mbaduk.data.dto.DataOrder;
import com.example.muara_mbaduk.data.dto.PackageOrder;
import com.example.muara_mbaduk.model.response.PaymentCheckoutResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembeliActivity extends AppCompatActivity {

    private RecyclerView recyclerView , kendaraanRecycleView;
    private RadioGroup radioGroup;
    private RadioButton bankRadioButton , bayarDitempatRadioButton , bniRadioButton, bcaRadioButton,briRadioButton;
    ImageView imageRadioBank , imageRadioBayarDitempat;
    private LinearLayout bankLinearLayout , bayarDitempatLinearLayout;
    private RelativeLayout bniRelativeLayout, briRelativeLayout, bcaRelativeLayout;
    private RadioGroup bankRadioGroup;
    private Dialog bankPaymentDialog;
    private ImageView bniImage , briImage , bcaImage;
    private TextView totalBayarTextView;
    private String paymentMetode;
    private Button berikutnyaBtn;
    private  boolean isCamping;
    private String date;
    private UserModel userModel;
    RealmHelper realmHelper;
    private PaymentServiceApi paymentServiceApi;



    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        paymentServiceApi = RetrofitClient.getInstance().create(PaymentServiceApi.class);
        paymentMetode = "";
        date = "";
        realmHelper = new RealmHelper(Realm.getDefaultInstance());
        SharedPreferences sh = getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        userModel = realmHelper.findByJwt(jwt);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);
        Toolbar toolbar = findViewById(R.id.detail_ticket_activity_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // menampilkan tombol kembali di toolbar
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        recyclerView = findViewById(R.id.detail_wisatawan_recycleview);
        kendaraanRecycleView = findViewById(R.id.detail_kendaraan_recycleview);
        totalBayarTextView = findViewById(R.id.jumlah_bayar_textView);
        berikutnyaBtn = findViewById(R.id.btn_berikutnya);
        Bundle bundle = getIntent().getExtras();
        int total_bayar = bundle.getInt("total_bayar");
        isCamping = bundle.getBoolean("iscamping");
        date = bundle.getString("date");
        totalBayarTextView.setText("Rp."+ total_bayar);
        DataOrder myObject = (DataOrder) bundle.getSerializable("wisatawan");
        PackageOrder packageOrder = (PackageOrder) bundle.getSerializable("package");
        DetailWisatawanAdapter detailWisatawanAdapter = new DetailWisatawanAdapter(myObject);
        recyclerView.setAdapter(detailWisatawanAdapter);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        int size = getIntent().getExtras().getInt("size");
        List<TiketPurchaseRequest> motor = myObject.getDataMotor();
        List<TiketPurchaseRequest> mobil = myObject.getDataMobil();
        radioGroup = findViewById(R.id.radio_group);
        bankRadioButton = findViewById(R.id.radio_button_bank);
        imageRadioBank = findViewById(R.id.checked_bank_image);
        bayarDitempatRadioButton = findViewById(R.id.radio_button_bayar_ditempat);
        imageRadioBayarDitempat = findViewById(R.id.checked_bayar_ditempat_image);
        bankLinearLayout = findViewById(R.id.radio_layout_1);
        bayarDitempatLinearLayout = findViewById(R.id.radio_layout_2);

        initDialog();
        dialogAction();


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
        DetailKendaraanAdapter detailKendaraanAdapter = new DetailKendaraanAdapter(size ,myObject);
        LinearLayoutManager linearLayoutKendaraan  = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        kendaraanRecycleView.setAdapter(detailKendaraanAdapter);
        kendaraanRecycleView.setLayoutManager(linearLayoutKendaraan);
        bankPaymentDialog.setOnDismissListener(dialog -> {
           if(briRadioButton.isChecked()){
               paymentMetode = briRadioButton.getText().toString();
           }else if(bniRadioButton.isChecked()){
               paymentMetode = bniRadioButton.getText().toString();
           }else if(bcaRadioButton.isChecked()){
               paymentMetode = bcaRadioButton.getText().toString();
           }
            System.out.println(paymentMetode);
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
                if(!request.getName().equalsIgnoreCase("belum ada nama") && !request.getIdentity().equalsIgnoreCase("belum ada identitas")){
                    isValid = true;
                }
            }
            for (TiketPurchaseRequest request : dataMobil) {
                if(! request.getIdentity().equalsIgnoreCase("belum ada plat nomor")){
                    isValid = true;
                }else {
                    isValid = false;
                }
            }
            for (TiketPurchaseRequest request : dataMotor) {
                if(! request.getIdentity().equalsIgnoreCase("belum ada plat nomor")){
                    isValid = true;
                }else{
                    isValid = false;
                }
            }
            if(isValid){
                ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("Harap tunggu sebentar", this);
                progresIndicator.show();
                if(paymentMetode.equalsIgnoreCase("")){
                    Toast.makeText(this, "Harap pilih metode pembayaran", Toast.LENGTH_SHORT).show();
                }else{
                    CheckoutTicketRequest checkoutTicketRequest = new CheckoutTicketRequest();
                    checkoutTicketRequest.setTickets(allTiket);
                    checkoutTicketRequest.setCamping(isCamping);
                    checkoutTicketRequest.setDate(date);
                    checkoutTicketRequest.setBank(paymentMetode);
                    checkoutTicketRequest.setPackages(packageOrder.getPackagePurchaseRequests());
                    checkoutTicketRequest.setUser_id(userModel.getId());
                    if(paymentMetode.equalsIgnoreCase("Bayar di tempat")){
                        checkoutTicketRequest.setBank(null);
                        Call<PaymentCheckoutResponse> responseCall = paymentServiceApi.checkoutCash(RetrofitClient.getApiKey(), checkoutTicketRequest);

                        responseCall.enqueue(new Callback<PaymentCheckoutResponse>() {
                            @Override
                            public void onResponse(Call<PaymentCheckoutResponse> call, Response<PaymentCheckoutResponse> response) {
                                progresIndicator.dismiss();
                                if(response.isSuccessful()){
                                    System.out.println(response.body().getData().getOrder_id());
                                }else{
                                    System.out.println(response.body().getCode());
                                }
                            }
                            @Override
                            public void onFailure(Call<PaymentCheckoutResponse> call, Throwable t) {
                                Log.e("error", "onFailure: " + t.getMessage());
                            }
                        });
                    }else{
                        Call<PaymentCheckoutResponse> responseCall = paymentServiceApi.checkoutBank(RetrofitClient.getApiKey(), checkoutTicketRequest);
                        responseCall.enqueue(new Callback<PaymentCheckoutResponse>() {
                            @Override
                            public void onResponse(Call<PaymentCheckoutResponse> call, Response<PaymentCheckoutResponse> response) {
                                progresIndicator.dismiss();
                                if(response.isSuccessful()){
                                    System.out.println(response.body().getData().getOrder_id());
                                }else{
                                    System.out.println(response.body().getCode());
                                }
                            }
                            @Override
                            public void onFailure(Call<PaymentCheckoutResponse> call, Throwable t) {
                                Log.e("error", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                }
            }else{
                System.out.println("not valid request");
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDialog(){
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
}