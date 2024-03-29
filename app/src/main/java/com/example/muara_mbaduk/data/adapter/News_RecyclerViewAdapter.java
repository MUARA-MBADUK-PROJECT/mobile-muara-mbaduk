package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.response.NewsResponse;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.BeritaDeskActivity;
import com.example.muara_mbaduk.view.activity.HomeActivity;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(newsResponse.getData().get(position).getThumbnail()).into(holder.gambar);
        String timeStampsFormat = UtilMethod.stringFormatedWithTimeStampsFormat(newsResponse.getData().get(position).getCreated_at());
        System.out.println(timeStampsFormat);
        holder.tanggal.setText(timeStampsFormat);
        holder.judul.setText(newsResponse.getData().get(position).getTitle());
        holder.newsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent desk = new Intent(context, BeritaDeskActivity.class);
                desk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                desk.putExtra("JUDUL", newsResponse.getData().get(position).getTitle());
                desk.putExtra("GAMBAR", newsResponse.getData().get(position).getThumbnail());
                desk.putExtra("DESKRIPSI", newsResponse.getData().get(position).getBody());
                context.startActivity(desk);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsResponse.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout newsRow;
        ImageView gambar;
        TextView tanggal;
        TextView judul;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newsRow = itemView.findViewById(R.id.news_row);
            gambar = itemView.findViewById(R.id.berita_iv);
            tanggal = itemView.findViewById(R.id.berita_tanggal_tv);
            judul  = itemView.findViewById(R.id.berita_desk_tv);
        }
    }
}
