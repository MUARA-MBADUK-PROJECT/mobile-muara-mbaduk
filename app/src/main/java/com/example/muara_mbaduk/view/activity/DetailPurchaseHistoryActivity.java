package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;
import com.example.muara_mbaduk.view.fragment.DetailOrderFragment;
import com.example.muara_mbaduk.view.fragment.OrderFragment;

import java.io.Serializable;

public class DetailPurchaseHistoryActivity extends AppCompatActivity {


    OrderFragment orderFragment;
    DetailOrderFragment detailOrderFragment;
    FrameLayout frameLayout;
    LinearLayout kodeOrderLayout, btnOderLayout;
    Button detailPemesananButton,checkStatusPembayaranInDetailButton;
    PaymentCheckout paymentCheckout;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_purchase);

        detailOrderFragment = new DetailOrderFragment();
        btnOderLayout = findViewById(R.id.layout_btn_order);
        frameLayout = findViewById(R.id.frame_order);
        checkStatusPembayaranInDetailButton = findViewById(R.id.check_status_pembayaran_btn_indetail);
        detailPemesananButton = findViewById(R.id.detail_pesanan_button);
        kodeOrderLayout = findViewById(R.id.kode_order_in_detailOrder);

        Bundle bundle = getIntent().getExtras();
        paymentCheckout = (PaymentCheckout) bundle.getSerializable("checkoutData");
        orderFragment = new OrderFragment(paymentCheckout);
        FragmentTransaction replace
                = getSupportFragmentManager().beginTransaction().replace(R.id.frame_order, orderFragment);
        kodeOrderLayout.setVisibility(View.GONE);
        replace.commit();
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
}