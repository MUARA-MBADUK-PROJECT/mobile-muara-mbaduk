package com.example.muara_mbaduk;

import com.example.muara_mbaduk.model.Errors;
import com.example.muara_mbaduk.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.model.response.TiketResponse;
import com.example.muara_mbaduk.data.remote.TicketServiceApi;
import com.example.muara_mbaduk.model.request.CheckinRequest;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TiketServiceApiTest {


    @Test
    public void testFindAllTikeet() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
        TicketServiceApi serviceApi = retrofit.create(TicketServiceApi.class);
        Call<TiketResponse> responseCall = serviceApi.findAll(RetrofitClient.getApiKey());
        TiketResponse tiketResponse = responseCall.execute().body();
        tiketResponse.getData().forEach(tiket -> {
            System.out.println(tiket.getTitle());
        });
    }


    @Test
    public void testCheckin() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
        TicketServiceApi serviceApi = retrofit.create(TicketServiceApi.class);
        CheckinRequest checkinRequest = new CheckinRequest();
        checkinRequest.setCamping(true);
        checkinRequest.setDate("07/05/2021");
        Call<TicketCheckinResponse> responseCall = serviceApi.checkin(RetrofitClient.getApiKey(), checkinRequest);
        Response<TicketCheckinResponse> execute = responseCall.execute();
        if(execute.isSuccessful()){
            TicketCheckinResponse body = execute.body();
        }else{
            ResponseBody responseBody = execute.errorBody();
            String jsonResponse = responseBody.string();
            Gson gson = new GsonBuilder().create();
            Errors checkinTicketErrorResponse = gson.fromJson(jsonResponse, Errors.class);
        }
    }
}
