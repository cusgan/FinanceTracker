package com.example.financetrackerapp;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.ContextUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallets extends Fragment {
    public static WalletAdapter wa;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvTotal, tvWalletCount;
    Button btnAddWallet;

    public Wallets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Wallets.
     */
    // TODO: Rename and change types and number of parameters
    public static Wallets newInstance(String param1, String param2) {
        Wallets fragment = new Wallets();
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
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = (TextView) getView().findViewById(R.id.tvWalletTotalBal);
        tvWalletCount = (TextView) getView().findViewById(R.id.tvWalletCount);
        tvTotal.setText("₱"+UserData.totalBalance);
        String s = "";
        if(UserData.wallets.size()>1) s="s";
        tvWalletCount.setText("from "+UserData.wallets.size()+ " wallet"+s);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.walletRecycler);
        WalletAdapter walletAdapter = new WalletAdapter();
        Wallets.wa = walletAdapter;
        rv.setAdapter(walletAdapter);
        View v = getView().findViewById(R.id.walletContainer);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        btnAddWallet = (Button) getView().findViewById(R.id.btnAddWallet);
        btnAddWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        view.getContext(),
                        AddWallet.class
                );
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tvTotal = (TextView) getView().findViewById(R.id.tvWalletTotalBal);
        tvWalletCount = (TextView) getView().findViewById(R.id.tvWalletCount);
        tvTotal.setText("₱"+String.format("%.2f",UserData.totalBalance));
        String s = "";
        if(UserData.wallets.size()>1) s="s";
        tvWalletCount.setText("from "+UserData.wallets.size()+ " wallet"+s);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.walletRecycler);
        WalletAdapter walletAdapter = new WalletAdapter();
//        if(!UserData.recyclers.contains(walletAdapter))UserData.recyclers.add(walletAdapter);
        rv.setAdapter(walletAdapter);
        Wallets.wa = walletAdapter;
        View v = getView().findViewById(R.id.walletContainer);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
    }
}
class WalletAdapter extends RecyclerView.Adapter<WalletHolder>{
    ArrayList<Wallet> wallets = UserData.wallets;
    View theview;

    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        theview = inflater.inflate(R.layout.recycler_wallet, parent, false);

        WalletHolder viewHolder
                = new WalletHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvname.setText(wallets.get(index).name+" ");
        holder.tvbal.setText("₱"+String.format("%.2f",wallets.get(index).balance));
//        holder.tvshared.setText(" ");

        if(wallets.get(index).userid2 != 0){
            holder.btnShare.setText("Shared Wallet");
            holder.btnShare.setEnabled(false);
            holder.btnShare.setBackgroundColor(Color.argb(1,0,70,0));
        }

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.selectedwallet = index;
                Intent intent = new Intent(
                        theview.getContext(),
                        InviteToWallet.class
                );
                theview.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(theview.getContext());
// Add the buttons.
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User taps OK button.
                        SQLInterface.deleteWallet(UserData.wallets.get(index).id);
                        wallets.remove(index);
                        Wallets.wa.notifyDataSetChanged();
                        // realod
                        Wallets n = Wallets.newInstance(null,null);
                        MainPage.getfr().beginTransaction().replace(R.id.frameMainPage,n );

                        //
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel deletion
                    }
                });
// Set other dialog properties.
                builder.setMessage("Are you sure you would like to delete "+UserData.wallets.get(index).name+"? ")
                        .setTitle("Confirm Wallet Deletion");

// Create the AlertDialog.
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }
}
class WalletHolder extends RecyclerView.ViewHolder {
    TextView tvname, tvbal;
    View view;
    Button btnShare, btnDelete;

    public WalletHolder(@NonNull View itemView) {
        super(itemView);
        tvname = (TextView)itemView.findViewById(R.id.tvWalletName);
        tvbal = (TextView) itemView.findViewById(R.id.tvWalletBal);
//        tvshared=(TextView) itemView.findViewById(R.id.tvWalletShared);
        btnShare = (Button) itemView.findViewById(R.id.btnShareWallet);
        btnDelete = (Button) itemView.findViewById(R.id.btnDeleteWallet);
    }
}