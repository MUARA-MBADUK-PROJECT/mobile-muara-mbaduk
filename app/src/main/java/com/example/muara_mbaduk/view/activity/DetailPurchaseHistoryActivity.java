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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.remote.PaymentServiceApi;
import com.example.muara_mbaduk.data.remote.ReviewsServiceApi;
import com.example.muara_mbaduk.model.entity.HistoryPayment;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;
import com.example.muara_mbaduk.model.response.HistoryResponse;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.fragment.DetailOrderFragment;
import com.example.muara_mbaduk.view.fragment.OrderFragment;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPurchaseHistoryActivity extends AppCompatActivity {

    OrderFragment orderFragment;
    DetailOrderFragment detailOrderFragment;
    FrameLayout frameLayout;
    LinearLayout kodeOrderLayout, btnOderLayout;
    ImageView barcodeImageView;
    Button detailPemesananButton, checkStatusPembayaranInDetailButton, check_status_pembayaran_button;
    PaymentCheckout paymentCheckout;
    Toolbar tolbar;
    TextView orderIdTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_purchase);
        initComponents();
        initFunction();




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
        tolbar = findViewById(R.id.detail_ticket_activity_toolbar);
        orderIdTextView = findViewById(R.id.order_id_textview);
        check_status_pembayaran_button = findViewById(R.id.check_status_pembayaran_button);


    }
    // all fucntion in here
    public void initFunction() {
        ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("tunggu sebentar", this);
        progresIndicator.show();
        PaymentServiceApi serviceApi = RetrofitClient.getInstance().create(PaymentServiceApi.class);
        Call<HistoryResponse> responseCall = serviceApi.findDetailPayment(RetrofitClient.getApiKey(), paymentCheckout.getId());
        orderIdTextView.setText(paymentCheckout.getOrder_id().toUpperCase());
        responseCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {

                HistoryPayment data = response.body().getData();
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail-history", data);
                ReviewsServiceApi serviceApi = RetrofitClient.getInstance().create(ReviewsServiceApi.class);
                Call<ReviewResponse> responseCall = serviceApi.findByPayment(RetrofitClient.getApiKey(), paymentCheckout.getId());
                // hit api review
                detailOrderFragment = new DetailOrderFragment();
                orderFragment = new OrderFragment(paymentCheckout);
                responseCall.enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        progresIndicator.dismiss();
                        if(response.isSuccessful()){
                            ReviewResponse body = response.body();
                            bundle.putSerializable("review" , body);
                            detailOrderFragment.setArguments(bundle);
                            orderFragment.setArguments(bundle);
                            FragmentTransaction replace
                                    = getSupportFragmentManager().beginTransaction().replace(R.id.frame_order, orderFragment);
                            kodeOrderLayout.setVisibility(View.GONE);
                            replace.commit();
                        }
                    }
                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        progresIndicator.dismiss();
                        Log.e("review", "onFailure: " + t.getMessage() );
                        UtilMethod.genereateErrorsSnackbar(getCurrentFocus() , getApplicationContext(), t.getMessage());
                    }
                });

            }



            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                progresIndicator.dismiss();
                Log.e("error", "onFailure: " + t.getMessage());
                UtilMethod.genereateErrorsSnackbar(getCurrentFocus() , getApplicationContext(), t.getMessage());
            }
        });

        Picasso.get().load(paymentCheckout.getBarcode()).into(barcodeImageView);
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

        tolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        check_status_pembayaran_button.setOnClickListener(v -> {

        });
    }
}