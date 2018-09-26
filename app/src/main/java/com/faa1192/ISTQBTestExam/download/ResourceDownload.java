package com.faa1192.ISTQBTestExam.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.faa1192.ISTQBTestExam.db.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResourceDownload extends AsyncTask<String, Integer, Void> {
    boolean success;
    //   String responseString;
    byte[] bytes;
    Context context;
    long time1;
    long time2;
    String num;

    public ResourceDownload(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Log.e("my", "SIZE STRINGS = " + strings.length);
        for (int j = 0; j < strings.length; j++) {
            num = strings[j];
            success = false;
            String urlString = "https://github.com/ArtiomCX75/ISTQBTestExam/raw/master/res/q/";
            try {
                URL url = new URL(urlString + "q" + num + ".jpg");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "image/jpeg");
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);


                ByteArrayOutputStream output = new ByteArrayOutputStream();
                InputStream inputStream = con.getInputStream();
                byte[] buffer = new byte[1000000];// = new byte[inputStream.available()];
               // Log.e("my", "SIZE BYTE ARRAY1 = " + buffer.length);
                int count;
                while ((count = inputStream.read(buffer)) != -1)
                    output.write(buffer, 0, count);
                Log.e("my", "SIZE BYTE ARRAY2 = " + buffer.length);
            //    Log.e("my", "SIZE BYTE ARRAY3 = " + bytes.length);
                bytes = output.toByteArray();
                Log.e("my", "SIZE BYTE ARRAY4 = " + bytes.length);
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

                    Log.e("my", "success");
                    ContentValues cv = new ContentValues();
                    cv.put("QUESTION_NUMBER", num);
                    cv.put("PIC", bytes);
                    db.beginTransaction();
                    db.insert(DBHelper.TABLE_RESOURCES, null, cv);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    time2 = System.currentTimeMillis();
                    Log.e("my", "TIME1: " + (time2 - time1));
                } catch (Exception e) {
                    Log.e("my", "Error in ResDownload 2");
                    for (int i = 0; i < e.getStackTrace().length; i++) {
                        Log.e("my", e.getStackTrace()[i].toString());
                    }
                }
            } else {
                Log.e("my", "Error in ResDownload 3");
            }

            Cursor c1 = DBHelper.getDB(context).getWritableDatabase().rawQuery("select count(*) from RESOURCE", null);
            c1.moveToNext();
            Log.e("my", "QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ = "+c1.getString(0));
            c1.close();
        }
        return null;
    }
}
