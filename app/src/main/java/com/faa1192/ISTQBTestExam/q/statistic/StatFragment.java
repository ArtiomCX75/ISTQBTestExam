package com.faa1192.ISTQBTestExam.q.statistic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faa1192.ISTQBTestExam.R;
import com.faa1192.ISTQBTestExam.db.DBStatistic;

import androidx.fragment.app.Fragment;

public class StatFragment extends Fragment {

    public StatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        DBStatistic dbStatistic = new DBStatistic(getActivity());
        TextView textViewTop60 = getActivity().findViewById(R.id.textViewTop60Value);
        textViewTop60.setText(dbStatistic.getStatForLastNResponse("60"));
        TextView textViewTop300 = getActivity().findViewById(R.id.textViewTop300Value);
        textViewTop300.setText(dbStatistic.getStatForLastNResponse("300"));
        TextView textViewAll = getActivity().findViewById(R.id.textViewAllValue);
        textViewAll.setText(dbStatistic.getStatForAll());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.stat_training, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
