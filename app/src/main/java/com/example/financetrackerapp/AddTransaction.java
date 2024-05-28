package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddTransaction extends AppCompatActivity {
    Button btnSaveTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        btnSaveTransaction = (Button) findViewById(R.id.btnSaveTrans);
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do sql stuff
                finish();
            }
        });
    }
}