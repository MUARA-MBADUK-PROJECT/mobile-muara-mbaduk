package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductServiceApi {

    @GET("products")
    Call<ProductResponse> findAll(@Header("Authorization") String authHeader);

}
