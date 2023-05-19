package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.entity.Faq;

import java.util.List;

public class FaqResponse {
    private int code;
    private String status;
    List<Faq> data;

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

    public List<Faq> getData() {
        return data;
    }

    public void setData(List<Faq> data) {
        this.data = data;
    }
}
