package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.request.UserLoginRequest;
import com.example.muara_mbaduk.data.model.response.UserLoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserServiceApi {

    @POST("users/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest token , @Header("Authorization") String authHeader);

}
