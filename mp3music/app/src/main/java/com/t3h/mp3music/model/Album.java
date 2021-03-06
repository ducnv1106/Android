package com.t3h.mp3music.model;

import android.provider.MediaStore;

import java.io.Serializable;

public class Album extends BaseModel implements Serializable {

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM)
    private String name;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ARTIST)
    private String artist;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM_ART)
    private String image;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM_KEY)
    private String keyAlbum;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.NUMBER_OF_SONGS)
    private int numberSong;

    public Album(String name, String artist, String image, String keyAlbum, int numberSong) {
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.keyAlbum = keyAlbum;
        this.numberSong = numberSong;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImage() {
        return image;
    }

    public String getKeyAlbum() {
        return keyAlbum;
    }

    public int getNumberSong() {
        return numberSong;
    }
}
