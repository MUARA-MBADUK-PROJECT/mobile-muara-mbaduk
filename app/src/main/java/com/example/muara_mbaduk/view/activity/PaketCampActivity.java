package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.PaketCamp_RecyclerViewAdapter;
import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaketCampActivity extends AppCompatActivity{
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_camp);
        toolbar = findViewById(R.id.paketCamp_toolbar);
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        RecyclerView recyclerView = findViewById(R.id.paketCampRecyclerView);

        Retrofit retrofit = RetrofitClient.getInstance();
        PackagesServiceApi packagesServiceApi = retrofit.create(PackagesServiceApi.class);
        Call<PackagesResponse> allPackages = packagesServiceApi.getAllPackages(RetrofitClient.getApiKey());
        ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("loading..", this);
        progresIndicator.show();
        allPackages.enqueue(new Callback<PackagesResponse>() {
            @Override
            public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response) {
                progresIndicator.dismiss();
                PackagesResponse body = response.body();
                PaketCamp_RecyclerViewAdapter adapter = new PaketCamp_RecyclerViewAdapter(getApplicationContext(), body);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<PackagesResponse> call, Throwable t) {
                progresIndicator.dismiss();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PaketCampActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}