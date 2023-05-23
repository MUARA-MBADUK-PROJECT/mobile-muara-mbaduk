package com.example.muara_mbaduk.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ReviewPaymentAdapter extends RecyclerView.Adapter<ReviewPaymentAdapter.ReviewPaymentViewHolder> {
    ReviewResponse reviewResponse;

    public ReviewPaymentAdapter(ReviewResponse reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    @NonNull
    @Override
    public ReviewPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_payment_layout, parent,false);
        return new ReviewPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPaymentViewHolder holder, int position) {
        Picasso.get().load(reviewResponse.getData().get(position).getImages()).into(holder.circularImageView);
        holder.jumlahRatingTextView.setText(reviewResponse.getData().get(position).getStar());
        holder.nameReviewerTextview.setText(reviewResponse.getData().get(position).getFullname());
        holder.isiReviewPaymentTextView.setText(reviewResponse.getData().get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return reviewResponse.getData().size();
    }

    public static class ReviewPaymentViewHolder extends RecyclerView.ViewHolder{
        CircularImageView circularImageView;
        TextView nameReviewerTextview,jumlahRatingTextView,isiReviewPaymentTextView;
        public ReviewPaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.image_reviewer_circularImageView);
            nameReviewerTextview = itemView.findViewById(R.id.name_reviewwer_textview);
            jumlahRatingTextView = itemView.findViewById(R.id.jumlah_star_review_textview);
            isiReviewPaymentTextView = itemView.findViewById(R.id.isi_review_payment_textview);
        }
    }
}
