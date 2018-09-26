package com.faa1192.ISTQBTestExam.q.training;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.faa1192.ISTQBTestExam.QuestionObject;
import com.faa1192.ISTQBTestExam.R;
import com.faa1192.ISTQBTestExam.db.DBHelper;
import com.faa1192.ISTQBTestExam.db.DBQuestion;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    public final List<QuestionObject> questionList;
    protected final Context context;

    public TrainingAdapter(List<QuestionObject> cityList, Context context) {
        this.questionList = cityList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;

        }


    }

    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_training, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;


        TextView textView;
        textView = cardView.findViewById(R.id.textQuestion);
        // textView.setText(questionList.get(position).toString());//country + ": " + questionList.get(position).name);
        int x = questionList.get(position).num;
        textView.setText(new DBQuestion(cardView.getContext()).getRus(x));
        textView.setTag(x);
        SQLiteDatabase db = DBHelper.getDB(cardView.getContext()).getReadableDatabase();
        Cursor cursor = db.query(true, DBHelper.TABLE_VARIANTS, new String[]{"NUM", "RUS", "ENG", "_id"}, "QUESTION_NUMBER = " + x, null, null, null, "_id", null);
        if (cursor.moveToPosition(0)) {
            textView = cardView.findViewById(R.id.textV1);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getStr(cursor.getString(1), cursor.getString(2)));
            textView.setTag(cursor.getString(0));
        }
        if (cursor.moveToPosition(1)) {
            textView = cardView.findViewById(R.id.textV2);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getStr(cursor.getString(1), cursor.getString(2)));
            textView.setTag(cursor.getString(0));
        }
        if (cursor.moveToPosition(2)) {
            textView = cardView.findViewById(R.id.textV3);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getStr(cursor.getString(1), cursor.getString(2)));
            textView.setTag(cursor.getString(0));
        }
        if (cursor.moveToPosition(3)) {
            textView = cardView.findViewById(R.id.textV4);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getStr(cursor.getString(1), cursor.getString(2)));
            textView.setTag(cursor.getString(0));
        }
        if (cursor.moveToPosition(4)) {
            textView = cardView.findViewById(R.id.textV5);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getStr(cursor.getString(1), cursor.getString(2)));
            textView.setTag(cursor.getString(0));
        }
        cursor.close();
        db.close();
        ConstraintLayout cl = cardView.findViewWithTag("card_training");
        String qnum = cl.findViewById(R.id.textQuestion).getTag().toString();
        View var1 = cl.findViewById(R.id.textV1);
        View var2 = cl.findViewById(R.id.textV2);
        View var3 = cl.findViewById(R.id.textV3);
        View var4 = cl.findViewById(R.id.textV4);
        View var5 = cl.findViewById(R.id.textV5);
        ArrayList<View> varList = new ArrayList<>();
        varList.add(var1);
        varList.add(var2);
        varList.add(var3);
        varList.add(var4);
        varList.add(var5);
        for (View v:varList
             ) {
            v.setBackground(cardView.getResources().getDrawable(R.drawable.question_background));
        }


        var1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v, cardView);
            }
        });

        var2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v, cardView);
            }
        });

        var3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v, cardView);
            }
        });

        var4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v, cardView);
            }
        });

        var5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v, cardView);
            }
        });


        ImageView imageView = cardView.findViewById(R.id.img1);
        imageView.setVisibility(View.GONE);
        DBQuestion dbQuestion = new DBQuestion(cardView.getContext());
        byte[] a = dbQuestion.getImg(x);
        if(a.length>0){
            imageView.setVisibility(View.VISIBLE);
            Bitmap bm = BitmapFactory.decodeByteArray(a, 0, a.length);
            imageView.setImageBitmap(bm);
        }
        // textView = cardView.findViewById(R.id.textQuestion);

        //textView.setText((questionList.get(position).getExtraName().length()==0)?(new Country(context).getName(questionList.get(position).country)):(questionList.get(position).getExtraName()+", "+new Country(context).getName(questionList.get(position).country)));
       /* cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Log.e("my", view.getClass()+"");
//                Toast.makeText(view.getContext(), view.getTag()+"", Toast.LENGTH_SHORT).show();
                    }
                });

*/


    }

    public void changeView(View v, CardView cardView) {
        Log.e("me", "CHANGE VIEW");
        String currentVar = v.getTag().toString();
        if (!currentVar.equals("")) {
            View layView = cardView.findViewWithTag("card_training");
            View qView = layView.findViewById(R.id.textQuestion);
            String qNum = qView.getTag().toString();
           // Log.e("my", "qNum = " + qNum);
            String correctVar = getCorrectVar(qNum);
          //  Log.e("my", "correctVar = " + correctVar);
          //  Log.e("my", "Question number: " + qNum + " Current number: " + currentVar + " Correct number: " + correctVar);
            DBQuestion dbQuestion = new DBQuestion(cardView.getContext());
            dbQuestion.addStats(qNum, correctVar, currentVar);
            if (currentVar.equals(correctVar)) {
                v.setBackgroundColor(Color.GREEN);
                return;
            }
            makeColor(correctVar, cardView);
        }
//               v.setBackgroundColor(v.getTag().equals(getCorrectVar(cardView.findViewWithTag("card_training").findViewById(R.id.textQuestion).getTag().toString())) ? Color.GREEN : Color.RED);
    }

    public void makeColor(String correctVar, CardView cardView) {
        Log.e("me", "MAKE COLOR");
        ConstraintLayout cl = cardView.findViewWithTag("card_training");
        View var1 = cl.findViewById(R.id.textV1);
        View var2 = cl.findViewById(R.id.textV2);
        View var3 = cl.findViewById(R.id.textV3);
        View var4 = cl.findViewById(R.id.textV4);
        View var5 = cl.findViewById(R.id.textV5);
        ArrayList<View> varList = new ArrayList<>();
        varList.add(var1);
        varList.add(var2);
        varList.add(var3);
        varList.add(var4);
        varList.add(var5);

        for (View view : varList) {
            if (view == null)
                return;
            if (view.getTag() == null || view.getTag().equals(""))
                return;
            if (view.getTag().toString().equals(correctVar))
                view.setBackgroundColor(Color.GREEN);
            else
                view.setBackgroundColor(Color.RED);

        }
    }


    public String getCorrectVar(String qnum) {
        DBQuestion dbQuestion = new DBQuestion(this.context);
        return dbQuestion.getCorrectVarForQuestion(qnum);
    }

    public String getStr(String rus, String eng) {
        if (rus == null || rus.equals("null") || rus.equals(""))
            return eng;
        return rus;
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }
}