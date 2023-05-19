package com.example.muara_mbaduk.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PaymentCheckout implements Serializable {
    private String id;
    private String order_id;
    private String user_id;
    private String transaction_id;
    private boolean camping;
    private String type;
    private String date;
    private String date_types;
    @JsonProperty("gross_amount")
    private long gross_amount;
    private String barcode;
    private long expire_at;
    private String status;
    private String created_at;
    private String updated_at;


    public void setGross_amount(long gross_amount) {
        this.gross_amount = gross_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public boolean isCamping() {
        return camping;
    }

    public void setCamping(boolean camping) {
        this.camping = camping;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_types() {
        return date_types;
    }

    public void setDate_types(String date_types) {
        this.date_types = date_types;
    }

    public long getGross_amount() {
        return gross_amount;
    }

    public void setGroos_amount(int groos_amount) {
        this.gross_amount = groos_amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(long expire_at) {
        this.expire_at = expire_at;
    }
}
