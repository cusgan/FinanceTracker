package com.example.financetrackerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Stats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stats extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText startDate, endDate;
    TextView netIncome, netExpense, totalIncome, totalExpense, largestExpense, largestExpName, mostSpentCat;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Stats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Stats.
     */
    // TODO: Rename and change types and number of parameters
    public static Stats newInstance(String param1, String param2) {
        Stats fragment = new Stats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        startDate = (EditText) getView().findViewById(R.id.txtStatStart);
        endDate = (EditText) getView().findViewById(R.id.txtStatEnd);
        netIncome = (TextView) getView().findViewById(R.id.tvNetIncome);
        netExpense = (TextView) getView().findViewById(R.id.tvNetExpenses);
        totalIncome = (TextView) getView().findViewById(R.id.tvTotalIncome);
        totalExpense = (TextView) getView().findViewById(R.id.tvTotalExpenses);
        largestExpense = (TextView) getView().findViewById(R.id.tvLargestExpense);
        largestExpName = (TextView) getView().findViewById(R.id.tvLargeExpenseName);
        mostSpentCat = (TextView) getView().findViewById(R.id.tvMostSpentCategory);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }
}