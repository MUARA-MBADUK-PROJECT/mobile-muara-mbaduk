package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HomeActivity extends AppCompatActivity {
    ImageView hargatiket;
    TextView displayNameTextView;
    ImageView paketcamp, pemesananTiket, avatarImageView, riwayatPemesananImageView;
    String photo_image, email, displayName;
    RealmHelper realmHelper;
    Realm realm;

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
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            displayName = extras.getString("name");
            photo_image = extras.getString("avatar");
            email = extras.getString("email");
        }


        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        riwayatPemesananImageView = findViewById(R.id.riwayat_pemesan_btn);
        avatarImageView = findViewById(R.id.avatar_imageView);
        displayNameTextView = findViewById(R.id.displayName_textview);
        Picasso.get().load(photo_image).into(avatarImageView);
        displayNameTextView.setText(displayName);
        System.out.println(photo_image);
        hargatiket = findViewById(R.id.hargatiket_id);
        paketcamp = findViewById(R.id.paketcamp);
        pemesananTiket = findViewById(R.id.pembeliantiket);
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
            List<UserModel> allUser = realmHelper.findAllUser();
            allUser.forEach(user -> {
                System.out.println(user.getId());
                System.out.println(user.getEmail());
            });
            Intent i = new Intent(HomeActivity.this, TicketPurchaseActivity.class);
            startActivity(i);
        });
        riwayatPemesananImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, PurchaseHistoryActivity.class);
            startActivity(intent);
        });

    }

}