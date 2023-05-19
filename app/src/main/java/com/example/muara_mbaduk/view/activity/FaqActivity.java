package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.Faq_RecyclerViewAdapter;
import com.example.muara_mbaduk.model.response.FaqResponse;
import com.example.muara_mbaduk.data.remote.FaqServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        RecyclerView recyclerView = findViewById(R.id.Faq_rv);

        Retrofit retrofit = RetrofitClient.getInstance();
        FaqServiceApi faqServiceApi = retrofit.create(FaqServiceApi.class);
        Call<FaqResponse> allFaq = faqServiceApi.getAllResponse(RetrofitClient.getApiKey());

        allFaq.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                FaqResponse body = response.body();
                Faq_RecyclerViewAdapter adapter = new Faq_RecyclerViewAdapter(getApplicationContext(), body);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}