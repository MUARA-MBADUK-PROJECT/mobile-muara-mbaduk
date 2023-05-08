package com.example.muara_mbaduk.view.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;

import java.util.ArrayList;

public class PaketCamp_RecyclerViewAdapter extends RecyclerView.Adapter<PaketCamp_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<PaketCampModel> paketCampModels;

    public PaketCamp_RecyclerViewAdapter (Context context, ArrayList<PaketCampModel> paketCampModels){
    this.context = context;
    this.paketCampModels = paketCampModels;
    }
    @NonNull
    @Override
    public PaketCamp_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.paket_row, parent, false);
        return new PaketCamp_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaketCamp_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvnama.setText(paketCampModels.get(position).getNamaPaket());
        holder.tvharga.setText(paketCampModels.get(position).getHargaPaket());
        holder.imageView.setImageResource(paketCampModels.get(position).getGambar());
    }

    @Override
    public int getItemCount() {
        return paketCampModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvnama, tvharga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvnama = itemView.findViewById(R.id.textView);
            tvharga = itemView.findViewById(R.id.textView2);

        }
    }
}
