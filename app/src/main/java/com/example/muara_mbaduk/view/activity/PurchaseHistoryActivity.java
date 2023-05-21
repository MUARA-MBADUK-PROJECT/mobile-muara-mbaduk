package com.example.muara_mbaduk.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.PurchaseHistoryAdapter;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.data.remote.PaymentServiceApi;
import com.example.muara_mbaduk.model.response.PaymentHistoryResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.google.android.material.snackbar.Snackbar;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    private PaymentServiceApi paymentServiceApi;
    RealmHelper realmHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentServiceApi = RetrofitClient.getInstance().create(PaymentServiceApi.class);
        realmHelper = new RealmHelper(Realm.getDefaultInstance());

        setContentView(R.layout.activity_purchase_history);
        View view = findViewById(R.id.layout_history_payment);
        toolbar = findViewById(R.id.detail_ticket_activity_toolbar);
        recyclerView = findViewById(R.id.history_purchase_recycleview);
        String jwt = UtilMethod.getJwt(this);
        UserModel userModel = realmHelper.findByJwt(jwt);
        Call<PaymentHistoryResponse> responseCall = paymentServiceApi.findAllPayment(RetrofitClient.getApiKey(), userModel.getId());
        ProgressDialog progressDialog = UtilMethod.getProgresIndicator("Harap tunggu", this);
        progressDialog.show();
        responseCall.enqueue(new Callback<PaymentHistoryResponse>() {
            @Override
            public void onResponse(Call<PaymentHistoryResponse> call, Response<PaymentHistoryResponse> response) {
                progressDialog.dismiss();
                PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(getApplicationContext(), response.body());
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                toolbar.setOnClickListener(v -> {
                    onBackPressed();
                });
            }

            @Override
            public void onFailure(Call<PaymentHistoryResponse> call, Throwable t) {
                Log.e("error", "onFailure: "+t.getMessage());
                Snackbar snackbar = Snackbar.make(view, "Please check your connection", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                snackbar.show();
                progressDialog.dismiss();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //close all activity after 2 seconds
                       onBackPressed();
                    }
                }, 2000);
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}