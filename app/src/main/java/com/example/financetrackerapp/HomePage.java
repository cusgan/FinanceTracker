package com.example.financetrackerapp;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tvBalance, tvIncome, tvExpenses, tvWelcome, tvBudgetTotal, tvBudgetSpent, tvSpentPercent;
    ImageButton btnNotifs, btnLogout;
    Button btnWallets, btnGoals, btnBudget, btnStats;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();

        tvBalance = (TextView) getView().findViewById(R.id.tvTotalBalance);
        tvIncome = (TextView) getView().findViewById(R.id.tvIncome);
        tvExpenses = (TextView) getView().findViewById(R.id.tvExpenses);
        tvWelcome = (TextView) getView().findViewById(R.id.tvWelcome);

        tvBudgetTotal = getView().findViewById(R.id.tvBudgetTotal);
        tvBudgetSpent = getView().findViewById(R.id.tvBudgetSpent);
        tvSpentPercent  = getView().findViewById(R.id.tvSpentPercent);

        tvWelcome.setText("Welcome, " + UserData.name);
        tvBalance.setText("₱" + String.format("%.2f", UserData.totalBalance));
        tvIncome.setText("₱" + String.format("%.2f", UserData.totalIncome));
        tvExpenses.setText("₱" + String.format("%.2f", UserData.totalExpenses));

        tvBudgetTotal.setText("₱" + String.format("%.2f", UserData.monthlyBudget));
        tvBudgetSpent.setText("₱" + String.format("%.2f", UserData.budgetSpent)+" spent");
        float percnt = -1;
        percnt = 100.0f*((float)UserData.budgetSpent/(float)UserData.monthlyBudget+0.1f);
        if(Float.isNaN(percnt) || Float.isInfinite(percnt))
            tvSpentPercent.setText("- - -");
        else
            tvSpentPercent.setText("("+ percnt +"%)");

        btnNotifs = (ImageButton) getView().findViewById(R.id.btnNotifs);
        btnNotifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMainPage, Notifications.class, null)
                        .addToBackStack(null).commit();
            }
        });

        btnLogout = (ImageButton) getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btnWallets = (Button) getView().findViewById(R.id.btnViewWallets);
        btnWallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMainPage, Wallets.class, null)
                        .addToBackStack(null).commit();
            }
        });


        btnGoals = (Button) getView().findViewById(R.id.btnGoals);
        btnGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMainPage, Goals.class, null)
                        .addToBackStack(null).commit();
            }
        });

        btnBudget = (Button) getView().findViewById(R.id.btnBudgeting);
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMainPage, Budgeting.class, null)
                        .addToBackStack(null).commit();
            }
        });

        btnStats = (Button) getView().findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMainPage, Stats.class, null)
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tvWelcome.setText("Welcome, " + UserData.name);
        tvBalance.setText("₱" + String.format("%.2f", UserData.totalBalance));
        tvIncome.setText("₱" + String.format("%.2f", UserData.totalIncome));
        tvExpenses.setText("₱" + String.format("%.2f", UserData.totalExpenses));

        tvBudgetTotal.setText("₱" + String.format("%.2f", UserData.monthlyBudget));
        tvBudgetSpent.setText("₱" + String.format("%.2f", UserData.budgetSpent)+" spent");
        float percnt = -1;
        percnt = 100.0f*((float)UserData.budgetSpent/(float)UserData.monthlyBudget);
        if(Float.isNaN(percnt) || Float.isInfinite(percnt))
            tvSpentPercent.setText("- - -");
        else
            tvSpentPercent.setText("("+ percnt +"%)");
    }
}