package com.example.muara_mbaduk.data.model;

import java.util.ArrayList;
import java.util.List;

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
