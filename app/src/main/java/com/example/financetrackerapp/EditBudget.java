package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class EditBudget extends AppCompatActivity {
    Button btnChangeBudget, btnCancel;
    EditText total, food, house, transpo, util, health, savings, personal, misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

        total = (EditText) findViewById(R.id.txtTotalBudgetPlan);
        food = (EditText) findViewById(R.id.txtEditFood);
        house = (EditText) findViewById(R.id.txtEditHousing);
        transpo = (EditText) findViewById(R.id.txtEditTranspo);
        util = (EditText) findViewById(R.id.txtEditUtil);
        health = (EditText) findViewById(R.id.txtEditHealth);
        savings = (EditText) findViewById(R.id.txtEditSavings);
        personal = (EditText) findViewById(R.id.txtEditPersonal);
        misc = (EditText) findViewById(R.id.txtEditMisc);

        btnChangeBudget = (Button) findViewById(R.id.btnConfirmBudgetEdit);
        btnChangeBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sql stuff
                finish();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancelBudget);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}