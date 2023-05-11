package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.muara_mbaduk.R;

import java.util.ArrayList;

public class PaketCampActivity extends AppCompatActivity {
ArrayList<PaketCampModel> paketCampModels = new ArrayList<>();
int[] paketGambar = {R.drawable.baseline_image_24_green, R.drawable.baseline_image_24_yellow, R.drawable.baseline_image_24_red, R.drawable.baseline_image_24};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_camp);
        RecyclerView recyclerView = findViewById(R.id.paketCampRecyclerView);
        SetUpPaketCampModels();
        PaketCamp_RecyclerViewAdapter adapter = new PaketCamp_RecyclerViewAdapter(this, paketCampModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void SetUpPaketCampModels(){
        String[] paketNama = getResources().getStringArray(R.array.nama_paket_camp_txt);
        String[] paketHarga = getResources().getStringArray(R.array.paket_harga_txt);

        for (int i = 0; i<paketNama.length; i++) {
            paketCampModels.add(new PaketCampModel(paketNama[i], paketHarga[i], paketGambar[i]));
        }
    }
}