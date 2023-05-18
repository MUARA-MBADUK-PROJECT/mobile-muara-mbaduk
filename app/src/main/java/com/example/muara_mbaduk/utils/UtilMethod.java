package com.example.muara_mbaduk.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.Errors;

import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;

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

    
}
