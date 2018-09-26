package com.faa1192.ISTQBTestExam.q;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.faa1192.ISTQBTestExam.R;
import com.faa1192.ISTQBTestExam.q.statistic.StatFragment;
import com.faa1192.ISTQBTestExam.q.training.TrainingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class QuestionActivity extends AppCompatActivity {

    private TextView mTextMessage;
    TrainingFragment trainingFragment;
    ExamFragment examFragment;
    StatFragment statFragment;
    FragmentTransaction fragmentTransaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_training:
                    //mTextMessage.setText(R.string.title_training);
                    fragmentTransaction.replace(R.id.frgmCont, trainingFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_exam:
//                    mTextMessage.setText(R.string.title_exam);
                    fragmentTransaction.replace(R.id.frgmCont, examFragment);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_stat:
             //       mTextMessage.setText(R.string.title_stat);
                    fragmentTransaction.replace(R.id.frgmCont, statFragment);
                    fragmentTransaction.commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("my", "ON CREATE QuestionActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        trainingFragment = new TrainingFragment();
  //      examFragment = new ExamFragment();
        statFragment = new StatFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frgmCont, trainingFragment);
        fragmentTransaction.commit();
    //    mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.findViewById(R.id.navigation_exam).setEnabled(false);
        navigation.findViewById(R.id.navigation_stat).setEnabled(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
