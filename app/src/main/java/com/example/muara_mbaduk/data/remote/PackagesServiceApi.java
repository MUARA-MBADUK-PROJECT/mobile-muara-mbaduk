package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.response.PackagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PackagesServiceApi {

    @GET("packages/")
    Call<PackagesResponse> getAllPackages(@Header("Authorization") String authHeader);

}
