package com.t3h.miniproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.t3h.miniproject.model.News;
import com.t3h.miniproject.model.NewsSaved;

@Database(entities = NewsSaved.class, version = 1)
public abstract class DataBaseSaved extends RoomDatabase {

    public static DataBaseSaved dataBaseSaved;
    public abstract NewsSavedDao getNewsSavedDao();

    public static DataBaseSaved getInstance(Context context){
        if(dataBaseSaved==null){
            dataBaseSaved= Room.databaseBuilder(context,DataBaseSaved.class,"saved-database")
                    .allowMainThreadQueries()
                    .build();

        }
        return dataBaseSaved;
    }
}
