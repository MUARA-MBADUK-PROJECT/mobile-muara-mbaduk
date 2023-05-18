package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.request.UserValidateRequest;
import com.example.muara_mbaduk.model.response.UserValidateResponse;
import com.example.muara_mbaduk.data.remote.UserServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View view = findViewById(R.id.splash_view);
        SharedPreferences sh = getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        Log.i("jwt", "onActivityResult: "+jwt);
        if(!jwt.equalsIgnoreCase("not-found")){
            UserServiceApi serviceApi = RetrofitClient.getInstance().create(UserServiceApi.class);
            UserValidateRequest userValidateRequest = new UserValidateRequest();
            userValidateRequest.setToken(jwt);
            Call<UserValidateResponse> responseCall = serviceApi.validateTokenJwt(RetrofitClient.getApiKey(),userValidateRequest);
            responseCall.enqueue(new Callback<UserValidateResponse>() {
                @Override
                public void onResponse(Call<UserValidateResponse> call, Response<UserValidateResponse> response) {
                    if(response.isSuccessful()){
                        Intent homePageIntent = new Intent(SplashActivity.this , HomeActivity.class);
                        startActivity(homePageIntent);
                    }else{
                        Intent loginIntent = new Intent(SplashActivity.this , LoginActivity.class);
                        startActivity(loginIntent);
                    }
                }
                @Override
                public void onFailure(Call<UserValidateResponse> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(view, "Please check your connection", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    snackbar.show();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //close all activity after 2 seconds
                            finishAffinity();
                        }
                    }, 2000);
                }
            });
        }else{
            // if jwt sharedpreference null need to login before access the application
            Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
            startActivity(intent);
        }
    }

}