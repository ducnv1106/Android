package com.t3h.miniproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.t3h.miniproject.model.News;

@Database(entities = News.class,version = 1)
public abstract class DataBaseFavorite extends RoomDatabase {
    public static DataBaseFavorite dataBaseFavorite;
    public abstract NewsDao getNewsDao();

    public static DataBaseFavorite getInstance(Context context){
        if(dataBaseFavorite==null){
            dataBaseFavorite= Room.databaseBuilder(context,DataBaseFavorite.class,"favorite-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return dataBaseFavorite;
    }
}
