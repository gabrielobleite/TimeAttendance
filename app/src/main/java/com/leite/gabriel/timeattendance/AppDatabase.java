package com.leite.gabriel.timeattendance;

import androidx.room.*;

@Database(entities = {TimeCheck.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TimeCheckDao timeCheckDao();
}
