package com.example.muara_mbaduk.data.remote;



import com.example.muara_mbaduk.model.request.ReviewPaymenetRequest;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.example.muara_mbaduk.model.response.ReviewStoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewsServiceApi {

    @GET("reviews/payment/{id}")
    Call<ReviewResponse> findByPayment(@Header("Authorization") String authHeader , @Path("id") String id);

    @GET("reviews/package/{id}")
    Call<ReviewResponse> findByPackage(@Header("Authorization") String authHeader, @Path("id") String id);

    @POST("reviews")
    Call<ReviewStoreResponse> addaReviewPayment(@Header("Authorization") String authHeader , @Body ReviewPaymenetRequest reviewPaymenetRequest);

}
