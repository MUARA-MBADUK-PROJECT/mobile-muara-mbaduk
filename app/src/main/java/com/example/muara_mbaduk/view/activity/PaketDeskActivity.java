package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.PaketProduct_RecyclerViewAdapter;
import com.example.muara_mbaduk.data.adapter.UlasanCamp_RecyclerViewAdapter;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.model.entity.Packages;
import com.example.muara_mbaduk.model.response.PackageBySlugResponse;
import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.model.response.TestimoniesResponse;
import com.example.muara_mbaduk.data.remote.TestimoniesServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import androidx.appcompat.widget.Toolbar;

public class PaketDeskActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_desk);
        toolbar = findViewById(R.id.app_paket_camp_toolbar);
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        String gambar = getIntent().getStringExtra("GAMBAR");
        String nama = getIntent().getStringExtra("NAMA");
        String harga = getIntent().getStringExtra("HARGA");
        String deskripsi = getIntent().getStringExtra("DESKRIPSI");
        String slug = getIntent().getStringExtra("SLUG");
        System.out.println(slug);
        System.out.println("slug")
        ;

        ImageView ivGambar = findViewById(R.id.imageid);
        TextView tvNama = findViewById(R.id.nama_paket_camp);
        TextView tvHarga = findViewById(R.id.harga_paket_camp);
        TextView tvDesk = findViewById(R.id.deskripsi);
        Button pesanSekarang = findViewById(R.id.btn_pesanSekarang);
        pesanSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaketDeskActivity.this, TicketPurchaseActivity.class);
                startActivity(i);
            }
        });

        Picasso.get().load(gambar).into(ivGambar);
        tvNama.setText(nama);
        tvHarga.setText(harga);
        tvDesk.setText(deskripsi);

        RecyclerView recyclerView = findViewById(R.id.rv_ulasan);
        RecyclerView productRv = findViewById(R.id.product_rv);
        productRv.setNestedScrollingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        Retrofit retrofit = RetrofitClient.getInstance();
        PackagesServiceApi packagesServiceApi = retrofit.create(PackagesServiceApi.class);
        Call<PackageBySlugResponse> productDesk = packagesServiceApi.findproductPackage(RetrofitClient.getApiKey(), slug);
        productDesk.enqueue(new Callback<PackageBySlugResponse>() {
            @Override
            public void onResponse(Call<PackageBySlugResponse> call, Response<PackageBySlugResponse> response) {
                PackageBySlugResponse body = response.body();
                PaketProduct_RecyclerViewAdapter adapter= new PaketProduct_RecyclerViewAdapter(getApplicationContext(), body);
                productRv.setAdapter(adapter);
                productRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<PackageBySlugResponse> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        TestimoniesServiceApi testimoniesServiceApi = retrofit.create(TestimoniesServiceApi.class);
        Call<TestimoniesResponse> allTestimonies = testimoniesServiceApi.getAllTestimonies(RetrofitClient.getApiKey());
        allTestimonies.enqueue(new Callback<TestimoniesResponse>() {
            @Override
            public void onResponse(Call<TestimoniesResponse> call, Response<TestimoniesResponse> response) {
                TestimoniesResponse body = response.body();
                UlasanCamp_RecyclerViewAdapter adapter = new UlasanCamp_RecyclerViewAdapter(getApplicationContext(), body);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure(Call<TestimoniesResponse> call, Throwable t) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PaketCampActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}