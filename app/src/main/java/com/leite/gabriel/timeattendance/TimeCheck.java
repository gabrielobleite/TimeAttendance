package com.leite.gabriel.timeattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class TimeCheck implements BaseColumns {
    private static final String TABLE_NAME = "timecheck";
    private static final String COLUMN_NAME_DATE = "date_check";
    private static final String COLUMN_NAME_TIME = "time_check";
    private static final String SQL_CREATE_TIME =
            "CREATE TABLE " + TimeCheck.TABLE_NAME + " (" +
                    TimeCheck._ID + " INTEGER PRIMARY KEY," +
                    TimeCheck.COLUMN_NAME_DATE + " TEXT," +
                    TimeCheck.COLUMN_NAME_TIME + " TEXT)";

    private static final String SQL_DELETE_TIME =
            "DROP TABLE IF EXISTS " + TimeCheck.TABLE_NAME;

    private long id;
    private String date;
    private String time;

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private class TimeCheckReaderDbHelper extends SQLiteOpenHelper {
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

    public long Save(Context context) {
        if(getId()==0)
            return Create(context);
        else
            return Update(context);
    }

    private long Create(Context context) {
        TimeCheckReaderDbHelper dbHelper = new TimeCheckReaderDbHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TimeCheck.COLUMN_NAME_DATE, getDate());
        values.put(TimeCheck.COLUMN_NAME_TIME, getTime());

        // Insert the new row, returning the primary key value of the new row
        id = db.insert(TimeCheck.TABLE_NAME, null, values);
        return id;
    }

    private long Update(Context context) {
        TimeCheckReaderDbHelper dbHelper = new TimeCheckReaderDbHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TimeCheck.COLUMN_NAME_TIME, getTime());

        // Which row to update, based on the title
        String selection = TimeCheck._ID + " = ?";
        String[] selectionArgs = { Long.toString(getId()) };

        int count = db.update(
                TimeCheck.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return getId();
    }

    public List RecoverTimeCheck(Context context, String sDate) {
        TimeCheckReaderDbHelper dbHelper = new TimeCheckReaderDbHelper(context);

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
            TimeCheck item = new TimeCheck();
            item.id = cursor.getLong(cursor.getColumnIndexOrThrow(TimeCheck._ID));
            item.date = cursor.getString(cursor.getColumnIndexOrThrow(TimeCheck.COLUMN_NAME_DATE));
            item.time = cursor.getString(cursor.getColumnIndexOrThrow(TimeCheck.COLUMN_NAME_TIME));
            itemIds.add(item);
        }
        cursor.close();

        return itemIds;
    }

    public void Load(Context context, long lId) {
        TimeCheckReaderDbHelper dbHelper = new TimeCheckReaderDbHelper(context);

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
        String selection = TimeCheck._ID + " = ?";
        String[] selectionArgs = { Long.toString(lId) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = TimeCheck._ID + " ASC";

        Cursor cursor = db.query(
                TimeCheck.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        TimeCheck item;
        while(cursor.moveToNext()) {
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(TimeCheck._ID));
            this.date = cursor.getString(cursor.getColumnIndexOrThrow(TimeCheck.COLUMN_NAME_DATE));
            this.time = cursor.getString(cursor.getColumnIndexOrThrow(TimeCheck.COLUMN_NAME_TIME));
        }
        cursor.close();
    }

    public void Delete(Context context) {
        TimeCheckReaderDbHelper dbHelper = new TimeCheckReaderDbHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define 'where' part of query.
        String selection = TimeCheck._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { Long.toString(getId()) };
        // Issue SQL statement.
        int deletedRows = db.delete(TimeCheck.TABLE_NAME, selection, selectionArgs);
    }
}
