package com.example.muara_mbaduk.model.entity;

import java.io.Serializable;
import java.util.List;

public class HistoryPayment extends PaymentCheckout implements Serializable {

    private Bank va_numbers;

    private UserHistory user;


    public UserHistory getUser() {
        return user;
    }

    public void setUser(UserHistory user) {
        this.user = user;
    }

    private String barcode;
    private boolean camping;


    private List<PackagePayment> packages;
    private List<TicketPayment> tickets;


    public Bank getVa_numbers() {
        return va_numbers;
    }

    public void setVa_numbers(Bank va_numbers) {
        this.va_numbers = va_numbers;
    }

    public List<PackagePayment> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagePayment> packages) {
        this.packages = packages;
    }

    public List<TicketPayment> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketPayment> tickets) {
        this.tickets = tickets;
    }
}
