package com.faa1192.ISTQBTestExam.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "app_db";
    public static final String TABLE_APP_VERSION = "APPVERSION";
    public static final String TABLE_QUESTION_VERSION = "QUESTIONVERSION";
    public static final String TABLE_QUESTION = "QUESTION";
    public static final String TABLE_VARIANTS = "VARIANT";
    public static final String TABLE_RESPONSES = "RESPONSE";
    public static final String TABLE_RESOURCES = "RESOURCE";
    public static final int DB_VERSION = 1;
    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_APP_VERSION + " (K TEXT, V TEXT);");
            db.execSQL("insert into " + TABLE_APP_VERSION + "(K, V) Values ('current', '0')");
            db.execSQL("CREATE TABLE " + TABLE_QUESTION_VERSION + " (K TEXT, V TEXT);");
            db.execSQL("insert into " + TABLE_QUESTION_VERSION + "(K, V) Values ('current', '0')");

            db.execSQL("CREATE TABLE " + TABLE_QUESTION + " (_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, NUM INTEGER, RUS TEXT, ENG TEXT, RESOURCE_NUMBER INTEGER);");

            db.execSQL("CREATE TABLE " + TABLE_VARIANTS + " (_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, NUM INTEGER, QUESTION_NUMBER INTEGER, RUS TEXT, ENG TEXT, IS_CORRECT TEXT);");

            db.execSQL("CREATE TABLE " + TABLE_RESPONSES + " (_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, DATE TEXT, QUESTION_NUMBER INTEGER, USER_ANSWER INTEGER, CORRECT_ANSWER INTEGER, SESSION_ID TEXT);");

            db.execSQL("CREATE TABLE " + TABLE_RESOURCES + " (_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, QUESTION_NUMBER INTEGER, PIC BLOB);");
        } catch (SQLException e) {
            Log.e("my", e.getMessage());
            for (int i = 0; i < e.getStackTrace().length; i++) {
                Log.e("my", e.getStackTrace()[i].toString());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static synchronized DBHelper getDB(Context context) {
        if (instance == null) {
            Log.e("my", "FIRST INIT");
            instance = new DBHelper(context);
            return instance;
        }
        Log.e("my", "SECOND INIT");
        return instance;
    }
}