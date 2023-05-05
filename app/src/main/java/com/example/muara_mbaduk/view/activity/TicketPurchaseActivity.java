package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.view.fragment.DateAndCategoryCampFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TicketPurchaseActivity extends AppCompatActivity {
    DateAndCategoryCampFragment dateAndCategoryCampFragment
            = new DateAndCategoryCampFragment();
    BottomNavigationView bottomNavigationView;
    TextView ppnTextView;
    Button berikutnyaBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_purchase);
        ppnTextView = findViewById(R.id.ppn_textView);
        ppnTextView.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame_fragment_ticket_purchase, dateAndCategoryCampFragment, "dateAndCategoryCampFragment").commit();
    }
    public Button getBtnBerikutnya() {
        return findViewById(R.id.btn_berikutnya);
    }
    public Toolbar getToolBar() {
        return findViewById(R.id.app_ticket_purchase_toolbar);
    }

}