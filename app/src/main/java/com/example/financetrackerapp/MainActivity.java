package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSignup, btnLogin;
    EditText etLogin, etPassword;

    private static boolean initializedTables;

    private void clearForm(LinearLayout group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof LinearLayout && (((LinearLayout)view).getChildCount() > 0))
                clearForm((LinearLayout)view);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!initializedTables){
            SQLInterface.createTables();
            initializedTables = true;
        }
        SQLInterface.newWallet("My Main Wallet",1200,3);
        etLogin = (EditText) findViewById(R.id.txtLoginEmail);
        etPassword = (EditText) findViewById(R.id.txtLoginPass);

        btnSignup = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,//this activity
                        SignUp.class
                );
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserData.debug) {
                    Intent intent = new Intent(
                            MainActivity.this,
                            MainPage.class
                    );
                    startActivity(intent);
                } else {
                    try{
                        UserData.accid = SQLInterface.login(etLogin.getText().toString(),etPassword.getText().toString());
                        if(UserData.accid != -1){
                            UserData.email = etLogin.getText().toString();
                            UserData.userid = SQLInterface.accIdToUserId(UserData.accid);
                            Intent intent = new Intent(
                                    MainActivity.this,
                                    MainPage.class
                            );
                            startActivity(intent);
                            etLogin.setText("");
                            etPassword.setText("");
                        } else {
                            toast("Could not login.");
                        }
                    } catch (Exception e){
                        toast("Could not login.");
                    }

                }
            }
        });
    }
    public void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}