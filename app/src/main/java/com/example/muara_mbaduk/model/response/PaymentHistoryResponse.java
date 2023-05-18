package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.ParrentModel;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;

import java.util.List;

public class PaymentHistoryResponse extends ParrentModel {

    private List<PaymentCheckout> data;

    public List<PaymentCheckout> getData() {
        return data;
    }

    public void setData(List<PaymentCheckout> data) {
        this.data = data;
    }
}
