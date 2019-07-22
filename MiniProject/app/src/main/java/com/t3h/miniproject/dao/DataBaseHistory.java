package com.t3h.miniproject.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class DataBaseHistory extends RoomDatabase {
    public abstract NewsDao getNewsDao();

    public static DataBaseHistory dataBaseHistory;
    public static DataBaseHistory getInstance(Context context){
        if(dataBaseHistory==null){
            dataBaseHistory= Room.databaseBuilder(context,DataBaseHistory.class,"history-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return dataBaseHistory;
    }
}
