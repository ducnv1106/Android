package com.t3h.mp3music.systemdata;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.model.Artist;
import com.t3h.mp3music.model.ImageArtist;
import com.t3h.mp3music.model.Song;

import java.util.ArrayList;

public class SystemData {

    private ArrayList<ImageArtist> arrayList = new ArrayList<>();
    private ContentResolver resolver;

    public SystemData(Context context) {
        resolver = context.getContentResolver();
    }


    public ArrayList<Album> getAlbum() {
        ArrayList<Album> arr = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();// chỉ con trỏ tới phần tử đâu tiền của list album

        int indexName = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
        int indexImage = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
        int indexKeyAlbum=cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY);
        int indexNumberSong=cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

        while (cursor.isAfterLast() == false) {
           for(int i=0;i<cursor.getColumnCount();i++){
               Log.e(cursor.getColumnName(i),cursor.getString(i)+"");

           }
           Log.e("----","----");

            String name = cursor.getString(indexName);
            String artist = cursor.getString(indexArtist);
            String image = cursor.getString(indexImage);
            String keyAlbum=cursor.getString(indexKeyAlbum);
            int numberSong=cursor.getInt(indexNumberSong);
            if (arrayList.size() == 0) {
                ImageArtist imageArtist = new ImageArtist(image, artist);
                arrayList.add(imageArtist);
            } else {
                if (checkNameArtist(arrayList, artist)) {
                    ImageArtist imageArtist = new ImageArtist(image, artist);
                    arrayList.add(imageArtist);
                }
            }
            Album album = new Album(name, artist, image,keyAlbum,numberSong);
            arr.add(album);
            cursor.moveToNext();
        }

        return arr;
    }

    public ArrayList<Artist> getArtist() {
        ArrayList<Artist> arr = new ArrayList<>();

        Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();

        int indexName = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
        int indexNumberSong = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
        int indexIdArtist = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);

        while (cursor.isAfterLast() == false) {

            String nameArtist = cursor.getString(indexName);
            int numberSong = cursor.getInt(indexNumberSong);
            String idArtist = cursor.getString(indexIdArtist);

            Artist artist = new Artist(nameArtist, numberSong, getImageArtist(nameArtist), idArtist);

            arr.add(artist);
            cursor.moveToNext();
        }
        return arr;
    }

    public boolean checkNameArtist(ArrayList<ImageArtist> arr, String artist) {
        for (int i = 0; i < arr.size(); i++) {
            if (artist.equals(arr.get(i).getArtist())) {
                return false;
            }
        }
        return true;
    }

    public String getImageArtist(String nameArtist) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (nameArtist.equals(arrayList.get(i).getArtist())) {
                return arrayList.get(i).getImage();
            }
        }
        return null;
    }

    public ArrayList<ImageArtist> getArrayList() {
        return arrayList;
    }

    public ArrayList<Song> getDataSong() {
        ArrayList<Song> dataSong = new ArrayList<>();

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();

        int indexData= cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexIdArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
        int indexKeyAlbum=cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY);



        while (cursor.isAfterLast() == false) {
//            for (int i=0;i<cursor.getColumnCount();i++){
//                Log.d(cursor.getColumnName(i),cursor.getString(i)+" ");
//                // get CloumnName Song
//
//            }
//            Log.d("-------------","-----------");
            String data=cursor.getString(indexData);
            String tilte = cursor.getString(indexTitle);
            int size = cursor.getInt(indexSize);
            int duration = cursor.getInt(indexDuration);
            String artist = cursor.getString(indexArtist);
            String idArtist = cursor.getString(indexIdArtist);
            String idAlbum=cursor.getString(indexKeyAlbum);


            Song song = new Song(data,tilte, size, duration, artist, idArtist,idAlbum,getImageArtist(artist));

            dataSong.add(song);
            cursor.moveToNext();
        }

        return dataSong;
    }

    public ArrayList<Song> getArrSongArtist(String id) {
        ArrayList<Song> data = new ArrayList<>();

        for (Song song : getDataSong()) {
            if (song.getIdArtist().equals(id)) {
                data.add(song);
            }
        }
        return data;
    }
    public ArrayList<Song> getArrSongAlbum(String keyAlbum){
        ArrayList<Song> data=new ArrayList<>();

        for(Song song: getDataSong()){
            if(song.getKeyAlbum().equals(keyAlbum)){
                data.add(song);
            }
        }
        return data;
    }
}
