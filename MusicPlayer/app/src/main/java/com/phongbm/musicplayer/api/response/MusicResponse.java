package com.phongbm.musicplayer.api.response;

import com.google.gson.annotations.SerializedName;
import com.phongbm.musicplayer.model.Music;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicResponse implements Serializable{

    @SerializedName("result")
    private ArrayList<Music> arr;

    public ArrayList<Music> getArr() {
        return arr;
    }
}
