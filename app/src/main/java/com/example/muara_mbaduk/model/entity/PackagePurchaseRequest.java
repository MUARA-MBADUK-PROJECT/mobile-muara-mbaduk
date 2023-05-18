package com.example.muara_mbaduk.model.entity;

import java.io.Serializable;

public class PackagePurchaseRequest implements Serializable {
    private String id;
    private int quantity;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
