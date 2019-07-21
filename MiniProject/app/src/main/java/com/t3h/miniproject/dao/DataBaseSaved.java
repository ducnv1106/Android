package com.t3h.miniproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.t3h.miniproject.model.News;

@Database(entities = News.class, version = 1)
public abstract class DataBaseSaved extends RoomDatabase {

    public static DataBaseSaved dataBaseSaved;
    public abstract NewsDao getNewsDao();

    public static DataBaseSaved getInstance(Context context){
        if(dataBaseSaved==null){
            dataBaseSaved= Room.databaseBuilder(context,DataBaseSaved.class,"saved-database")
                    .allowMainThreadQueries()
                    .build();

        }
        return dataBaseSaved;
    }
}
