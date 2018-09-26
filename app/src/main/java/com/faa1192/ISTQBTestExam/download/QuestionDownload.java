package com.faa1192.ISTQBTestExam.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.faa1192.ISTQBTestExam.db.DBHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionDownload extends AsyncTask<String, Integer, Void> {
    boolean success;
    String responseString;
    Context context;
    long time1;
    long time2;

    public QuestionDownload(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        success = false;
        String urlString = "https://raw.githubusercontent.com/ArtiomCX75/ISTQBTestExam/master/res/questions.txt";
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(inputStreamReader);
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            responseString = content.toString();
            Log.e("my", "length " + responseString.length());
            success = true;
        } catch (Exception e) {
            Log.e("my", "Error in QuestionDownload");
            for (int i = 0; i < e.getStackTrace().length; i++) {
                Log.e("my", e.getStackTrace()[i].toString());

            }
        }
        if (success) {
            DBHelper dbhelper = DBHelper.getDB(context);
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            try {
                db.execSQL("DELETE from " + DBHelper.TABLE_QUESTION);
                db.execSQL("DELETE from " + DBHelper.TABLE_VARIANTS);
            } catch (SQLException e) {
                Log.e("my", "sql exception. db doesn't exist");
            }
            try {

                Log.e("my", "success");
                JSONTokener jt = new JSONTokener(responseString);
                JSONObject jo = null;
                JSONArray ja = null;
                List<ContentValues> questionList = new ArrayList<>();
                List<ContentValues> variantList = new ArrayList<>();
                jo = new JSONObject(jt);
                ja = jo.getJSONArray("myarr");
                try {
                    time1 = System.currentTimeMillis();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo1 = ja.getJSONObject(i);
                        ContentValues cv = new ContentValues();
                        cv.put("NUM", jo1.getString("id"));
                        cv.put("RUS", jo1.getString("russianText"));
                        cv.put("ENG", jo1.getString("englishText"));
                        cv.put("RESOURCE_NUMBER", jo1.getString("resource").replace("q", ""));
                        questionList.add(cv);
                        JSONArray ja1 = jo1.getJSONArray("answers");
                        for (int j = 0; j < ja1.length(); j++) {
                            JSONObject jo2 = ja1.getJSONObject(j);
                            ContentValues cv1 = new ContentValues();
                            cv1.put("NUM", jo2.getString("id"));
                            cv1.put("QUESTION_NUMBER", jo2.getString("questionId"));
                            cv1.put("RUS", jo2.getString("russianText"));
                            cv1.put("ENG", jo2.getString("englishText"));
                            cv1.put("IS_CORRECT", jo2.getString("isCorrect"));
                            variantList.add(cv1);
                        }
                    }
                    time2 = System.currentTimeMillis();
                    Log.e("my", "TIME1: " + (time2 - time1));
                } catch (Exception e) {
                    Log.e("my", "Error in QuestionDownload 2");
                    for (int i = 0; i < e.getStackTrace().length; i++) {
                        Log.e("my", e.getStackTrace()[i].toString());
                    }
                }
                time1 = System.currentTimeMillis();
                db.beginTransaction();
                for (int i = 0; i < questionList.size(); i++) {
                    db.insert(DBHelper.TABLE_QUESTION, null, questionList.get(i));
                }
                for (int i = 0; i < variantList.size(); i++) {
                    db.insert(DBHelper.TABLE_VARIANTS, null, variantList.get(i));
                }
                db.execSQL("UPDATE " + DBHelper.TABLE_QUESTION_VERSION + " SET 'V' = (SELECT V FROM " + DBHelper.TABLE_QUESTION_VERSION + " WHERE K = 'available') WHERE K = 'current'");

                db.setTransactionSuccessful();
                db.endTransaction();
                time2 = System.currentTimeMillis();
                Log.e("my", "TIME2: " + (time2 - time1));



                Cursor cursor = DBHelper.getDB(context).getWritableDatabase().rawQuery("SELECT RESOURCE_NUMBER FROM "+DBHelper.TABLE_QUESTION+" WHERE RESOURCE_NUMBER != 'null'", null);
                ArrayList<String> strList = new ArrayList<>();
                while(cursor.moveToNext()){
                    String s = cursor.getString(0);
                    Log.e("my", s);
                    strList.add(s);

                }
                ResourceDownload resourceDownload = new ResourceDownload(context);
                String[] sArr = strList.toArray(new String[strList.size()]);
                resourceDownload.execute(sArr);

                VersionDownload versionDownload = new VersionDownload(context);
                versionDownload.execute("");


            } catch (Exception e) {
                Log.e("my", "Error in QuestionDownload 777");
                Log.e("my", e.getMessage());
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }
        } else {
            Log.e("my", "Error in QuestionDownload 3");
        }
        return null;
    }
}
