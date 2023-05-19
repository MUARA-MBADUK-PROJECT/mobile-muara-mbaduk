package com.example.muara_mbaduk.data.model.response;

import com.example.muara_mbaduk.data.model.entity.News;

import java.util.List;

public class NewsResponse {
    private int code;
    private String status;
    List<News> data;

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

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }
}
