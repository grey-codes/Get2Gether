package com.get2gether.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by grey on 5/9/2020.
 */

public class NotificationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Get2Gether.db";
    private static final int DATABASE_VERSION = 1;

    // constructor
    public NotificationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates the recipes table when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the recipes table
        final String CREATE_NOTIFICATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Notification.TABLE_NAME + "(" +
                        DatabaseDescription.Notification._ID + " integer primary key, " +
                        DatabaseDescription.Notification.COLUMN_ID + " integer);";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE); // create the recipes table
    }

    // normally defines how to upgrade the database when the schema changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
    }

}
