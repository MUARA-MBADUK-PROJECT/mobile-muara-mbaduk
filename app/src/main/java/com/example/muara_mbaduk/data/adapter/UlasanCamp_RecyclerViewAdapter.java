package com.example.muara_mbaduk.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.response.TestimoniesResponse;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class UlasanCamp_RecyclerViewAdapter extends RecyclerView.Adapter<UlasanCamp_RecyclerViewAdapter.MyViewHolder>{
    Context context;
    TestimoniesResponse testimoniesResponse;

    public UlasanCamp_RecyclerViewAdapter (Context context, TestimoniesResponse testimoniesResponse){
        this.context = context;
        this.testimoniesResponse = testimoniesResponse;
    }
    @NonNull
    @Override
    public UlasanCamp_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_ulasan, parent, false);
        return new UlasanCamp_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UlasanCamp_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvnama.setText(testimoniesResponse.getData().get(position).getFullname());
        holder.tvdesk.setText(testimoniesResponse.getData().get(position).getDescription());
        Picasso.get().load(testimoniesResponse.getData().get(position).getImages()).into(holder.profilImage);
        holder.rating.setImageResource(R.drawable.rating_1);

    }

    @Override
    public int getItemCount() {
        return testimoniesResponse.getData().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircularImageView profilImage;
        ImageView rating;
        TextView tvnama, tvdesk;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilImage = itemView.findViewById(R.id.ulasan_profil_iv);
            rating = itemView.findViewById(R.id.ulasan_rating_iv);
            tvnama = itemView.findViewById(R.id.ulasan_nama_tv);
            tvdesk = itemView.findViewById(R.id.ulasan_desk_tv);
        }
    }
}
