package com.example.financetrackerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wallets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wallets extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        TextView tvTotal, tvWalletCount;
        tvTotal = (TextView) getView().findViewById(R.id.tvWalletTotalBal);
        tvWalletCount = (TextView) getView().findViewById(R.id.tvWalletCount);
        tvTotal.setText("₱"+UserData.totalBalance);
        String s = "";
        if(UserData.wallets.size()>1) s="s";
        tvWalletCount.setText("from "+UserData.wallets.size()+ " wallet"+s);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView tvTotal, tvWalletCount;
        tvTotal = (TextView) getView().findViewById(R.id.tvWalletTotalBal);
        tvWalletCount = (TextView) getView().findViewById(R.id.tvWalletCount);
        tvTotal.setText("₱"+UserData.totalBalance);
        String s = "";
        if(UserData.wallets.size()>1) s="s";
        tvWalletCount.setText("from "+UserData.wallets.size()+ " wallet"+s);
    }
}
class WalletAdapter extends RecyclerView.Adapter<WalletHolder>{
    ArrayList<Wallet> wallets = UserData.wallets;

    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        View theview = inflater.inflate(R.layout.fragment_wallets, parent, false);

        WalletHolder viewHolder
                = new WalletHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvname.setText(wallets.get(index).name+" ");
        holder.tvbal.setText("₱"+wallets.get(index).balance);
        if(wallets.get(index).userid2 != 0)
            holder.tvshared.setText("(Shared Wallet.)");
        else
            holder.tvshared.setText(" ");
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                listiner.click(index);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }
}
class WalletHolder extends RecyclerView.ViewHolder {
    TextView tvname, tvbal,tvshared;
    View view;

    public WalletHolder(@NonNull View itemView) {
        super(itemView);
        tvname = (TextView)itemView.findViewById(R.id.tvWalletName);
        tvbal = (TextView) itemView.findViewById(R.id.tvWalletBal);
        tvshared=(TextView) itemView.findViewById(R.id.tvWalletShared);
    }
}