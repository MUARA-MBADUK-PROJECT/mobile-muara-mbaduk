package com.example.muara_mbaduk.data.remote;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuan2Response;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuanResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SyaratDanKetentuanServiceApi {
    @GET("pages/")
    Call<SyaratDanKetentuanResponse> getAllSK (@Header("Authorization")String authHeader);
    @GET("pages/1f274310-ec90-4d06-a5d7-a9561001f52d")
    Call<SyaratDanKetentuanResponse> getSK1 (@Header("Authorization")String authHeader);
    @GET("pages/b6e07109-8884-4954-ae48-3c629d39ed06")
    Call<SyaratDanKetentuan2Response> getSk2(@Header("Authorization") String authHeader);

}
