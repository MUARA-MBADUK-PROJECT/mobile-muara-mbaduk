package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.DetailKendaraanAdapter;
import com.example.muara_mbaduk.data.adapter.DetailWisatawanAdapter;
import com.example.muara_mbaduk.data.pojo.DataOrder;
import com.example.muara_mbaduk.data.pojo.DetailKendaraan;

import java.util.ArrayList;
import java.util.Objects;

public class DetailPembeliActivity extends AppCompatActivity {

    private RecyclerView recyclerView , kendaraanRecycleView;
    private RadioGroup radioGroup;
    private RadioButton bankRadioButton , bayarDitempatRadioButton;
    ImageView imageRadioBank , imageRadioBayarDitempat;
    private LinearLayout bankLinearLayout , bayarDitempatLinearLayout;
    private Dialog bankPaymentDialog;
    private ImageView bniImage , briImage , bcaImage;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);
        Toolbar toolbar = findViewById(R.id.detail_ticket_activity_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // menampilkan tombol kembali di toolbar
        toolbar.setOnClickListener(v -> {
            System.out.println("clicked back");
            onBackPressed();
        });
        recyclerView = findViewById(R.id.detail_wisatawan_recycleview);
        kendaraanRecycleView = findViewById(R.id.detail_kendaraan_recycleview);
        Bundle bundle = getIntent().getExtras();
        DataOrder myObject = (DataOrder) bundle.getSerializable("data");
        DetailWisatawanAdapter detailWisatawanAdapter = new DetailWisatawanAdapter(myObject.getDataList());
        recyclerView.setAdapter(detailWisatawanAdapter);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        int size = getIntent().getExtras().getInt("size");
        ArrayList<String> motor = getIntent().getStringArrayListExtra("motor");
        ArrayList<String> mobil = getIntent().getStringArrayListExtra("mobil");
        DetailKendaraan detailKendaraan = new DetailKendaraan();
        detailKendaraan.setDataMotor(motor);
        detailKendaraan.setDataMobil(mobil);
        radioGroup = findViewById(R.id.radio_group);
        bankRadioButton = findViewById(R.id.radio_button_bank);
        imageRadioBank = findViewById(R.id.checked_bank_image);
        bayarDitempatRadioButton = findViewById(R.id.radio_button_bayar_ditempat);
        imageRadioBayarDitempat = findViewById(R.id.checked_bayar_ditempat_image);
        bankLinearLayout = findViewById(R.id.radio_layout_1);
        bayarDitempatLinearLayout = findViewById(R.id.radio_layout_2);

        initDialog();

        bankLinearLayout.setOnClickListener(v -> {
            bankRadioButton.setSelected(true);
            bayarDitempatRadioButton.setSelected(false);
            imageRadioBank.setImageDrawable(getDrawable(R.drawable.checked_drawable));
            imageRadioBayarDitempat.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            bankPaymentDialog.show();
        });
        bayarDitempatLinearLayout.setOnClickListener(v -> {
            bankRadioButton.setSelected(false);
            bayarDitempatRadioButton.setSelected(true);
            imageRadioBank.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
            imageRadioBayarDitempat.setImageDrawable(getDrawable(R.drawable.checked_drawable));
        });
        DetailKendaraanAdapter detailKendaraanAdapter = new DetailKendaraanAdapter(size ,detailKendaraan);
        LinearLayoutManager linearLayoutKendaraan  = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        kendaraanRecycleView.setAdapter(detailKendaraanAdapter);
        kendaraanRecycleView.setLayoutManager(linearLayoutKendaraan);
    }
    public void initDialog(){
        bankPaymentDialog = new Dialog(this);
        bankPaymentDialog.setContentView(R.layout.payment_dialog);
        briImage = bankPaymentDialog.findViewById(R.id.bri_image);
        bcaImage = bankPaymentDialog.findViewById(R.id.bca_image);
        bniImage = bankPaymentDialog.findViewById(R.id.bni_image);
        bniImage.setImageDrawable(getDrawable(R.drawable.checked_drawable));
        briImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
        bcaImage.setImageDrawable(getDrawable(R.drawable.unchecked_drawable));
        bankPaymentDialog.setCancelable(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // cek apakah tombol kembali diklik
            onBackPressed(); // navigasi kembali ke activity induk
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}