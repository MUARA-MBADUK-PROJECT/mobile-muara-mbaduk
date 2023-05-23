package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.entity.Packages;

import java.util.ArrayList;
import java.util.List;

public class PackagesResponse {
    private int code;
    private String status;
    ArrayList<Packages> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Packages> getData() {
        return data;
    }

    public void setData(ArrayList<Packages> data) {
        this.data = data;
    }
}
