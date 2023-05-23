package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.entity.Packages;

import java.util.ArrayList;

public class PackageBySlugResponse {
    private int code;
    private String status;
    Packages data;

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

    public Packages getData() {
        return data;
    }

    public void setData(Packages data) {
        this.data = data;
    }
}
