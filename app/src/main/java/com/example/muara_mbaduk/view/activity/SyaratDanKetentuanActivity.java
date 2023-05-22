package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.remote.SyaratDanKetentuanServiceApi;
import com.example.muara_mbaduk.model.entity.SyaratDanKetentuan;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuanResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SyaratDanKetentuanActivity extends AppCompatActivity {
    TextView txt;
    SyaratDanKetentuanResponse syaratDanKetentuanResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_dan_ketentuan);

        Retrofit retrofit = RetrofitClient.getInstance();
        SyaratDanKetentuanServiceApi syaratDanKetentuanServiceApi = retrofit.create(SyaratDanKetentuanServiceApi.class);
        Call<SyaratDanKetentuanResponse> getSK1 = syaratDanKetentuanServiceApi.getAllSK(RetrofitClient.getApiKey());
        getSK1.enqueue(new Callback<SyaratDanKetentuanResponse>() {
            @Override
            public void onResponse(Call<SyaratDanKetentuanResponse> call, Response<SyaratDanKetentuanResponse> response) {
                if (response.code() == 200){
                    SyaratDanKetentuanResponse body = response.body();
                    ArrayList<SyaratDanKetentuan> data = (ArrayList<SyaratDanKetentuan>) body.getData();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        data.forEach(sk ->{
                            if (sk.getSlug().equalsIgnoreCase("terms-of-service")){
                                txt = findViewById(R.id.sk_desk_tv);
                                txt.setText(Html.fromHtml(sk.getBody()));
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(Call<SyaratDanKetentuanResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }

        });
    }
}