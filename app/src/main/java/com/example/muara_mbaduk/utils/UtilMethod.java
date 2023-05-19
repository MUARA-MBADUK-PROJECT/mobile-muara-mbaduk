package com.example.muara_mbaduk.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.Errors;

import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UtilMethod {

    public static int getStartIndex(String kalimat, String kata) {
        return kalimat.indexOf(kata);
    }

    public static int getLastIndex(String kalimat, String kata) {
        return getStartIndex(kalimat, kata) + kata.length();
    }

    public static void setFont(TextView textView, Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/poppins.ttf");
        textView.setTypeface(typeface);
    }

    public static ProgressDialog getProgresIndicator(String message, Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(message);
        return pd;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isWeekend() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static Errors generateErrors(String json){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Errors.class);
    }


    public static Snackbar genereateErrorsSnackbar(View view , Context context , String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        snackbar.setTextColor(ContextCompat.getColor(context, R.color.white));
        return snackbar;
    }


    public static String generateMessage(List<Map<String ,String>> products , String tanggalCamping , String namaPemesan , String tanggalPemesanan , String email , String totalHarga, String catatan , String nowhatshap){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pemesanan Camping di Muarambaduk\nHalo \nSaya ingin memesan paket camping di Muarambaduk pada tanggal "+tanggalCamping+" Berikut adalah informasi pemesan:\n\n"+"Nama Pemesan: "+namaPemesan+"\nTanggal Pemesanan: "+tanggalPemesanan+"\nEmail: "+email+"\n"+"No WhatshApp:"+nowhatshap+"\n"+
                "Daftar Alat yang Dipesan:\n\n");
        products.forEach(stringStringMap -> {
            stringBuilder.append("- " + stringStringMap.get("name")+" (jumlah) "+stringStringMap.get("count") + ", harga satuan : "+stringStringMap.get("price")+"\n");
        });
        stringBuilder.append("\n" +
                "Total Harga: "+totalHarga+"\n" +
                "\n" +
                "Catatan:"+catatan+"");
        return stringBuilder.toString();
//        return "Halo,\nIni pesan pertama.\nIni pesan kedua.";
    }

    public static boolean isEmailValid(String email) {
        // Pola regular expression untuk memeriksa alamat email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public static String getJwt(Context context){
        SharedPreferences sh = context.getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        return jwt;
    }
    
}
