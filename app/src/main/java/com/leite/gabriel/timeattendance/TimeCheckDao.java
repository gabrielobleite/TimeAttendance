package com.leite.gabriel.timeattendance;

import java.util.List;

import androidx.room.*;

@Dao
public interface TimeCheckDao {
    @Query("SELECT * FROM TimeCheck")
    List<TimeCheck> getAll();

    @Query("SELECT * FROM TimeCheck WHERE date = :date")
    List<TimeCheck> loadByDate(String date);

    @Query("SELECT * FROM TimeCheck WHERE id = :id LIMIT 1")
    TimeCheck load(long id);

    @Insert
    void insertAll(TimeCheck... users);

    @Delete
    void delete(TimeCheck user);

    @Update
    void update(TimeCheck order);
}
