package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.News_RecyclerViewAdapter;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;

import com.example.muara_mbaduk.data.remote.SyaratDanKetentuanServiceApi;
import com.example.muara_mbaduk.model.response.NewsResponse;
import com.example.muara_mbaduk.data.remote.NewsServiceApi;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuan2Response;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuanResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;

import com.example.muara_mbaduk.utils.UtilMethod;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeActivity extends AppCompatActivity {
    ImageView hargatiket;
    TextView displayNameTextView,skTextView;
    ImageView paketcamp, pemesananTiket, avatarImageView, riwayatPemesananImageView,Faq, sk;
    RealmHelper realmHelper;
    Realm realm;
    UserModel userModel;

    View view;
    LinearLayoutManager linearLayoutManager;


    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol kembali sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponents();


        SyaratDanKetentuanServiceApi serviceApi = RetrofitClient.getInstance().create(SyaratDanKetentuanServiceApi.class);

        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SyaratDanKetentuanActivity.class);
                startActivity(i);
            }
        });
        hargatiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HargaTiketActivity.class);
                startActivity(i);
            }
        });
        paketcamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, PaketCampActivity.class);
                startActivity(i);

            }
        });

        pemesananTiket.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, TicketPurchaseActivity.class);
            startActivity(i);

        });
        Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, FaqActivity.class);
                startActivity(i);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.berita_rv);

        Retrofit retrofit = RetrofitClient.getInstance();
        NewsServiceApi newsServiceApi = retrofit.create(NewsServiceApi.class);
        Call<NewsResponse> allNews = newsServiceApi.getAllNews(RetrofitClient.getApiKey());
        allNews.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse body = response.body();
                News_RecyclerViewAdapter adapter = new News_RecyclerViewAdapter(getApplicationContext(), body);
                linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {

            }
        });

        riwayatPemesananImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, PurchaseHistoryActivity.class);
            startActivity(intent);
        });
    }

    public void initComponents(){
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        userModel = realmHelper.findByJwt(UtilMethod.getJwt(this));
        riwayatPemesananImageView = findViewById(R.id.riwayat_pemesan_btn);
        avatarImageView = findViewById(R.id.avatar_imageView);
        displayNameTextView = findViewById(R.id.displayName_textview);
        if(userModel != null){
            Picasso.get().load(userModel.getImages()).into(avatarImageView);
            displayNameTextView.setText(userModel.getFullname());
        }
        hargatiket = findViewById(R.id.hargatiket_id);
        paketcamp = findViewById(R.id.paketcamp);
        pemesananTiket = findViewById(R.id.pembeliantiket);
        Faq = findViewById(R.id.faq_iv);
        sk = findViewById(R.id.sk);

        view = findViewById(R.id.home_layout);
    }
}