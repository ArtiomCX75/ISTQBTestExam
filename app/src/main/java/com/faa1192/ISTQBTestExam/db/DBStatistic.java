package com.faa1192.ISTQBTestExam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_RESPONSES;
import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_VARIANTS;

public class DBStatistic {
    Context context;


    //_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, DATE TEXT, QUESTION_NUMBER INTEGER, USER_ANSWER INTEGER, CORRECT_ANSWER INTEGER, SESSION_ID TEXT);");


    public DBStatistic(Context context) {
        this.context = context;
    }

    public String getStatForAll() {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("Select count(*) from " + TABLE_RESPONSES, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return getStatForLastNResponse(s);
    }

    public String getStatForLastNResponse(String n) {
        try {
            SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from " + TABLE_RESPONSES + " ORDER BY _id DESC LIMIT " + n, null);
            cursor.moveToLast();
            int id = cursor.getInt(0);
            Log.e("my", "ID = " + id);
            cursor = db.rawQuery("Select count (*) from " + TABLE_RESPONSES + " WHERE _id  >= " + id, null);// USER_ANSWER = CORRECT_ANSWER ORDER BY ID DESC LIMIT " + n, null);
            cursor.moveToNext();
            int all = cursor.getInt(0);
            Log.e("my", "ALL = " + all);
            cursor = db.rawQuery("Select count (*) from " + TABLE_RESPONSES + " WHERE _id  >= " + id + " AND USER_ANSWER = CORRECT_ANSWER", null);// USER_ANSWER = CORRECT_ANSWER ORDER BY ID DESC LIMIT " + n, null);
            cursor.moveToNext();
            int correct = cursor.getInt(0);
            Log.e("my", "Correct = " + correct);
            cursor.close();
            db.close();
            Double d = (double) correct / all*100;
            return correct + "/" + all + " = " + d.intValue()+"%";
        }
        catch (Exception e){
            return "";
        }
    }


}
