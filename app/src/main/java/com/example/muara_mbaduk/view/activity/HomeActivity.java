package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.muara_mbaduk.R;
public class HomeActivity extends AppCompatActivity {
    ImageView hargatiket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hargatiket = findViewById(R.id.hargatiket_id);
        hargatiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HargaTiketActivity.class);
                startActivity(i);
            }
        });
    }

}