package com.example.financetrackerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Budgeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Budgeting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnEditBudget;
    TextView total, food, house, transpo, util, health, savings, personal, misc;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Budgeting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Budgeting.
     */
    // TODO: Rename and change types and number of parameters
    public static Budgeting newInstance(String param1, String param2) {
        Budgeting fragment = new Budgeting();
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budgeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        total = (TextView) getView().findViewById(R.id.tvTotalBudgetAll);
        food = (TextView) getView().findViewById(R.id.tvFoodBudget);
        house = (TextView) getView().findViewById(R.id.tvHousingBudget);
        transpo = (TextView) getView().findViewById(R.id.tvTranspoBudget);
        util = (TextView) getView().findViewById(R.id.tvUtilBudget);
        health = (TextView) getView().findViewById(R.id.tvHealthBudget);
        savings = (TextView) getView().findViewById(R.id.tvSavingsBudget);
        personal = (TextView) getView().findViewById(R.id.tvPersonalBudget);
        misc = (TextView) getView().findViewById(R.id.tvMiscBudget);

        btnEditBudget = (Button) getView().findViewById(R.id.btnEditBudget);
        btnEditBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        v.getContext(),
                        Budgeting.class
                );
                startActivity(intent);
            }
        });
    }
}