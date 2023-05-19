package com.example.muara_mbaduk;

import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PackagesServiceApiTest {

    @Test
    public void testGetAllPackagesSuccess() throws IOException {
        Retrofit retrofit = RetrofitClient.getInstance();
        PackagesServiceApi packagesServiceApi = retrofit.create(PackagesServiceApi.class);
        Call<PackagesResponse> packages = packagesServiceApi.getAllPackages(RetrofitClient.getApiKey());
        PackagesResponse packagesResponse = packages.execute().body();
        packagesResponse.getData().forEach(packagesOrder -> {
            System.out.println(packagesOrder.getTitle());
        } );
    }
}
