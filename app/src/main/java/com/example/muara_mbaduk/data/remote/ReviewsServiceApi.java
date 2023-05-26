package com.example.muara_mbaduk.data.remote;



import com.example.muara_mbaduk.model.request.ReviewPaymenetRequest;
import com.example.muara_mbaduk.model.request.ReviewUpdateRequest;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.example.muara_mbaduk.model.response.ReviewStoreResponse;
import com.example.muara_mbaduk.model.response.ReviewUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewsServiceApi {

    @GET("reviews/payment/{id}")
    Call<ReviewResponse> findByPayment(@Header("Authorization") String authHeader , @Path("id") String id);

    @GET("reviews/package/{id}")
    Call<ReviewResponse> findByPackage(@Header("Authorization") String authHeader, @Path("id") String id);

    @POST("reviews")
    Call<ReviewStoreResponse> addReviewPayment(@Header("Authorization") String authHeader , @Body ReviewPaymenetRequest reviewPaymenetRequest);

    @PUT("reviews/{idPayment}")
    Call<ReviewUpdateResponse> updateReviewPayment(@Header("Authorization") String authHeader , @Body ReviewUpdateRequest request , @Path("idPayment")  String idPayment);

}
