package com.example.muara_mbaduk.data.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.remote.ReviewsServiceApi;
import com.example.muara_mbaduk.model.Errors;
import com.example.muara_mbaduk.model.request.ReviewUpdateRequest;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.example.muara_mbaduk.model.response.ReviewUpdateResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.HomeActivity;
import com.hadi.emojiratingbar.EmojiRatingBar;
import com.hadi.emojiratingbar.RateStatus;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewPaymentAdapter extends RecyclerView.Adapter<ReviewPaymentAdapter.ReviewPaymentViewHolder> {
    ReviewResponse reviewResponse;
    private Dialog reviewEditDialog;

    private Button simpanUpdateReviewBtn;


    LayerDrawable layerDrawable;
    private ScaleRatingBar emojiRatingBar , starReview;
    private EditText reviewDescriptionEditText;

    Context context;
    private String idPayment;


    public ReviewPaymentAdapter(ReviewResponse reviewResponse , Context context , String idPayment) {
        this.reviewResponse = reviewResponse;
        this.context = context;
        this.idPayment = idPayment;
    }

    @NonNull
    @Override
    public ReviewPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_payment_layout, parent,false);
        reviewEditDialog = new Dialog(parent.getContext());
        reviewEditDialog.setContentView(R.layout.dialog_edit_review);
        reviewEditDialog.setCancelable(true);
        emojiRatingBar = reviewEditDialog.findViewById(R.id.star_rating);
        simpanUpdateReviewBtn =reviewEditDialog.findViewById(R.id.simpan_update_review_btn);
        return new ReviewPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPaymentViewHolder holder, int position) {
        Picasso.get().load(reviewResponse.getData().get(position).getImages()).into(holder.circularImageView);
        holder.nameReviewerTextview.setText(reviewResponse.getData().get(position).getFullname());
        holder.isiReviewPaymentTextView.setText(reviewResponse.getData().get(position).getDescription());
        holder.starReview.setNumStars(5);
        holder.starReview.setRating(Integer.parseInt(reviewResponse.getData().get(position).getStar()));
        holder.editReviewButton.setOnClickListener(v -> {
            reviewDescriptionEditText = reviewEditDialog.findViewById(R.id.edit_review_edittext);
            int parseInt = Integer.parseInt(reviewResponse.getData().get(position).getStar());
            emojiRatingBar.setNumStars(5);
            emojiRatingBar.setRating(parseInt);
            reviewDescriptionEditText.setText(reviewResponse.getData().get(position).getDescription());
            reviewEditDialog.show();
            simpanUpdateReviewBtn.setOnClickListener(view -> {
                ReviewUpdateRequest request = new ReviewUpdateRequest();
                request.setStar(UtilMethod.floatToInt(emojiRatingBar.getRating()));
                request.setDescription(reviewDescriptionEditText.getText().toString());

                ReviewsServiceApi serviceApi = RetrofitClient.getInstance().create(ReviewsServiceApi.class);
                Call<ReviewUpdateResponse> responseCall = serviceApi.updateReviewPayment(RetrofitClient.getApiKey(), request,idPayment);

                responseCall.enqueue(new Callback<ReviewUpdateResponse>() {
                    @Override
                    public void onResponse(Call<ReviewUpdateResponse> call, Response<ReviewUpdateResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context , "Review berhasil di update tunggu sebentar" , Toast.LENGTH_LONG).show();
                            System.out.println(request.toString());
                            final Handler handler = new Handler(Looper.getMainLooper());
                            reviewEditDialog.dismiss();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //close all activity after 2 seconds
                                    Intent intent = new Intent(context.getApplicationContext() , HomeActivity.class);
                                    context.startActivity(intent);
                                }
                            }, 2000);

                        }else{
                            String jsonError = null;
                            try {
                                jsonError = response.errorBody().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Errors errors = UtilMethod.generateErrors(jsonError);
                            Toast.makeText(context , errors.getErrors().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ReviewUpdateResponse> call, Throwable t) {
                        Toast.makeText(context , t.getMessage() , Toast.LENGTH_LONG).show();
                    }
                });
            });
        });
    }
    @Override
    public int getItemCount() {
        return reviewResponse.getData().size();
    }

    public static class ReviewPaymentViewHolder extends RecyclerView.ViewHolder{
        CircularImageView circularImageView;
        TextView nameReviewerTextview,isiReviewPaymentTextView;
        Button editReviewButton;
        ScaleRatingBar starReview;
        public ReviewPaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.image_reviewer_circularImageView);
            nameReviewerTextview = itemView.findViewById(R.id.name_reviewwer_textview);
            starReview = itemView.findViewById(R.id.star_review);
            isiReviewPaymentTextView = itemView.findViewById(R.id.isi_review_payment_textview);
            editReviewButton =itemView.findViewById(R.id.edit_review_btn);
            // membuat rating bar tidak bisa diclick
            starReview.setIsIndicator(true);
        }
    }
}
