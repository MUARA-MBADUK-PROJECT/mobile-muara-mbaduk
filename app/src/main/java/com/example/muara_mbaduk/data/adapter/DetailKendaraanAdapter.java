package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.pojo.DetailKendaraan;

public class DetailKendaraanAdapter extends RecyclerView.Adapter<DetailKendaraanAdapter.DetailKendaraanViewHolder> {
    private int size;
    private DetailKendaraan detailKendaraan;
    private int countMotor = 0;
    private int countMobil = 1;

    public DetailKendaraanAdapter(int size, DetailKendaraan detailKendaraan) {
        this.size = size;
        this.detailKendaraan = detailKendaraan;
    }
    @NonNull
    @Override
    public DetailKendaraanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kendaraan, parent, false);
        DetailKendaraanAdapter.DetailKendaraanViewHolder holder = new DetailKendaraanAdapter.DetailKendaraanViewHolder(view);
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailKendaraanViewHolder holder, int position) {
        if (detailKendaraan.getDataMobil().size() != 0) {
            countMotor++;
            if(position +1 > detailKendaraan.getDataMobil().size()){
                countMotor--;
            }
            holder.mobilIdentityTextView.setText("Kendaraan " + String.valueOf(countMotor));
        }
        if (detailKendaraan.getDataMotor().size() != 0) {
           countMotor++;
           holder.motorIdentitiyTextView.setText("Kendaraan " + String.valueOf(countMotor));
        }
        if (size == detailKendaraan.getDataMobil().size() && detailKendaraan.getDataMobil().get(position) == null) {
            holder.mobilDetailLinearLayout.setVisibility(View.GONE);
        } else {
            if (position + 1 > detailKendaraan.getDataMobil().size()) {
                holder.mobilDetailLinearLayout.setVisibility(View.GONE);
            }
        }
        if (size == detailKendaraan.getDataMotor().size() && detailKendaraan.getDataMotor().get(position) == null) {
            holder.motorDetailLinearLayout.setVisibility(View.GONE);
        } else {
            if (position + 1 > detailKendaraan.getDataMotor().size()) {
                holder.motorDetailLinearLayout.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public int getItemCount() {
        return size;
    }
    public static class DetailKendaraanViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMotor, textViewMobil, motorIdentitiyTextView, mobilIdentityTextView;
        LinearLayout motorDetailLinearLayout, mobilDetailLinearLayout;
        public DetailKendaraanViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMotor = itemView.findViewById(R.id.platnomor_motor_textview);
            textViewMobil = itemView.findViewById(R.id.platnomor_textview);
            motorIdentitiyTextView = itemView.findViewById(R.id.motor_identity_textview);
            mobilIdentityTextView = itemView.findViewById(R.id.mobil_identity_textview);
            motorDetailLinearLayout = itemView.findViewById(R.id.motor_detail_linearlayout);
            mobilDetailLinearLayout = itemView.findViewById(R.id.mobil_detail_linearlayout);
        }
    }
}
