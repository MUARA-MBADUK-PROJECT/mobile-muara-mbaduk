package com.example.muara_mbaduk.utils;

import retrofit2.Retrofit;

public class RetrofitClient {

    private static Retrofit retrofit;

    private RetrofitClient(){

    }
    public static Retrofit getInstance() {
        if(retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl("baseurl").build();
        }
        return retrofit;
    }

}
