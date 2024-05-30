package com.example.financetrackerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistory extends Fragment {
    public static TransAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHistory newInstance(String param1, String param2) {
        TransactionHistory fragment = new TransactionHistory();
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
        return inflater.inflate(R.layout.fragment_transaction_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recyclerTransaction);
        TransAdapter t = new TransAdapter();
        adapter = t;
        rv.setAdapter(t);
        TransactionHistory.adapter = t;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
    }
}
class TransAdapter extends RecyclerView.Adapter<TransHolder>{
    ArrayList<Transaction> list = UserData.transactions;
    View theview;

    @NonNull
    @Override
    public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        theview = inflater.inflate(R.layout.recycler_transaction, parent, false);

        TransHolder viewHolder
                = new TransHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Transaction transaction = UserData.transactions.get(index);
        String amountpeso = "₱"+String.format("%.2f",Math.abs(transaction.amount));
        if(transaction.amount<0) {
            amountpeso = "-" + amountpeso;
            holder.amountDisplay.setTextColor(Color.parseColor("#F44336"));
        } else {
            amountpeso = "+" + amountpeso;
            holder.amountDisplay.setTextColor(Color.parseColor("#57C478"));
        }
        holder.amountDisplay.setText(amountpeso);
        holder.walletDisplay.setText("- - -");
        if(UserData.getWallet(transaction.walletid) !=null)
            holder.walletDisplay.setText(UserData.getWallet(transaction.walletid).name);
        holder.descDisplay.setText(""+transaction.description);
        holder.deetsDisplay.setText(""+transaction.category + " - "+transaction.datetime.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class TransHolder extends RecyclerView.ViewHolder{
    TextView amountDisplay, walletDisplay, descDisplay, deetsDisplay;

    public TransHolder(@NonNull View itemView) {
        super(itemView);
        amountDisplay = (TextView) itemView.findViewById(R.id.tvTrHistAmt);
        walletDisplay = (TextView) itemView.findViewById(R.id.tvTrHistWallet);
          descDisplay = (TextView) itemView.findViewById(R.id.tvTrHistDesc);
         deetsDisplay = (TextView) itemView.findViewById(R.id.tvTrHistDetails);
    }
}