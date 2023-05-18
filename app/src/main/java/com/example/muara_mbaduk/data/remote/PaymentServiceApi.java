package com.example.muara_mbaduk.data.remote;

import com.example.muara_mbaduk.model.request.CheckoutTicketRequest;
import com.example.muara_mbaduk.model.response.PaymentCheckoutResponse;
import com.example.muara_mbaduk.model.response.PaymentHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentServiceApi {
    @POST("payments/cash")
    Call<PaymentCheckoutResponse> checkoutCash(@Header("Authorization") String authHeader, @Body CheckoutTicketRequest checkoutTicketRequest);
    @POST("payments/bank")
    Call<PaymentCheckoutResponse> checkoutBank(@Header("Authorization") String authHeader, @Body CheckoutTicketRequest checkoutTicketRequest);
    @GET("payments/user/{id}")
    Call<PaymentHistoryResponse> findAllPayment(@Header("Authorization") String authHeader, @Path("id") String id);

}
