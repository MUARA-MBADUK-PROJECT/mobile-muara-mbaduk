package com.example.muara_mbaduk.data.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.view.activity.PaketDeskActivity;
import com.squareup.picasso.Picasso;

public class PaketCamp_RecyclerViewAdapter extends RecyclerView.Adapter<PaketCamp_RecyclerViewAdapter.MyViewHolder> {
    Context context;

//    ArrayList<PaketCampModel> paketCampModels;
    PackagesResponse packagesResponse;
    public PaketCamp_RecyclerViewAdapter (Context context, PackagesResponse packagesResponse){
    this.context = context;
    this.packagesResponse = packagesResponse;
    }

    @NonNull
    @Override
    public PaketCamp_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.paket_row, parent, false);
        return new PaketCamp_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaketCamp_RecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvnama.setText(packagesResponse.getData().get(position).getTitle());
        holder.tvharga.setText(String.valueOf(packagesResponse.getData().get(position).getPrice()));
        Picasso.get().load(packagesResponse.getData().get(position).getImage()).into(holder.imageView);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getPosition();

            Context context = v.getContext();
            Intent intent = new Intent(context, PaketDeskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("GAMBAR", packagesResponse.getData().get(position).getImage());
            intent.putExtra("NAMA", packagesResponse.getData().get(position).getTitle());
            intent.putExtra("HARGA", String.valueOf(packagesResponse.getData().get(position).getPrice()));
            intent.putExtra("DESKRIPSI", packagesResponse.getData().get(position).getDescription());
            intent.putExtra("SLUG", packagesResponse.getData().get(position).getSlug());
            intent.putExtra("ID", packagesResponse.getData().get(position).getId());
            context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return packagesResponse.getData().size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ConstraintLayout paketRow;
        TextView tvnama, tvharga;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvnama = itemView.findViewById(R.id.textView);
            tvharga = itemView.findViewById(R.id.textView2);
            btn = itemView.findViewById(R.id.detail_paketCamp_bton);
            paketRow = itemView.findViewById(R.id.paket_row);
//
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//         int position = getAdapterPosition();
//            Toast.makeText(itemView.getContext(), "position" + position, Toast.LENGTH_SHORT).show();
//        }
    }
}
