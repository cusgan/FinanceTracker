package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
//        SQLInterface.createTables();
//        SQLInterface.signupAccount("bascug2311@email","secret","bambi");
//        int accid = SQLInterface.login("bascug2311@email","secret");
//        Toast.makeText(this,"Account Id: "+accid,Toast.LENGTH_SHORT).show();
//        int userid = SQLInterface.accIdToUserId(accid);
//        int walletid = SQLInterface.newWallet("My Walleat",1000,userid);
//        int goalid = SQLInterface.newGoal("New Car",160000,10,userid);
//        SQLInterface.newTransaction("Bought food","Food",20,walletid);
    }
}