package com.leite.gabriel.timeattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public final class Repository {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Repository() {}

    private static Repository instance;

    public static Repository getInstance() {
        if(instance == null)
            instance = new Repository();
        return instance;
    }

    private static final String SQL_CREATE_TIME =
            "CREATE TABLE " + TimeCheck.TABLE_NAME + " (" +
                    TimeCheck._ID + " INTEGER PRIMARY KEY," +
                    TimeCheck.COLUMN_NAME_DATE + " TEXT," +
                    TimeCheck.COLUMN_NAME_TIME + " TEXT)";

    private static final String SQL_DELETE_TIME =
            "DROP TABLE IF EXISTS " + TimeCheck.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class TimeCheck implements BaseColumns {
        public static final String TABLE_NAME = "timecheck";
        public static final String COLUMN_NAME_DATE = "date_check";
        public static final String COLUMN_NAME_TIME = "time_check";
    }

    public class TimeCheckReaderDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "TimeAttendance.db";

        public TimeCheckReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TIME);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_TIME);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public long CreateTimeCheck(Context context, String sDate, String sTime) {
        Repository.TimeCheckReaderDbHelper dbHelper = new Repository.TimeCheckReaderDbHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TimeCheck.COLUMN_NAME_DATE, sDate);
        values.put(TimeCheck.COLUMN_NAME_TIME, sTime);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TimeCheck.TABLE_NAME, null, values);
        return newRowId;
    }

    public List RecoverTimeCheck(Context context, String sDate) {
        Repository.TimeCheckReaderDbHelper dbHelper = new Repository.TimeCheckReaderDbHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                TimeCheck.COLUMN_NAME_DATE,
                TimeCheck.COLUMN_NAME_TIME
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = TimeCheck.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { sDate };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = TimeCheck.COLUMN_NAME_TIME + " ASC";

        Cursor cursor = db.query(
                TimeCheck.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList();
        while(cursor.moveToNext()) {
            String itmTime = cursor.getString(cursor.getColumnIndexOrThrow(TimeCheck.COLUMN_NAME_TIME));
            itemIds.add(itmTime);
        }
        cursor.close();

        return itemIds;
    }
}
