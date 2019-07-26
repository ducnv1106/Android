package com.t3h.miniproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;

@Entity(tableName = "NewsSaved")
public class NewsSaved extends News {

    @ColumnInfo(name ="path")
    private String path;

    public NewsSaved(int id, String title, String desc, String url, String img, String date, String path) {
        super(id, title, desc, url, img, date);
        this.path = path;
    }
}
