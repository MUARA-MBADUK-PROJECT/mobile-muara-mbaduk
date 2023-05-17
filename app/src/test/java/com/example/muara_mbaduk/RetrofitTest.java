package com.example.muara_mbaduk;

import com.example.muara_mbaduk.data.model.response.UserValidateResponse;
import com.example.muara_mbaduk.data.remote.UserServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitTest {

    @Test
    public void testCallDumyApi() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
    }


    @Test
    public void testValidateUser() throws IOException {
        UserServiceApi serviceApi = RetrofitClient.getInstance().create(UserServiceApi.class);
      //  Call<UserValidateResponse> responseCall = serviceApi.validateTokenJwt(RetrofitClient.getApiKey(), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImRkMDA2NGM4LTRmM2MtNGI2ZS05ZWIzLWMwMjg0Y2IzOGY3OCIsImZ1bGxuYW1lIjoiTW9oYW1tYWQgdGFqdXQgWmFtIHphbWkiLCJlbWFpbCI6Im1vaGFtbWFkdGFqdXR6YW16YW1pMDdAZ21haWwuY29tIiwiaW1hZ2VzIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUdObXl4WmFDNENQNTJJUVR3VFp1OTY5ZGVreVdWNnAtMlF5eGRZdFJYRGw9czk2LWMiLCJpYXQiOjE2ODQyMTE3MjEsImV4cCI6MTY4NDgxNjUyMX0.H9iR2JZ_MU6zy3eEthbJecMLzYGs9qVJSsAJIf19q1c");
       // UserValidateResponse body = responseCall.execute().body();
    }
}
