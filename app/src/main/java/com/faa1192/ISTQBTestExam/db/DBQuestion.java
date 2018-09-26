package com.faa1192.ISTQBTestExam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faa1192.ISTQBTestExam.QuestionObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_QUESTION;
import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_RESOURCES;
import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_RESPONSES;
import static com.faa1192.ISTQBTestExam.db.DBHelper.TABLE_VARIANTS;

public class DBQuestion {
    Context context;

    public DBQuestion(Context context) {
        this.context = context;
    }

    public List<QuestionObject> getQuestionFromDB() {
        List<QuestionObject> objectList = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getDB(context).getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(true, TABLE_QUESTION, new String[]{"NUM"}, "", null, "NUM", null, null, null);
        long time1 = System.currentTimeMillis();
        while (cursor.moveToNext()) {
            QuestionObject question = new QuestionObject(context, cursor.getInt(0));//, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            Cursor cursor1 = db.query(true, TABLE_VARIANTS, new String[]{"NUM"}, "QUESTION_NUMBER = " + question.num, null, "NUM", null, null, null);
            cursor1.close();
            objectList.add(question);
        }
        long time2 = System.currentTimeMillis();
        Log.e("my", "TIME load Q from DB: " + (time2 - time1));
        cursor.close();
        Log.e("my", "SIZE Q LIST = " + objectList.size());
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return objectList;
    }

    public String getRus(int num) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_QUESTION, new String[]{"RUS"}, "NUM = " + num, null, null, null, null, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return num+") "+s;
    }


    public String getEng(int num) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_QUESTION, new String[]{"ENG"}, "NUM = " + num, null, null, null, null, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return num+") "+s;
    }


    public String getVRus(String num) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_VARIANTS, new String[]{"RUS"}, "NUM = " + num, null, null, null, null, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return s;
    }


    public String getVEng(String num) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_VARIANTS, new String[]{"ENG"}, "NUM = " + num, null, null, null, null, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return s;
    }


    public String getCorrectVarForQuestion(String qnum) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor cursor = db.query(TABLE_VARIANTS, new String[]{"NUM"}, "QUESTION_NUMBER = " + qnum + " AND IS_CORRECT = 'true'", null, null, null, null, null);
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return s;
    }

    public byte[] getImg(int num) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        Cursor c1 = db.rawQuery("select count(*) from RESOURCE", null);
        c1.moveToNext();
        Log.e("my", "WWWWWWWWWWWWWWWWWWWW = "+c1.getString(0));
        c1.close();
        Log.e("my", "WWW "+num);
        Cursor cursor = db.rawQuery("select PIC from RESOURCE where QUESTION_NUMBER = "+num, null);
        cursor.moveToNext();

        byte[] arr = new byte[0];
        try {
            arr = cursor.getBlob(0);
        }
        catch (Exception e){

        }
        cursor.close();
        db.close();
        return arr;
    }


    //db.execSQL("CREATE TABLE " + TABLE_RESPONSES + " (_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, QUESTION_NUMBER TEXT, DATE TEXT, USER_ANSWER TEXT, CORRECT_ANSWER TEXT, SESSION_ID TEXT);");

    public void addStats(String qnum, String corNum, String userNum, String session) {
        SQLiteDatabase db = DBHelper.getDB(context).getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String d = dateFormat.format(date);
        db.execSQL("INSERT INTO " + TABLE_RESPONSES + "(DATE, QUESTION_NUMBER, USER_ANSWER, CORRECT_ANSWER, SESSION_ID) Values ('" + d + "', '"+qnum + "', '"+userNum + "', '"+corNum + "', '"+session+"')");
        //  Cursor cursor = db.query(TABLE_VARIANTS, new String[]{"NUM"}, "QUESTION_NUMBER = " + qnum + " AND IS_CORRECT = 'true'", null, null, null, null, null);
        Cursor c = db.query(TABLE_RESPONSES, null, null, null, null, null, null );
        while (c.moveToNext()){
            Log.e("my", "_id:"+c.getString(0)+" date:"+c.getString(1)+" q num:"+c.getString(2)+" u V:"+c.getString(3)+" c V:"+c.getString(4)+" session:"+c.getString(5)+"!");
        }
        c.close();
        db.close();
    }
    public void addStats(String qnum, String corNum, String userNum) {
        addStats(qnum, corNum, userNum, "");
    }


}
