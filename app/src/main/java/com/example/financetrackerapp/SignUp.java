package com.example.financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText etEmail, etPass, etName;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText) findViewById(R.id.txtLoginEmail);
        etPass = (EditText) findViewById(R.id.txtLoginPass);
        etName = (EditText) findViewById(R.id.txtName);

        confirm = (Button) findViewById(R.id.btnConfirmSignup);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String email = etEmail.getText().toString();
                    String pass = etPass.getText().toString();
                    String name = etName.getText().toString();
                    if(SQLInterface.signupAccount(email,pass,name)){
                        toast("Successfully Registered!");
                        finish();
                    } else {
                        toast("Could not Register!");
                    }

                } catch(Exception e){
                    e.printStackTrace();
                    toast("Could not Register!");
                }
            }
        });

    }
    public void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}