package com.example.financetrackerapp;

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
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    public static String[] texts = new String[10];
    Button seeStats;
    EditText startDate, endDate;
    View statsContainer;
    TextView dates, netBal, noIncome, noExpense, totalIncome, totalExpense, largestExpense, largestExpName, mostSpentCat;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        statsContainer = (View) getView().findViewById(R.id.cvStats);
        startDate = (EditText) getView().findViewById(R.id.txtStatStart);
        endDate = (EditText) getView().findViewById(R.id.txtStatEnd);
        dates = (TextView) getView().findViewById(R.id.tvStatsFromTo);
        netBal = (TextView) getView().findViewById(R.id.tvStatNetBal);
        noIncome = (TextView) getView().findViewById(R.id.tvNoIncome);
        noExpense = (TextView) getView().findViewById(R.id.tvNoExpense);
        totalIncome = (TextView) getView().findViewById(R.id.tvTotalIncome);
        totalExpense = (TextView) getView().findViewById(R.id.tvTotalExpenses);
        largestExpense = (TextView) getView().findViewById(R.id.tvLargestExpense);
        largestExpName = (TextView) getView().findViewById(R.id.tvLargeExpenseName);
        mostSpentCat = (TextView) getView().findViewById(R.id.tvMostSpentCategory);

        seeStats = (Button)  getView().findViewById(R.id.btnConfirmDate);
        seeStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.execute(()->{
                    {
                        Timestamp start=null,end=null;
                        try {
                            String strStart = startDate.getText().toString().replace('/','-'),
                                    strEnd = endDate.getText().toString().replace('/','-');
                             start = Timestamp.valueOf(strStart.concat(" 09:01:15"));
                             end = Timestamp.valueOf(strEnd.concat(" 00:00:00"));
                        } catch (Exception e) {
                            MainPage.makeToast("Could not retrieve statistics");
                        }

                        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                        //dates.setText("Stats from " + df.format(start) + " to " + df.format(end));
                        Stats.texts[0] = "Stats from " + df.format(start) + " to " + df.format(end);
                        try (
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT SUM(amount) FROM tbltransaction WHERE userid = ? AND transactiondate >= ? AND transactiondate <= ?;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            BigDecimal netbalance = rs.getBigDecimal(1);
                            //netBal.setText("₱" + String.format("%.2f", netbalance));
                            Stats.texts[1] = "₱" + String.format("%.2f", netbalance);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try (
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT SUM(amount) FROM tbltransaction WHERE userid = ? AND transactiondate >= ? AND transactiondate <= ? AND amount > 0;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            BigDecimal totalinc;
                            if (rs.getBigDecimal(1) != null) totalinc = rs.getBigDecimal(1);
                            else totalinc = new BigDecimal(0);
                            //totalIncome.setText("₱" + String.format("%.2f", totalinc));
                            Stats.texts[2] = "₱" + String.format("%.2f", totalinc);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try (
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT SUM(amount) * -1 FROM tbltransaction WHERE userid = ? AND transactiondate >= ? AND transactiondate <= ? AND amount < 0;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            BigDecimal totalexp;
                            if (rs.getBigDecimal(1) != null) totalexp = rs.getBigDecimal(1);
                            else totalexp = new BigDecimal(0);
                            //totalExpense.setText("₱" + String.format("%.2f", totalexp));
                            Stats.texts[3] = "₱" + String.format("%.2f", totalexp);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try (//no of income
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT COUNT(amount) FROM tbltransaction WHERE userid = ? " +
                                                "AND transactiondate >= ? AND transactiondate <= ? " +
                                                "AND amount > 0;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            Stats.texts[4] = rs.getInt(1)+"";
                            //totalExpense.setText("₱" + String.format("%.2f", totalexp));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try ( //no of expenses
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT COUNT(amount) FROM tbltransaction WHERE userid = ? " +
                                                "AND transactiondate >= ? AND transactiondate <= ? " +
                                                "AND amount < 0;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            Stats.texts[5] = rs.getInt(1)+"";
                            //totalExpense.setText("₱" + String.format("%.2f", totalexp));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try ( //largest expense
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT MIN(amount)*-1,transactiondesc " +
                                                "FROM tbltransaction WHERE userid = ? " +
                                                "AND transactiondate >= ? AND transactiondate <= ? " +
                                                "AND amount < 0;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            Stats.texts[6] = "₱" + String.format("%.2f",rs.getFloat(1));
                            Stats.texts[7] = rs.getString(2);
                            //totalExpense.setText("₱" + String.format("%.2f", totalexp));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try (
                                Connection c = SQLInterface.getConnection(); /*automatically close()*/
                                PreparedStatement statement = c.prepareStatement(
                                        "SELECT category,COUNT(category) AS frequency " +
                                                "FROM tbltransaction WHERE userid = ? " +
                                                "AND transactiondate >= ? AND transactiondate <= ? " +
                                                "GROUP BY category ORDER BY frequency DESC LIMIT 1;"
                                );
                        ) {
                            statement.setInt(1, UserData.userid);
                            statement.setTimestamp(2, start);
                            statement.setTimestamp(3, end);

                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            Stats.texts[8] = rs.getString(1);
                            //totalExpense.setText("₱" + String.format("%.2f", totalexp));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
                try {
                    es.awaitTermination(3000, TimeUnit.MILLISECONDS);
                    dates.setText(Stats.texts[0]);
                    netBal.setText(Stats.texts[1]);
                    totalIncome.setText(Stats.texts[2]);
                    totalExpense.setText(Stats.texts[3]);
                    noIncome.setText(Stats.texts[4]);
                    noExpense.setText(Stats.texts[5]);
                    largestExpense.setText(Stats.texts[6]);
                    largestExpName.setText(Stats.texts[7]);
                    mostSpentCat.setText(Stats.texts[8]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                statsContainer.setAlpha(1f);
            }
        });

        statsContainer.setAlpha(0f);


    }

    @Override
    public void onResume() {
        super.onResume();
        statsContainer.setAlpha(0f);
    }
}