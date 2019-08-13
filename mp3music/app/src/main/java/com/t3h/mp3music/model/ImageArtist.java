package com.t3h.mp3music.model;

import android.provider.MediaStore;

public class ImageArtist {


    private String image;

    private String artist;

    public String getArtist() {
        return artist;
    }

    public String getImage() {
        return image;
    }

    public ImageArtist(String image, String artist) {
        this.image = image;
        this.artist = artist;
    }
}
