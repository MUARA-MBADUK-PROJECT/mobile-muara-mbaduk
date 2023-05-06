package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.TiketResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TicketServiceApi {

    @GET("tickets")
    Call<TiketResponse> findAll(@Header("Authorization") String authHeader);

}
