package com.example.muara_mbaduk.view.activity;

public class PaketCampModel {
    String namaPaket;
    String hargaPaket;
    int gambar;

    public PaketCampModel(String namaPaket, String hargaPaket, int gambar) {
        this.namaPaket = namaPaket;
        this.hargaPaket = hargaPaket;
        this.gambar = gambar;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public String getHargaPaket() {
        return hargaPaket;
    }

    public int getGambar() {
        return gambar;
    }
}
