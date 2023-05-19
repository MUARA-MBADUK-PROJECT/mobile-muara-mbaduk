package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.ParrentModel;
import com.example.muara_mbaduk.model.entity.HistoryPayment;

public class HistoryResponse  extends ParrentModel {


    private HistoryPayment data;


    public HistoryPayment getData() {
        return data;
    }

    public void setData(HistoryPayment data) {
        this.data = data;
    }
}
