package com.example.financetrackerapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifications newInstance(String param1, String param2) {
        Notifications fragment = new Notifications();
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recyclerNotifs);
        NotifAdapter na = new NotifAdapter();
        rv.setAdapter(na);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        RecyclerView rv2 = (RecyclerView) getView().findViewById(R.id.recyclerInvites);
        InvAdapter na2 = new InvAdapter();
        rv2.setAdapter(na2);
        rv2.setLayoutManager(new LinearLayoutManager(rv2.getContext()));
    }
}

class NotifHolder extends RecyclerView.ViewHolder {
    TextView tvtext;
    Button dismiss;
    View view;

    public NotifHolder(@NonNull View itemView) {
        super(itemView);
        tvtext = (TextView) itemView.findViewById(R.id.tvNotifBody);
        dismiss = (Button) itemView.findViewById(R.id.btnDismissNotif);
    }
}
class NotifAdapter extends RecyclerView.Adapter<NotifHolder>{
    ArrayList<Notif> notifs = UserData.notifs;

    @NonNull
    @Override
    public NotifHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        View theview = inflater.inflate(R.layout.recycler_notif, parent, false);

        NotifHolder viewHolder
                = new NotifHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvtext.setText(notifs.get(index).text+" ");
        final int notifID = notifs.get(index).id;
        holder.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLInterface.deleteNotif(notifID);
                SQLInterface.getUserData(UserData.userid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }
}

class InvHolder extends RecyclerView.ViewHolder {
    TextView tvtext;
    Button yes,no;
    View view;

    public InvHolder(@NonNull View itemView) {
        super(itemView);
        tvtext = (TextView) itemView.findViewById(R.id.tvInvBody);
        yes = (Button) itemView.findViewById(R.id.btnAcceptWallet);
        no = (Button) itemView.findViewById(R.id.btnDeleteWallet);
    }
}
class InvAdapter extends RecyclerView.Adapter<InvHolder>{
    ArrayList<Invite> notifs = UserData.invites;

    @NonNull
    @Override
    public InvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        View theview = inflater.inflate(R.layout.recycler_invites, parent, false);

        InvHolder viewHolder
                = new InvHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvtext.setText(notifs.get(index).text+" ");
        final int notifID = notifs.get(index).id;
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLInterface.acceptInvite(notifID);
                SQLInterface.getUserData(UserData.userid);
            }
        });
        if(holder.no!=null)
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLInterface.deleteNotif(notifID);
                SQLInterface.getUserData(UserData.userid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }
}