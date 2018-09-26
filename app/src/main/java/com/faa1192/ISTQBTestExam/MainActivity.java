package com.faa1192.ISTQBTestExam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faa1192.ISTQBTestExam.download.QuestionDownload;
import com.faa1192.ISTQBTestExam.download.VersionDownload;
import com.faa1192.ISTQBTestExam.q.QuestionActivity;

public class MainActivity extends AppCompatActivity {
    VersionDownload versionDownload;
    QuestionDownload question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("my", "ON CREATE");
        setContentView(R.layout.activity_main);
        versionDownload = new VersionDownload(this);
        versionDownload.execute("");
        question = new QuestionDownload(this);
        Button button = findViewById(R.id.button1);
        button.setEnabled(true); // TODO: 20.09.2018 удалить
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = versionDownload.isOldQuestionVersion() ? "OLD Q" : "NOT OLD Q";
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                versionDownload = new VersionDownload(MainActivity.this);
                versionDownload.execute("");
                if (versionDownload.isOldQuestionVersion()) {
                    question = new QuestionDownload(MainActivity.this);
                    question.execute("");
                } else {
                    startActivity(new Intent(MainActivity.this, QuestionActivity.class));
                }
            }
        });
       // button.performClick(); // TODO: 25.09.2018 удалить
        TextView textView = findViewById(R.id.textView1);
        textView.setText("Ok");

        boolean b = versionDownload.isOldAppVersion();
        if (b) {
            Log.e("my", "OLD APP");
            textView.setText("App is old. https://github.com/ArtiomCX75/ISTQBTestExam");
            //    button.setEnabled(false);
        } else {
            Log.e("my", "NOT OLD APP");
            textView.setText("VersionDownload is up-to-date");
            //  button.setEnabled(true);
        }


        b = versionDownload.isOldQuestionVersion();
        if (b) {
            Log.e("my", "OLD QUESTION");
            Toast.makeText(MainActivity.this, "1 Загружаются вопросы", Toast.LENGTH_SHORT).show();
            question.execute("");
        } else {
            Log.e("my", "NOT OLD QUESTION");
        }

    }
}
