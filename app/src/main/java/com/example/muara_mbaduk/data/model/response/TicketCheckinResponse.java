package com.example.muara_mbaduk.data.model.response;

public class TicketCheckinResponse {
    private int code;
    private String status;
    private CheckinResponse data;

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

    public CheckinResponse getData() {
        return data;
    }

    public void setData(CheckinResponse data) {
        this.data = data;
    }
}
