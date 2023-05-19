package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.entity.Tiket;

import java.util.ArrayList;

public class TiketResponse {

    private int code;
    private String status;
    private ArrayList<Tiket> data;


    // Getter methods
    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Tiket> getData() {
        return data;
    }
}
