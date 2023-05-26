package com.example.muara_mbaduk.model;

import com.example.muara_mbaduk.model.entity.Pages;

import java.io.Serializable;

public class ParrentModel  {
    private int code;
    private String status;

    private Pages page;

    public Pages getPage() {
        return page;
    }

    public void setPage(Pages page) {
        this.page = page;
    }

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
}
