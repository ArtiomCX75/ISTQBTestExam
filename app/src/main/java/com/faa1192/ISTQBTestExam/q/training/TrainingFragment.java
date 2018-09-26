package com.faa1192.ISTQBTestExam.q.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.faa1192.ISTQBTestExam.MainActivity;
import com.faa1192.ISTQBTestExam.QuestionObject;
import com.faa1192.ISTQBTestExam.R;
import com.faa1192.ISTQBTestExam.db.DBQuestion;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TrainingFragment extends Fragment {


    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_training, container, false);


        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        List<QuestionObject> questionObjectList = new DBQuestion(getActivity()).getQuestionFromDB();
        TrainingAdapter trainingAdapter = new TrainingAdapter(questionObjectList, this.getActivity());//((PrefCityDBHelper.init(getContext()).getCityList(), getContext());
        int prefCityCount = trainingAdapter.getItemCount();
        if(prefCityCount==0){
            getActivity().startActivityForResult(new Intent(getActivity(), MainActivity.class), 1);
            //getActivity().overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
        }
        recyclerView.setAdapter(trainingAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
       /* ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "mv: " + target + "=" + viewHolder, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //  Toast.makeText(getActivity(), "sw: "+direction+"="+viewHolder, Toast.LENGTH_LONG).show();
                PrefCitiesListFragment fragment = (PrefCitiesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
                RecyclerView recyclerView = (RecyclerView) fragment.getView();
                PrefCitiesAdapter cwta = (PrefCitiesAdapter) recyclerView.getAdapter();
                PrefCityDBHelper.init(getActivity()).delFromDbPref(cwta.questionList.get(viewHolder.getAdapterPosition()));
                cwta = new PrefCitiesAdapter(PrefCityDBHelper.init(getActivity()).getCityList(), getActivity());
                recyclerView.setAdapter(cwta);
            }
        };
    */
    //    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
    //    itemTouchHelper.attachToRecyclerView(recyclerView);
        return recyclerView;
    }


}
