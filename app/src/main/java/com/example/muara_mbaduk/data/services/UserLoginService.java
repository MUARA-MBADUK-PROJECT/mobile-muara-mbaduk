package com.example.muara_mbaduk.data.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.muara_mbaduk.data.model.request.UserLoginRequest;
import com.example.muara_mbaduk.data.model.response.UserLoginResponse;
import com.example.muara_mbaduk.data.remote.UserServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginService {

    private UserServiceApi userServiceApi;

    public UserLoginService(UserServiceApi userServiceApi) {
        this.userServiceApi = userServiceApi;
    }

    public void login(UserLoginRequest token , Context context , View view , GoogleSignInAccount account){
        ProgressDialog silahkan_menunggu_validasi = UtilMethod.getProgresIndicator("Silahkan Menunggu Validasi", context);
        silahkan_menunggu_validasi.show();
        Call<UserLoginResponse> userLoginResponseCall = this.userServiceApi.loginUser(token , RetrofitClient.getApiKey());
        userLoginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                silahkan_menunggu_validasi.dismiss();
                UserLoginResponse userLoginResponse = response.body();
                Intent intent = new Intent(context , HomeActivity.class);
                intent.putExtra("email" , account.getEmail());
                intent.putExtra("avatar" , account.getPhotoUrl().toString());
                intent.putExtra("name" , account.getDisplayName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
            silahkan_menunggu_validasi.dismiss();
            Snackbar snackbar = UtilMethod.genereateErrorsSnackbar(view, context, t.getMessage());
            snackbar.show();
            }
        });
    }
}
