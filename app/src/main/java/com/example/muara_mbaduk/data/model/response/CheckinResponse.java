package com.example.muara_mbaduk.data.model.response;

import com.example.muara_mbaduk.data.model.entity.Packages;
import com.example.muara_mbaduk.data.model.entity.TiketCheckin;

import java.util.List;

public class CheckinResponse {
    private String date;
    private String date_types;
    private boolean camping;

    private List<TiketCheckin> tickets;

    private List<Packages> packages;

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_types() {
        return date_types;
    }

    public void setDate_types(String date_types) {
        this.date_types = date_types;
    }

    public boolean isCamping() {
        return camping;
    }

    public void setCamping(boolean camping) {
        this.camping = camping;
    }

    public List<TiketCheckin> getTickets() {
        return tickets;
    }

    public void setTickets(List<TiketCheckin> tickets) {
        this.tickets = tickets;
    }
}
