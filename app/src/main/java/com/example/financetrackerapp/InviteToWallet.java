package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InviteToWallet extends AppCompatActivity {
    Button btnShare, btnCancel; TextView inputemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_wallet);

        btnShare = (Button) findViewById(R.id.btnShareWallet);
        btnCancel = (Button) findViewById(R.id.btnCancelShare);
        inputemail = (TextView) findViewById(R.id.txtShareWith);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String email = inputemail.getText().toString();
                    int i =SQLInterface.sendInvite(
                            UserData.wallets.get(UserData.selectedwallet).id,
                            email,
                            UserData.name,
                            UserData.wallets.get(UserData.selectedwallet).name);
                    if(i<=0)
                        throw new Exception("No user found");
                } catch (Exception e){
                    MainPage.makeToast("Invite not sent: User not found");
                }
                finish();
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