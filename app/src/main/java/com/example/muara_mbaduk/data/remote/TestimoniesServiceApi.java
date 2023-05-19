package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.response.TestimoniesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TestimoniesServiceApi {
    @GET("testimonies/")
    Call<TestimoniesResponse> getAllTestimonies(@Header("Authorization") String authHeader);
}
