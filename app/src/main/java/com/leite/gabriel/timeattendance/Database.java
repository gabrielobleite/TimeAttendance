package com.leite.gabriel.timeattendance;

import android.content.Context;

import androidx.room.Room;

public class Database {
    private static AppDatabase db;

    public static AppDatabase Instance() {
        return db;
    }

    public static void InstantiateDatabase(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "TimeAttendance").build();
    }

    public static TimeCheckDao TimeCheck() {
        return db.timeCheckDao();
    }
}
