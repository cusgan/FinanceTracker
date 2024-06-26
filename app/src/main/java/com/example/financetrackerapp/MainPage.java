package com.example.financetrackerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {
    Button btnHome, btnTransact, btnHistory;
    public static MainPage mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mp = this;
        SQLInterface.getUserData(UserData.userid);

        //default fragment is home page
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frameMainPage, HomePage.class, null)
                    .commit();
        }

        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.frameMainPage, HomePage.class, null)
                        .commit();
            }
        });

        btnTransact = (Button) findViewById(R.id.btnAddTransact);
        btnTransact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainPage.this,
                        AddTransaction.class
                );
                startActivity(intent);
            }
        });

        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameMainPage, TransactionHistory.class, null)
                        .addToBackStack(null).commit();
            }
        });
    }
    public static FragmentManager getfr(){
        return MainPage.mp.getSupportFragmentManager();
    }
    public static void makeToast(String text){
        Toast.makeText(mp.getBaseContext(),text,Toast.LENGTH_SHORT).show();
    }
}