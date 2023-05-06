package com.example.muara_mbaduk;

import com.example.muara_mbaduk.data.model.TiketResponse;
import com.example.muara_mbaduk.data.remote.TicketServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
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
}
