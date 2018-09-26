package com.faa1192.ISTQBTestExam.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.faa1192.ISTQBTestExam.db.DBHelper;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class VersionDownload extends AsyncTask<String, Integer, Void> {
    //  String countryName = "";
    boolean success;
    String responseString;
    Context context;

    public VersionDownload(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        success = false;
        String urlString = "https://raw.githubusercontent.com/ArtiomCX75/ISTQBTestExam/master/res/version.txt";
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            InputStream is = con.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(inputStreamReader);
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            responseString = content.toString();
            success = true;
        } catch (Exception e) {
            Log.e("my", "Error in VersionDownload");
            for (int i = 0; i < e.getStackTrace().length; i++) {
                Log.e("my", e.getStackTrace()[i].toString());
            }
        }
        if (success) {
            DBHelper dbhelper = DBHelper.getDB(context);
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            try {
                db.execSQL("DELETE from " + DBHelper.TABLE_APP_VERSION + " WHERE K = 'available'");
                db.execSQL("DELETE from " + DBHelper.TABLE_QUESTION_VERSION + " WHERE K = 'available'");
                Log.e("my", "del availables from " + DBHelper.TABLE_APP_VERSION + " and " + DBHelper.TABLE_QUESTION_VERSION);
            } catch (SQLException e) {
                Log.e("my", "sql exception. db doesn't exist");
            }
            try {
                Log.e("my", "success");
                ContentValues contentValues = new ContentValues();
                db.beginTransaction();
                String availableAppVersion;
                String availableQuestionVersion;
                JSONTokener jt = new JSONTokener(responseString);
                JSONObject jo;
                jo = new JSONObject(jt);
                availableAppVersion = jo.getString("appVersion");
                availableQuestionVersion = jo.getString("questionVersion");
                contentValues.put("K", "available");
                contentValues.put("V", availableAppVersion);
                Log.e("my", "availableAppVersion = " + availableAppVersion);
                Log.e("my", "QTT1 " + db.insert(DBHelper.TABLE_APP_VERSION, null, contentValues));
                contentValues = new ContentValues();
                contentValues.put("K", "available");
                contentValues.put("V", availableQuestionVersion);
                Log.e("my", "availableQuestionVersion = " + availableQuestionVersion);
                Log.e("my", "QTT2 " + db.insert(DBHelper.TABLE_QUESTION_VERSION, null, contentValues));

                //publishProgress(i * 100 / list.size());

                db.setTransactionSuccessful();
                db.endTransaction();
                //    Toast.makeText(context, "download completed", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Log.e("my", "Error in VersionDownload 2");
                Log.e("my", e.getMessage());
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }

        } else {
            Log.e("my", "Error in VersionDownload 3");
        }
        return null;
    }

    public boolean isOldAppVersion() {
        DBHelper db = DBHelper.getDB(context);
        Map<String, Integer> map = new HashMap<>();
        Cursor cursor = db.getWritableDatabase().query(DBHelper.TABLE_APP_VERSION, null, null, null, null, null, null);
        cursor.moveToFirst();
        Log.e("my", cursor.getString(0));
        Log.e("my", cursor.getString(1));
        map.put(cursor.getString(0), cursor.getInt(1));
        if (!cursor.moveToNext())
            return false;
        Log.e("my", cursor.getString(0));
        Log.e("my", cursor.getString(1));
        map.put(cursor.getString(0), cursor.getInt(1));
        int available = map.get("available");
        int current = map.get("current");
        if (available > current)
            return true;
        return false;
    }

    public boolean isOldQuestionVersion() {
        DBHelper db = DBHelper.getDB(context);
        Map<String, Integer> map = new HashMap<>();
        Cursor cursor = db.getWritableDatabase().query(DBHelper.TABLE_QUESTION_VERSION, null, null, null, null, null, null);
        cursor.moveToFirst();
        Log.e("my", "DB QUESTION VERSION");
        Log.e("my", cursor.getString(0));
        Log.e("my", cursor.getString(1));
        map.put(cursor.getString(0), cursor.getInt(1));
        if (!cursor.moveToNext())
            return false;
        Log.e("my", cursor.getString(0));
        Log.e("my", cursor.getString(1));
        map.put(cursor.getString(0), cursor.getInt(1));
        int available = map.get("available");
        int current = map.get("current");
        Log.e("my", "Current = " + current + "  Available = " + available);
        if (available > current)
            return true;
        return false;
    }
}