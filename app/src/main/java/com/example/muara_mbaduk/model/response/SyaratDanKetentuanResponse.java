package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.entity.SyaratDanKetentuan;

import java.util.List;

public class SyaratDanKetentuanResponse {
    private int code;
    private String status;
    List<SyaratDanKetentuan> data;

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

    public List<SyaratDanKetentuan> getData() {
        return data;
    }

    public void setData(List<SyaratDanKetentuan> data) {
        this.data = data;
    }
}
