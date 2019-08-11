package com.t3h.mp3music.systemdata;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.model.Song;

import java.util.ArrayList;

public class SystemData {
    private ContentResolver resolver;

    public SystemData(Context context) {
        resolver=context.getContentResolver();
    }

    public ArrayList<Song> getSong() {
        ArrayList<Song> arr = new ArrayList<>();

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();

        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

        while (cursor.isAfterLast() == false) {
//            for (int i=0;i<cursor.getColumnCount();i++){
//                Log.d(cursor.getColumnName(i),cursor.getString(i)+"");
//                // get CloumnName Song
//
//            }
//            Log.d("-------------","-----------");
            String tilte = cursor.getString(indexTitle);
            int size = cursor.getInt(indexSize);
            int duration = cursor.getInt(indexDuration);
            String artist = cursor.getString(indexArtist);

            Song song = new Song(tilte, size, duration, artist);

            arr.add(song);
            cursor.moveToNext();
        }

        return arr;
    }

    public ArrayList<Album> getAlbum(){
        ArrayList<Album> arr=new ArrayList<>();
        Cursor cursor=resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,null,null,null,null);

        cursor.moveToFirst();// chỉ con trỏ tới phần tử đâu tiền của list album

        int indexName=cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        int indexArtist=cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
        int indexImage=cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

        while (cursor.isAfterLast()==false){
//           for(int i=0;i<cursor.getColumnCount();i++){
//               Log.e(cursor.getColumnName(i),cursor.getString(i)+"");
//
//           }
//           Log.e("----","----");
            String name=cursor.getString(indexName);
            String artist=cursor.getString(indexArtist);
            String image=cursor.getString(indexImage);

            Album album=new Album(name,artist,image);
            arr.add(album);

           cursor.moveToNext();
        }
        return  null;
    }
}
