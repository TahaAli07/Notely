package com.example.taha.notelyassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "notely.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String str = "CREATE TABLE NOTELY(ID INT ,TEXT TITLE , TEXT DESCRIPTION , TEXT TIME )";

        db.execSQL(str);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
