package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.response.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NewsServiceApi {
    @GET("news/")
    Call<NewsResponse> getAllNews (@Header("Authorization")String authHeader);
}
