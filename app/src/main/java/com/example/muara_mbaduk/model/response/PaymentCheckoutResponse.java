package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.ParrentModel;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;

public class PaymentCheckoutResponse extends ParrentModel {
    private PaymentCheckout data;

    public PaymentCheckout getData() {
        return data;
    }

    public void setData(PaymentCheckout data) {
        this.data = data;
    }
}
