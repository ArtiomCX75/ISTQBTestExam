package com.faa1192.ISTQBTestExam;

import android.content.Context;

import com.faa1192.ISTQBTestExam.db.DBQuestion;

import androidx.annotation.NonNull;

public class VariantObject {
    Context context;
    //   public int id;
    public String num;
    //  public String qnum;
    //   private String rus;
    //  public String eng;
    //  public boolean isCorrect;

    public VariantObject() {
    }

    public VariantObject(Context context) {
        this.context = context;
    }

    public VariantObject(Context context, String num) {
        this(context);
        this.num = num;
    }
/*    public VariantObject(int id, String num, String qnum, String rus, String eng, int isCorrect) {
        this.id = id;
        this.num = num;
        this.qnum = qnum;
        this.rus = rus;
        this.eng = eng;
        this.isCorrect = (isCorrect == 1) ? true : false;
    }

    */

    public String getRus() {
        return new DBQuestion(context).getVRus(num);
    }

    public String getEng() {
        return new DBQuestion(context).getVEng(num);
    }


    @NonNull
    @Override
    public String toString() {
        String rus = getRus();
        String eng = getEng();
        if (rus == null || rus.equals("null") || rus.equals(""))
            return eng;
        return rus;
    }

}