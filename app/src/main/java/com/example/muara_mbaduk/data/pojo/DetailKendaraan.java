package com.example.muara_mbaduk.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class DetailKendaraan {
    private ArrayList<String> dataMobil;
    private ArrayList<String> dataMotor;

    public ArrayList<String> getDataMobil() {
        return dataMobil;
    }

    public void setDataMobil(ArrayList<String> dataMobil) {
        this.dataMobil = dataMobil;
    }

    public ArrayList<String> getDataMotor() {
        return dataMotor;
    }

    public void setDataMotor(ArrayList<String> dataMotor) {
        this.dataMotor = dataMotor;
    }
}
