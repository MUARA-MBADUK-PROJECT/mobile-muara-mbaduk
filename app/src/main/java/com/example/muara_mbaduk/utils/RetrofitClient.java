package com.example.muara_mbaduk.utils;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://muarambaduk.aristocaesar.my.id/api/v1/";

    private static Retrofit retrofit;

    private RetrofitClient() {

    }

    public static String getApiKey(){
        return "21a5abce903553898ace6ab4915c60a50b8a97e9";
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL).
                    addConverterFactory(JacksonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }

}
