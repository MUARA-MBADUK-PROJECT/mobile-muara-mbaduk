package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.squareup.picasso.Picasso;

public class BeritaDeskActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_desk);
        toolbar = findViewById(R.id.app_beritaDesk_toolbar);
        toolbar.setOnClickListener(v -> {
            onBackPressed();
        });
        String judul = getIntent().getStringExtra("JUDUL");
        String gambar = getIntent().getStringExtra("GAMBAR");
        String desk = getIntent().getStringExtra("DESKRIPSI");

        TextView tvJudul = findViewById(R.id.newsDesk_title_tv);
        ImageView ivGambar = findViewById(R.id.newsDesk_gambar_iv);
        TextView tvDesk = findViewById(R.id.news_deskription_tv);

        tvJudul.setText(judul);
        Picasso.get().load(gambar).into(ivGambar);
        tvDesk.setText(desk);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}