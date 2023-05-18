package com.example.muara_mbaduk.model.entity;


import java.io.Serializable;

public class TiketPurchaseRequest implements Serializable {

    private String id; // id tiket
    private String name; // name of user  if vehicle set null
    private String identity; // identity card or number identity vehicle


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
