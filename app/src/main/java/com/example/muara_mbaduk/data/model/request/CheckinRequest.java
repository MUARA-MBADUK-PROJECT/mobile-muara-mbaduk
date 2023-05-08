package com.example.muara_mbaduk.data.model.request;

public class CheckinRequest {
    private String date;
    private boolean camping;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCamping() {
        return camping;
    }

    public void setCamping(boolean camping) {
        this.camping = camping;
    }
}
