package com.t3h.mp3music.model;

import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.Serializable;

public class Song extends BaseModel implements Serializable  {

    @FieldInfo(nameInfo = MediaStore.Audio.Media.DATA)
    private String data;

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


    @FieldInfo(nameInfo = MediaStore.Audio.Media.ALBUM_KEY)
    private String keyAlbum;


    private String img;

    public Song(String data, String title, int size, int duration, String artist, String idArtist, String keyAlbum, String img) {
        this.data = data;
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.idArtist = idArtist;
        this.keyAlbum = keyAlbum;
        this.img = img;
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

    public String getKeyAlbum() {
        return keyAlbum;
    }

    public String getImg() {
        return img;
    }

    public String getData() {
        return data;
    }
}
