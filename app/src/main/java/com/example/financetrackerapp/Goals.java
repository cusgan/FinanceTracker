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
 * Use the {@link Goals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Goals extends Fragment {
    public static Goals g;
    public static GoalAdapter ga;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Goals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Goals.
     */
    // TODO: Rename and change types and number of parameters
    public static Goals newInstance(String param1, String param2) {
        Goals fragment = new Goals();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Goals.g=this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recycler_goals);
        GoalAdapter t = new GoalAdapter();
        rv.setAdapter(t);
        Goals.ga = t;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recycler_goals);
        GoalAdapter t = new GoalAdapter();
        rv.setAdapter(t);
        Goals.ga = t;
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
    }
}
class GoalAdapter extends RecyclerView.Adapter<GoalHolder>{
    ArrayList<Goal> list = UserData.goals;
    View theview;

    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        theview = inflater.inflate(R.layout.recycler_goals, parent, false);

        GoalHolder viewHolder
                = new GoalHolder(theview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Goal goal = list.get(index);
        if(holder.tname!=null) holder.tname.setText(goal.name);
        holder.progress.setText(String.format("%.2f",goal.balance));
        int percent = (int)Math.round(10.0*(goal.balance/goal.amount));
        holder.percent.setText(percent+"% reached");
        holder.target.setText(String.format("%.2f",goal.amount));
        holder.sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLInterface.deleteGoal(goal.id);
                UserData.goals.remove(index);
                Goals.ga.notifyDataSetChanged();
                // realod
                Goals ga = Goals.newInstance(null,null);
                MainPage.getfr().beginTransaction().replace(R.id.frameMainPage,ga );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class GoalHolder extends RecyclerView.ViewHolder{
    TextView tname, progress, percent, target;
    Button sex;

    public GoalHolder(@NonNull View itemView) {
        super(itemView);
        tname = (TextView) itemView.findViewById(R.id.tvGoalNameReal);
        progress = (TextView) itemView.findViewById(R.id.tvGoalProgress);
        percent = (TextView) itemView.findViewById(R.id.tvGoalsPercentSaved);
        target = (TextView) itemView.findViewById(R.id.tvGoalTargetVal);
        sex = (Button) itemView.findViewById(R.id.btnDeleteGoal);
    }
}