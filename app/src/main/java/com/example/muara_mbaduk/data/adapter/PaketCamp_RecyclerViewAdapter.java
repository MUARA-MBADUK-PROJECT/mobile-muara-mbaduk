package com.example.muara_mbaduk.data.adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.response.PackagesResponse;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.PaketCampModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public void onBindViewHolder(@NonNull PaketCamp_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvnama.setText(packagesResponse.getData().get(position).getTitle());
        holder.tvharga.setText(String.valueOf(packagesResponse.getData().get(position).getPrice()));
        Picasso.get().load(packagesResponse.getData().get(position).getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return packagesResponse.getData().size();
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
