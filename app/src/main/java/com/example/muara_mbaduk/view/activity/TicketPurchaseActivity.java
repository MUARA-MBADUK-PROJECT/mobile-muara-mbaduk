package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.remote.SyaratDanKetentuanServiceApi;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuan2Response;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.fragment.DateAndCategoryCampFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketPurchaseActivity extends AppCompatActivity {
    DateAndCategoryCampFragment dateAndCategoryCampFragment
            = new DateAndCategoryCampFragment();
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    TextView ppnTextView , skTextView;
    Button berikutnyaBtn;
    Dialog dialog;

    Button agreeTermsBtn;


    SyaratDanKetentuanServiceApi serviceApi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sk2);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(dialog1 -> {
            Intent intent = new Intent(TicketPurchaseActivity.this , HomeActivity.class);
            startActivity(intent);
        });
        skTextView = dialog.findViewById(R.id.sk1_textview);
        agreeTermsBtn = dialog.findViewById(R.id.sk1_button);
        serviceApi = RetrofitClient.getInstance().create(SyaratDanKetentuanServiceApi.class);
        Call<SyaratDanKetentuan2Response> responseCall = serviceApi.getSk2(RetrofitClient.getApiKey());
        responseCall.enqueue(new Callback<SyaratDanKetentuan2Response>() {
            @Override
            public void onResponse(Call<SyaratDanKetentuan2Response> call, Response<SyaratDanKetentuan2Response> response) {
                SyaratDanKetentuan2Response body = response.body();
                System.out.println(body.getData().getBody());
                if(response.isSuccessful()){
                    skTextView.setText(Html.fromHtml(response.body().getData().getBody()));
                    dialog.show();
                    agreeTermsBtn.setOnClickListener(v1 -> {
                        dialog.dismiss();
                    });
                }else{
                    Snackbar snackbar = UtilMethod.genereateErrorsSnackbar(getCurrentFocus(), getApplicationContext(), "Gagal mengambil Syarat Dan ketentuan");
                    snackbar.show();
                }
            }
            @Override
            public void onFailure(Call<SyaratDanKetentuan2Response> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        setContentView(R.layout.activity_ticket_purchase);
        ppnTextView = findViewById(R.id.ppn_textView);
        ppnTextView.setVisibility(View.GONE);
        toolbar = findViewById(R.id.app_ticket_purchase_toolbar);
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame_fragment_ticket_purchase, dateAndCategoryCampFragment, "dateAndCategoryCampFragment").commit();
    }
    public Button getBtnBerikutnya() {
        return findViewById(R.id.btn_berikutnya);
    }
    public Toolbar getToolBar() {
        return findViewById(R.id.app_ticket_purchase_toolbar);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}