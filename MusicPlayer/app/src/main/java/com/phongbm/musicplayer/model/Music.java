package com.phongbm.musicplayer.model;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

public class Music extends MP3Media {

    @SerializedName("title")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.TITLE)
    private String title;

    @SerializedName("url")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.DATA)
    private String data;

    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.DURATION)
    private int duration;

    @SerializedName("artist")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ARTIST)
    private String artist;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ALBUM)
    private String album;


    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
