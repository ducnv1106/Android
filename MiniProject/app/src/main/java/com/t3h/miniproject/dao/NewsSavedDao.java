package com.t3h.miniproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.t3h.miniproject.model.NewsSaved;

import java.util.List;

@Dao
public interface NewsSavedDao {
    @Query("SELECT * FROM NewsSaved")
    List<NewsSaved> getAll();

    @Insert
    void insert(NewsSaved...newsSaveds);

    @Delete
    void delete(NewsSaved...newsSaveds);
}
