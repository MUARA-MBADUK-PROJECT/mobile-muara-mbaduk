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
import com.example.muara_mbaduk.model.response.ReviewResponse;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

public class UlasanCamp_RecyclerViewAdapter extends RecyclerView.Adapter<UlasanCamp_RecyclerViewAdapter.MyViewHolder>{
    Context context;
    ReviewResponse reviewResponse;

    public UlasanCamp_RecyclerViewAdapter (Context context, ReviewResponse reviewResponse){
        this.context = context;
        this.reviewResponse = reviewResponse;
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

        holder.tvnama.setText(reviewResponse.getData().get(position).getFullname());
        holder.tvdesk.setText(reviewResponse.getData().get(position).getDescription());
        holder.starReviews.setNumStars(5);
        holder.starReviews.setClickable(false);
        holder.starReviews.setScrollable(false);
        holder.starReviews.setRating(Integer.parseInt(reviewResponse.getData().get(position).getStar()));;
        Picasso.get().load(reviewResponse.getData().get(position).getImages()).into(holder.profilImage);

    }

    @Override
    public int getItemCount() {
        return reviewResponse.getData().size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircularImageView profilImage;
        TextView tvnama, tvdesk;
        ScaleRatingBar starReviews;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            starReviews = itemView.findViewById(R.id.star_reviews);
            profilImage = itemView.findViewById(R.id.ulasan_profil_iv);
            tvnama = itemView.findViewById(R.id.ulasan_nama_tv);
            tvdesk = itemView.findViewById(R.id.ulasan_desk_tv);
        }
    }

}
