package com.example.muara_mbaduk.data.model.response;

import com.example.muara_mbaduk.data.model.response.CheckinData;

public class TicketCheckinResponse {
    private int code;
    private String status;
    private CheckinData data;

//    private List<ErrorsResponse> errors;
//
//
//    public List<ErrorsResponse> getErrors() {
//        return errors;
//    }

//    public void setErrors(List<ErrorsResponse> errors) {
//        this.errors = errors;
//    }

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

    public CheckinData getData() {
        return data;
    }

    public void setData(CheckinData data) {
        this.data = data;
    }
}
