package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.request.UserLoginRequest;
import com.example.muara_mbaduk.model.request.UserValidateRequest;
import com.example.muara_mbaduk.model.response.UserLoginResponse;
import com.example.muara_mbaduk.model.response.UserValidateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserServiceApi {

    @POST("users/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest token , @Header("Authorization") String authHeader);

    @POST("users/account")
    Call<UserValidateResponse> validateTokenJwt(@Header("Authorization") String authHeader , @Body UserValidateRequest token);

}
