package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.remote.PaymentServiceApi;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;
import com.example.muara_mbaduk.model.response.HistoryResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.fragment.DetailOrderFragment;
import com.example.muara_mbaduk.view.fragment.OrderFragment;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPurchaseHistoryActivity extends AppCompatActivity {


    OrderFragment orderFragment;
    DetailOrderFragment detailOrderFragment;
    FrameLayout frameLayout;
    LinearLayout kodeOrderLayout, btnOderLayout;
    ImageView barcodeImageView;
    Button detailPemesananButton,checkStatusPembayaranInDetailButton;
    PaymentCheckout paymentCheckout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_purchase);
        initComponents();
        ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("tunggu sebentar", this);
        progresIndicator.show();
        PaymentServiceApi serviceApi = RetrofitClient.getInstance().create(PaymentServiceApi.class);
        Call<HistoryResponse> responseCall = serviceApi.findDetailPayment(RetrofitClient.getApiKey(), paymentCheckout.getId());
        responseCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                progresIndicator.dismiss();
                detailOrderFragment = new DetailOrderFragment(response.body().getData());
                orderFragment = new OrderFragment(paymentCheckout,response.body().getData());
                FragmentTransaction replace
                        = getSupportFragmentManager().beginTransaction().replace(R.id.frame_order, orderFragment);
                kodeOrderLayout.setVisibility(View.GONE);
                replace.commit();
            }
            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                progresIndicator.dismiss();
                Log.e("error", "onFailure: "+t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.get().load(paymentCheckout.getBarcode()).into(barcodeImageView);
//        go to detail pesanan
        detailPemesananButton.setOnClickListener(v -> {
            kodeOrderLayout.setVisibility(View.VISIBLE);
            btnOderLayout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.frame_order, detailOrderFragment).commit();
        });
        checkStatusPembayaranInDetailButton.setOnClickListener(v -> {
            btnOderLayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_order, orderFragment).commit();
            kodeOrderLayout.setVisibility(View.GONE);
        });
    }

    public void initComponents(){
        btnOderLayout = findViewById(R.id.layout_btn_order);
        frameLayout = findViewById(R.id.frame_order);
        checkStatusPembayaranInDetailButton = findViewById(R.id.check_status_pembayaran_btn_indetail);
        detailPemesananButton = findViewById(R.id.detail_pesanan_button);
        kodeOrderLayout = findViewById(R.id.kode_order_in_detailOrder);
        barcodeImageView = findViewById(R.id.barcode_imageview);
        Bundle bundle = getIntent().getExtras();
        paymentCheckout = (PaymentCheckout) bundle.getSerializable("checkoutData");
    }
}