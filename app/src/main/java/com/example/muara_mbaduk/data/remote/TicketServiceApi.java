package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.data.model.response.TiketResponse;
import com.example.muara_mbaduk.data.model.request.CheckinRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TicketServiceApi  {

    @GET("tickets")
    Call<TiketResponse> findAll(@Header("Authorization") String authHeader);


    @POST("tickets/checkin")
    Call<TicketCheckinResponse> checkin(@Header("Authorization") String authHeader , @Body CheckinRequest checkinRequest);

}
