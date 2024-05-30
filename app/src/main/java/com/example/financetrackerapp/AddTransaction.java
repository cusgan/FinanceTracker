package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTransaction extends AppCompatActivity {
    Button btnSaveTransaction, btnCancelTRansaction;
    EditText etNTDesc, etNTAmount;
    RadioButton rbIncome;
    Spinner categorySpinner, walletSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        etNTDesc = (EditText) findViewById(R.id.tvNTDesc);
        etNTAmount = (EditText) findViewById(R.id.editTextNumberDecimal);
        rbIncome = (RadioButton) findViewById(R.id.rdbIncome);
        categorySpinner = (Spinner) findViewById(R.id.spnCategory);
        walletSpinner = (Spinner) findViewById(R.id.spnWallet);

        String[] arraySpinner = new String[UserData.wallets.size()];
        for(int i=0;i<UserData.wallets.size(); i++){
            arraySpinner[i] = UserData.wallets.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        walletSpinner.setAdapter(adapter);

        btnSaveTransaction = (Button) findViewById(R.id.btnSaveTrans);
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String desc;
                    try{
                        desc = etNTDesc.getText().toString();
                    } catch (Exception e) {
                        desc = "Transaction";
                    }
                    float amount = Float.parseFloat(etNTAmount.getText().toString());
                    if(!rbIncome.isChecked()) amount *= -1;
                    String category = categorySpinner.getSelectedItem().toString();
                    int walletIndex = walletSpinner.getSelectedItemPosition();
                    int walletid = UserData.wallets.get(walletIndex).id;
                    SQLInterface.newTransaction(
                            desc,
                            category,
                            amount,
                            walletid,
                            UserData.userid
                    );
                    toast("Successfully added new Transaction.");
                    SQLInterface.getUserData(UserData.userid);
                    finish();
                } catch (Exception e){
                    toast("Could not add new Transaction.");
                }
            }
        });

        btnCancelTRansaction = (Button) findViewById(R.id.btnCancelTrans);
        btnCancelTRansaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}