package com.t3h.mp3music.model;

import android.provider.MediaStore;

public class Song extends BaseModel {

    @FieldInfo(nameInfo = MediaStore.Audio.Media.TITLE)
    private String title;

    @FieldInfo(nameInfo = MediaStore.Audio.Media.SIZE)
    private int size;

    @FieldInfo(nameInfo = MediaStore.Audio.Media.DURATION)
    private int duration;

    @FieldInfo(nameInfo = MediaStore.Audio.Media.ARTIST)
    private String artist;

    @FieldInfo(nameInfo = MediaStore.Audio.Media.ARTIST_ID)
    private String idArtist;

    public Song(String title, int size, int duration, String artist, String idArtist) {
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.idArtist = idArtist;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getIdArtist() {
        return idArtist;
    }
}
