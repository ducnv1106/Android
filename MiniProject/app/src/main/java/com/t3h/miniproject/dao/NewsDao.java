package com.t3h.miniproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.t3h.miniproject.model.News;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE id=:id")
    News getFromID(int id);

    @Insert
    void insert(News...news);


   @Query("DELETE FROM news")
    void deleteAll();

    @Update
    void Update(News...news);

    @Delete
    void delete(News...news);

    @Query("DELETE FROM news LIMIT 1")
    void deleteDataBaseRowOne();
}
