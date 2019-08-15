package com.t3h.mp3music.model;

import android.provider.MediaStore;

import java.io.Serializable;

public class Song extends BaseModel  {

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


    @FieldInfo(nameInfo = MediaStore.Audio.Media.ALBUM_ID)
    private String idAlbum;

    public Song(String title, int size, int duration, String artist, String idArtist, String idAlbum) {
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.idArtist = idArtist;
        this.idAlbum = idAlbum;
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

    public String getIdAlbum() {
        return idAlbum;
    }
}
