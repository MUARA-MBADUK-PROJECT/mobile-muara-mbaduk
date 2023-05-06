package com.example.muara_mbaduk;

import com.example.muara_mbaduk.data.model.Barang;
import com.example.muara_mbaduk.data.remote.BarangApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitTest {

    @Test
    public void testCallDumyApi() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
        BarangApi barangApi = retrofit.create(BarangApi.class);
        Call<List<Barang>> allBarang = barangApi.getAllBarang();
        List<Barang> barang = allBarang.execute().body();
    }
}
