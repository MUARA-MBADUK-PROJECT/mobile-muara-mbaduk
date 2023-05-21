package com.example.muara_mbaduk.data.remote;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuan2Response;
import com.example.muara_mbaduk.model.response.SyaratDanKetentuanResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SyaratDanKetentuanServiceApi {
    @GET("pages/")
    Call<SyaratDanKetentuanResponse> getAllSK (@Header("Authorization")String authHeader);
    @GET("pages/terms-of-service")
    Call<SyaratDanKetentuanResponse> getSK1 (@Header("Authorization")String authHeader);
    @GET("pages/terms-of-service-2")
    Call<SyaratDanKetentuan2Response> getSk2(@Header("Authorization") String authHeader);

}
