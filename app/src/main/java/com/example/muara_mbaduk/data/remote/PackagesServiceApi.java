package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.entity.Packages;
import com.example.muara_mbaduk.model.response.HistoryResponse;
import com.example.muara_mbaduk.model.response.PackagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PackagesServiceApi {

    @GET("packages/")
    Call<PackagesResponse> getAllPackages(@Header("Authorization") String authHeader);
    @GET("packages/{slug}")
    Call<PackagesResponse> findproductPackage(@Header("Authorization") String authHeader, @Path("slug") String slug);

}
