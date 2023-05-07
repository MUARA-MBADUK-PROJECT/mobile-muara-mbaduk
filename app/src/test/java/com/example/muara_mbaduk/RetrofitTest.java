package com.example.muara_mbaduk;

import com.example.muara_mbaduk.utils.RetrofitClient;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Retrofit;

public class RetrofitTest {

    @Test
    public void testCallDumyApi() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
    }
}
