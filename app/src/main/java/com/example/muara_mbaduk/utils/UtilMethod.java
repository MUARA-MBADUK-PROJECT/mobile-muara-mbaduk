package com.example.muara_mbaduk.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilMethod {

    public static int getStartIndex(String kalimat, String kata) {
        return kalimat.indexOf(kata);
    }

    public static int getLastIndex(String kalimat, String kata) {
        return getStartIndex(kalimat, kata) + kata.length();
    }

    public static void setFont(TextView textView , Context context){
        Typeface typeface  = Typeface.createFromAsset(context.getAssets() , "font/poppins.ttf");
        textView.setTypeface(typeface);
    }

}
