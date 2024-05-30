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
    EditText[] ets;

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
        ets = new EditText[]{
                food,house,transpo,util,health,savings,personal,misc
        };
        for(int i=0; i<8; i++){
            if(UserData.budget.category[i]>0)
                ets[i].setText(""+UserData.budget.category[i]);
        }
        //TODO: stringformat and hints sa edit budget

        btnChangeBudget = (Button) findViewById(R.id.btnConfirmBudgetEdit);
        btnChangeBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float[] cats = new float[8];
                    float stotal = Float.parseFloat(total.getText().toString());
                    float ctotal = 0;
                    for (int i = 0; i < 8; i++) {
                        if(ets[i].getText().toString().length()>0)
                            cats[i] = Float.parseFloat(ets[i].getText().toString());
                        else
                            cats[i]=0;
                        ctotal += cats[i];
                        if(ctotal > stotal){
                            throw new Exception("nonomax");
                        }
                    }
                    UserData.budget.total = stotal;
                    UserData.budget.category = cats;
                    finish();
                } catch (Exception e){
                    if(e.getMessage().matches("nonomax"))
                        MainPage.makeToast("Sum of all categories should not exceed the max budget");
                    else
                        MainPage.makeToast("Could not save budget.");
                }
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