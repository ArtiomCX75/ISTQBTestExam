package com.faa1192.ISTQBTestExam;

import android.content.Context;
import android.util.Log;

import com.faa1192.ISTQBTestExam.db.DBQuestion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class QuestionObject {
    final Context context;
    public int num = 0;


    public QuestionObject(Context context) {
        this.context = context;
    }

    public QuestionObject(Context context, int num){
        this(context);
        this.num = num;
    }
    public String getRus() {
        return new DBQuestion(context).getRus(num);
    }

    public String getEng() {
        return new DBQuestion(context).getEng(num);
    }

    @NonNull
    @Override
    public String toString() {
        String rus = getRus();
        String eng = getEng();
        if (rus == null || rus.equals("null") || rus.equals(""))
            return num + ") " + eng;
        return num + ") " + rus;
    }
}
