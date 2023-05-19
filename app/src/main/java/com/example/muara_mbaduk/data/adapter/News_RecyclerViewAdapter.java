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
import com.example.muara_mbaduk.model.response.NewsResponse;
import com.squareup.picasso.Picasso;

public class News_RecyclerViewAdapter extends RecyclerView.Adapter<News_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    NewsResponse newsResponse;

    public News_RecyclerViewAdapter(Context context, NewsResponse newsResponse) {
        this.context = context;
        this.newsResponse = newsResponse;
    }

    @NonNull
    @Override
    public News_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_berita, parent, false);
        return new News_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(newsResponse.getData().get(position).getThumbnail()).into(holder.gambar);
        holder.tanggal.setText(newsResponse.getData().get(position).getUpdated_at());
        holder.judul.setText(newsResponse.getData().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return newsResponse.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView tanggal;
        TextView judul;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.berita_iv);
            tanggal = itemView.findViewById(R.id.berita_tanggal_tv);
            judul  = itemView.findViewById(R.id.berita_desk_tv);
        }
    }
}
