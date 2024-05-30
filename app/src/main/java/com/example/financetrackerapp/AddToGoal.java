package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddToGoal extends AppCompatActivity {
    Button confirmAdd, cancel;
    EditText amount;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_goal);

        confirmAdd = (Button) findViewById(R.id.btnConfirmAddToGoal);
        cancel = (Button) findViewById(R.id.btnCancelAddToGoal);
        amount = findViewById(R.id.txtGoalAmountAdd);

        spinner = findViewById(R.id.spnGoalWallet);
        String[] arraySpinner = new String[UserData.wallets.size()];
        for(int i=0;i<UserData.wallets.size(); i++){
            arraySpinner[i] = UserData.wallets.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float amt = Float.parseFloat(amount.getText().toString());
                    if(Float.isNaN(amt) || Float.isInfinite(amt) || amt==0){
                        throw new Exception("mamamoomoo");
                    }
                    int walletid = UserData.wallets.get(spinner.getSelectedItemPosition()).id;

                    Goal goal = UserData.goals.get(UserData.selectedGoal);
                    SQLInterface.transactGoal(goal.id,goal.name,walletid,amt,UserData.userid);
                    if(goal.balance<goal.amount){
                        goal.balance += amt;
                        if (goal.balance > goal.amount) {
                            SQLInterface.sendNotif("Congratulations, you have finished goal: " + goal.name + " !", UserData.userid);
                            Notif notif = new Notif();
                            notif.text = "Congratulations, you have finished goal: " + goal.name + " !";
                            notif.userid = UserData.userid;
                            notif.id = (int) Math.round(Math.random() * 56754);
                            UserData.notifs.add(notif);
                            Notifications.na.notifyDataSetChanged();
                        }
                    }
                    Goals.ga.notifyDataSetChanged();
                    MainPage.makeToast("Successfully added funds to Goal");
                    finish();
                } catch (Exception e){
                    if(e.getMessage().matches("mamamoomoo")){
                        MainPage.makeToast("Invalid Amount to Add");
                        return;
                    }
                    MainPage.makeToast("Could not Add to Goal...");
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}