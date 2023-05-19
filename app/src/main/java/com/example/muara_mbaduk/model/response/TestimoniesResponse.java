package com.example.muara_mbaduk.data.model.response;
import com.example.muara_mbaduk.data.model.entity.Testimonies;

import java.util.List;

public class TestimoniesResponse {
    private int code;
    private String status;
    List<Testimonies> data;

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

    public List<Testimonies> getData() {
        return data;
    }

    public void setData(List<Testimonies> data) {
        this.data = data;
    }
}
