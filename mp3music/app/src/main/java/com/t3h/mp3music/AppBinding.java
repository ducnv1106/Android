package com.t3h.mp3music;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;

public class AppBinding {
    private static SimpleDateFormat format=new SimpleDateFormat("mm:ss");

    @BindingAdapter("time")
    public static void setTime(TextView tv, int duration){
        String time=format.format(duration);
        tv.setText(time);
    }
}
