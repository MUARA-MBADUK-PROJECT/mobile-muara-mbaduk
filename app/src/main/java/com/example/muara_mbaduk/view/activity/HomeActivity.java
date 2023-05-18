package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView paketcamp, pemesananTiket, avatarImageView, Faq;
    String photo_image, email, displayName;
    RealmHelper realmHelper;
    Realm realm;

    @Override
    public void onBackPressed() {
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

        avatarImageView = findViewById(R.id.avatar_imageView);
        displayNameTextView = findViewById(R.id.displayName_textview);
        Picasso.get().load(photo_image).into(avatarImageView);
        displayNameTextView.setText(displayName);
        System.out.println(photo_image);
        hargatiket = findViewById(R.id.hargatiket_id);
        paketcamp = findViewById(R.id.paketcamp);
        pemesananTiket = findViewById(R.id.pembeliantiket);
        Faq = findViewById(R.id.faq_iv);
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
        Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, FaqActivity.class);
                startActivity(i);
            }
        });
    }

}