package com.example.muara_mbaduk.data.dto;

import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataOrder implements Serializable {


    private List<TiketPurchaseRequest> deque = new ArrayList<>();
    private List<TiketPurchaseRequest> dataMobil = new ArrayList<>();
    private List<TiketPurchaseRequest> dataMotor = new ArrayList<>();

    public List<TiketPurchaseRequest> getDataMobil() {
        return dataMobil;
    }

    public void setDataMobil(List<TiketPurchaseRequest> dataMobil) {
        this.dataMobil = dataMobil;
    }

    public List<TiketPurchaseRequest> getDataMotor() {
        return dataMotor;
    }

    public void setDataMotor(List<TiketPurchaseRequest> dataMotor) {
        this.dataMotor = dataMotor;
    }

    public List<TiketPurchaseRequest> getDeque() {
        return deque;
    }

    public void setDeque(List<TiketPurchaseRequest> deque) {
        this.deque = deque;
    }
}
