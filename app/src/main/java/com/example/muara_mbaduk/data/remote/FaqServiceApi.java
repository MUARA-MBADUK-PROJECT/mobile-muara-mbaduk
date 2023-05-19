package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.response.FaqResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FaqServiceApi {
    @GET("faq/")
    Call<FaqResponse> getAllResponse (@Header("Authorization")String authHeader);

}
