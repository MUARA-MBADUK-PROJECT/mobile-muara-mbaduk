package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.request.UserLoginRequest;
import com.example.muara_mbaduk.data.model.request.UserValidateRequest;
import com.example.muara_mbaduk.data.model.response.UserLoginResponse;
import com.example.muara_mbaduk.data.model.response.UserValidateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserServiceApi {

    @POST("users/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest token , @Header("Authorization") String authHeader);

    @POST("users/account")
    Call<UserValidateResponse> validateTokenJwt(@Header("Authorization") String authHeader , @Body UserValidateRequest token);

}
