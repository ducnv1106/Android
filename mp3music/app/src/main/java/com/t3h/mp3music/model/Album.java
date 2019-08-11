package com.t3h.mp3music.model;

import android.provider.MediaStore;

public class Album extends BaseModel {

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM)
    private String name;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ARTIST)
    private String artist;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM_ART)
    private String image;

    public Album(String name, String artist, String image) {
        this.name = name;
        this.artist = artist;
        this.image = image;
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
}
