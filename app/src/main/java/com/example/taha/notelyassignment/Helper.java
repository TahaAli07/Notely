package com.example.taha.notelyassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "notely.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String str = "CREATE TABLE NOTELY(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT ,  DESCRIPTION TEXT, TIME LONG ,STAR INTEGER DEFAULT 0, HEART INTEGER DEFAULT 0)";

        String insertArecord="INSERT INTO NOTELY VALUES(1,'NOTE ','DESCRIPTION OF NOTE 1',12121212545565,0,0 )";
        String insertArecord2="INSERT INTO NOTELY VALUES(2,'NOTE NOTE ','DESCRIPTION OF NOTE 2',12121212545565,0,0 )";
        String insertArecord3="INSERT INTO NOTELY VALUES(3,'NOTE NOTE NOTE','DESCRIPTION OF NOTE 3',12121212545565,0,0 )";


        db.execSQL(str);
        db.execSQL(insertArecord);
        db.execSQL(insertArecord2);
        db.execSQL(insertArecord3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
