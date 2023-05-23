package com.example.muara_mbaduk.data.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.hadi.emojiratingbar.EmojiRatingBar;
import com.hadi.emojiratingbar.RateStatus;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ReviewPaymentAdapter extends RecyclerView.Adapter<ReviewPaymentAdapter.ReviewPaymentViewHolder> {
    ReviewResponse reviewResponse;
    private Dialog reviewEditDialog;

    private EmojiRatingBar emojiRatingBar;
    private EditText reviewDescriptionEditText;

    public ReviewPaymentAdapter(ReviewResponse reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    @NonNull
    @Override
    public ReviewPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_payment_layout, parent,false);
        reviewEditDialog = new Dialog(parent.getContext());
        reviewEditDialog.setContentView(R.layout.dialog_edit_review);
        reviewEditDialog.setCancelable(true);
        return new ReviewPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPaymentViewHolder holder, int position) {
        Picasso.get().load(reviewResponse.getData().get(position).getImages()).into(holder.circularImageView);
        holder.jumlahRatingTextView.setText(reviewResponse.getData().get(position).getStar());
        holder.nameReviewerTextview.setText(reviewResponse.getData().get(position).getFullname());
        holder.isiReviewPaymentTextView.setText(reviewResponse.getData().get(position).getDescription());


        holder.editReviewButton.setOnClickListener(v -> {
            emojiRatingBar = reviewEditDialog.findViewById(R.id.rating_bar_dialog);
            reviewDescriptionEditText = reviewEditDialog.findViewById(R.id.edit_review_edittext);
            switch (reviewResponse.getData().get(position).getStar()){
                case "1" : {
                    emojiRatingBar.setCurrentRateStatus(RateStatus.AWFUL);
                    break;
                }
                case "2" : {
                    emojiRatingBar.setCurrentRateStatus(RateStatus.BAD);
                    break;
                }
                case "3" : {
                    emojiRatingBar.setCurrentRateStatus(RateStatus.OKAY);
                    break;
                }
                case "4" : {
                    emojiRatingBar.setCurrentRateStatus(RateStatus.GOOD);
                    break;
                }
                case "5" : {
                    emojiRatingBar.setCurrentRateStatus(RateStatus.GREAT);
                    break;
                }
                default:{
                    emojiRatingBar.setCurrentRateStatus(RateStatus.EMPTY);
                }
            }
            reviewDescriptionEditText.setText(reviewResponse.getData().get(position).getDescription());
            reviewEditDialog.show();
        });
    }




    @Override
    public int getItemCount() {
        return reviewResponse.getData().size();
    }

    public static class ReviewPaymentViewHolder extends RecyclerView.ViewHolder{
        CircularImageView circularImageView;
        TextView nameReviewerTextview,jumlahRatingTextView,isiReviewPaymentTextView;
        Button editReviewButton;
        public ReviewPaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.image_reviewer_circularImageView);
            nameReviewerTextview = itemView.findViewById(R.id.name_reviewwer_textview);
            jumlahRatingTextView = itemView.findViewById(R.id.jumlah_star_review_textview);
            isiReviewPaymentTextView = itemView.findViewById(R.id.isi_review_payment_textview);
            editReviewButton =itemView.findViewById(R.id.edit_review_btn);
        }
    }
}
