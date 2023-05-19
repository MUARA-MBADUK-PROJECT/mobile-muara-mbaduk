package com.example.muara_mbaduk.data.services;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.model.request.UserLoginRequest;
import com.example.muara_mbaduk.model.request.UserValidateRequest;
import com.example.muara_mbaduk.model.response.UserLoginResponse;
import com.example.muara_mbaduk.model.response.UserValidateResponse;
import com.example.muara_mbaduk.data.remote.UserServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginService {

    private UserServiceApi userServiceApi;
    private RealmHelper realmHelper;

    public UserLoginService(UserServiceApi userServiceApi) {
        this.userServiceApi = userServiceApi;
        realmHelper = new RealmHelper(Realm.getDefaultInstance());
    }
    public void login(UserLoginRequest token , Context context , View view , GoogleSignInAccount account){
        SharedPreferences sharedPreferences = context.getSharedPreferences("jwt",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ProgressDialog silahkan_menunggu_validasi = UtilMethod.getProgresIndicator("Silahkan Menunggu Validasi", context);
        silahkan_menunggu_validasi.show();
        Call<UserLoginResponse> userLoginResponseCall = this.userServiceApi.loginUser(token , RetrofitClient.getApiKey());
        userLoginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                silahkan_menunggu_validasi.dismiss();
                UserLoginResponse userLoginResponse = response.body();
                UserModel userModel = realmHelper.findByJwt(userLoginResponse.getData());
                editor.putString("jwt" , userLoginResponse.getData());
                editor.apply();
                Intent intent = new Intent(context , HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                if(userModel != null){
                    Log.i("userlogin", "user found");
                    context.startActivity(intent);
                }else{
                    // validate token jwt and get response
                    UserValidateRequest userValidateRequest = new UserValidateRequest();
                    userValidateRequest.setToken(userLoginResponse.getData());
                    Call<UserValidateResponse> responseCall = userServiceApi.validateTokenJwt(RetrofitClient.getApiKey(), userValidateRequest);
                    responseCall.enqueue(new Callback<UserValidateResponse>() {
                        @Override
                        public void onResponse(Call<UserValidateResponse> call, Response<UserValidateResponse> response) {
                            UserModel userModel1 = new UserModel();
                            UserValidateResponse body = response.body();
                            userModel1.setId(body.getData().getId());
                            userModel1.setJwt(userLoginResponse.getData());
                            userModel1.setFullname(body.getData().getFullname());
                            userModel1.setImages(body.getData().getImages());
                            userModel1.setEmail(body.getData().getEmail());
                            realmHelper.saveUser(userModel1);
                            context.startActivity(intent);
                        }
                        @Override
                        public void onFailure(Call<UserValidateResponse> call, Throwable t) {
                            Log.e("error", "onFailure: "+t.getMessage());
                            Snackbar snackbar = Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                            snackbar.setTextColor(ContextCompat.getColor(context, R.color.white));
                            snackbar.show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
            silahkan_menunggu_validasi.dismiss();
            Snackbar snackbar = UtilMethod.genereateErrorsSnackbar(view, context, t.getMessage());
            snackbar.show();
            }
        });
    }

    public UserModel getUserLogiByJwt(String jwt){
        return realmHelper.findByJwt(jwt);
    }



}
