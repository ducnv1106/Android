package com.t3h.mp3music;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

public class AppBinding {
    private static SimpleDateFormat format=new SimpleDateFormat("mm:ss");

    @BindingAdapter("time")
    public static void setTime(TextView tv, int duration){
        String time=format.format(duration);
        tv.setText(time);
        // chuyển đổi dữ liệu sang texview  item_song
    }

    @BindingAdapter("image")
    public static void setImg(ImageView iv,String image){
        Glide.with(iv).load(image).into(iv);
    }
    @BindingAdapter("numberSong")
    public static void setNumberTrack(TextView tv,int number){
        String numberTrack="Bài Hát: "+String.valueOf(number);
        tv.setText(numberTrack);
    }
}
