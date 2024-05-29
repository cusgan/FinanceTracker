package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddWallet extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText etName, etBal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);

        btnAdd = (Button) findViewById(R.id.btnAddWallet);
        btnCancel = (Button) findViewById(R.id.btnCancelGoal);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    etBal = (EditText) findViewById(R.id.txtWalletInitBal);
                    float walletBalance = Float.parseFloat( etBal.getText().toString() );
                    etName = (EditText) findViewById(R.id.txtWalletName);
                    String walletName = etName.getText().toString();
                    SQLInterface.newWallet(walletName,walletBalance,UserData.userid);
                    SQLInterface.getUserData(UserData.userid);
                    Wallets.wa.notifyDataSetChanged();
                    finish();
                } catch (Exception e){

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