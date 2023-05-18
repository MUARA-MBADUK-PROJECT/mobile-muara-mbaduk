package com.example.muara_mbaduk.model.request;

import com.example.muara_mbaduk.model.entity.PackagePurchaseRequest;
import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;

import java.util.List;
public class CheckoutTicketRequest {

    private String user_id;
    private String date;
    private boolean camping;
    private String bank;


    private List<PackagePurchaseRequest> packages;
    private List<TiketPurchaseRequest> tickets;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<PackagePurchaseRequest> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagePurchaseRequest> packages) {
        this.packages = packages;
    }

    public List<TiketPurchaseRequest> getTickets() {
        return tickets;
    }

    public void setTickets(List<TiketPurchaseRequest> tickets) {
        this.tickets = tickets;
    }


    @Override
    public String toString() {
        return "CheckoutTicketRequest{" +
                "user_id='" + user_id + '\'' +
                ", date='" + date + '\'' +
                ", camping=" + camping +
                ", bank='" + bank + '\'' +
                ", packages=" + packages +
                ", tickets=" + tickets +
                '}';
    }
}
