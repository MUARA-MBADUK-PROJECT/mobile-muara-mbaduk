package com.example.muara_mbaduk.data.remote;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuan2Response;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuanResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SyaratDanKetentuanServiceApi {
    @GET("pages/")
    Call<SyaratDanKetentuanResponse> getAllSK (@Header("Authorization")String authHeader);
    @GET("pages/738b3222-8a67-4347-91d5-2fdd72b4c0e3")
    Call<SyaratDanKetentuanResponse> getSK1 (@Header("Authorization")String authHeader);
    @GET("pages/ba7319f1-8107-4c43-b375-435f3ba3d478")
    Call<SyaratDanKetentuan2Response> getSk2(@Header("Authorization") String authHeader);
}
