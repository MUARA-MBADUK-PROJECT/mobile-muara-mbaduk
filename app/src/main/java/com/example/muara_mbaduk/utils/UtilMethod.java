package com.example.muara_mbaduk.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

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
}
