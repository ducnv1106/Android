package com.t3h.mp3music.model;

import android.provider.MediaStore;

import java.io.Serializable;

public class Artist extends BaseModel implements Serializable {

    @FieldInfo(nameInfo = MediaStore.Audio.Artists.ARTIST)
    private String name;

    @FieldInfo(nameInfo = MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    private int numberSong;

    @FieldInfo(nameInfo = MediaStore.Audio.Albums.ALBUM_ART)
    private String image;

    @FieldInfo(nameInfo = MediaStore.Audio.Artists._ID)
    private String idArtist;


    public Artist(String name, int numberSong, String image, String idArtist) {
        this.name = name;
        this.numberSong = numberSong;
        this.image = image;
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }


    public int getNumberSong() {
        return numberSong;
    }

    public String getImage() {
        return image;
    }

    public String getIdArtist() {
        return idArtist;
    }
}
