package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.data.model.Barang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BarangApi {

    @GET("barang")
    Call<List<Barang>> getAllBarang();

}
