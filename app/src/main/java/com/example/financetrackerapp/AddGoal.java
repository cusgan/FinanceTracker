package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGoal extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText txtGoalTitle, txtGoalTarget, txtGoalInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        btnAdd = (Button) findViewById(R.id.btnAddGoal);
        btnCancel = (Button) findViewById(R.id.btnCancelGoal);
        txtGoalTitle = findViewById(R.id.txtGoalTitle);
        txtGoalTarget = findViewById(R.id.txtGoalTarget);
        txtGoalInit = findViewById(R.id.txtGoalInit);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String title = txtGoalTitle.getText().toString();
                    float target = Float.parseFloat(txtGoalTarget.getText().toString());
                    float init = Float.parseFloat(txtGoalInit.getText().toString());
                    SQLInterface.newGoal(title,target,init,UserData.userid);
                    MainPage.makeToast("Created goal.");
                    SQLInterface.getUserData(UserData.userid);
                    Goals.ga.notifyDataSetChanged();
                    finish();
                } catch (Exception e){
                    MainPage.makeToast("Could not add new Goal.");
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}