package com.example.muara_mbaduk.model.request;

import java.util.ArrayList;
import java.util.List;

public class ReviewPaymenetRequest {


    private List<String> id_package = new ArrayList<>();
    private String id_payment;
    private String id_user;
    private int star;
    private String description;


    public List<String> getId_package() {
        return id_package;
    }

    public void setId_package(List<String> id_package) {
        this.id_package = id_package;
    }

    public String getId_payment() {
        return id_payment;
    }

    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
