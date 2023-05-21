package com.example.muara_mbaduk.model.entity;

import java.io.Serializable;

public class Bank implements Serializable {

    private String bank;
    private String va_number;


    public String getBank() {
        return bank;
    }

    public String getVa_number() {
        return va_number;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setVa_number(String va_number) {
        this.va_number = va_number;
    }

}
