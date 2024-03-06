package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    private EditText etUserName,etEmail,etPhone,etUserId,etPW,etRPW;
    private CheckBox cbRemUserId,cbRemPawss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etUserId = findViewById(R.id.etUserId);
        etPW = findViewById(R.id.etPW);
        etRPW = findViewById(R.id.etRPW);

        cbRemUserId = findViewById(R.id.cbRemUserId);
        cbRemPawss = findViewById((R.id.cbRemPass));



        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSignup();
            }
        });

    }

    private void processSignup(){
        String userName = etUserName.getText().toString().trim();
        String userEmail = etEmail.getText().toString().trim();
        String userPhone = etPhone.getText().toString().trim();
        String userId = etUserId.getText().toString().trim();
        String userPW = etPW.getText().toString().trim();
        String userRPW = etRPW.getText().toString().trim();
        String errMsg = "";


//        check validatioin

        if(userName.length() < 4 || userName.length() > 8){
            errMsg += "Invalid User Name, ";
        }

        if(userPW.length() != 4 || userPW.equals(userRPW)){
            errMsg+= "invalid password";
        }

        if(errMsg.length() > 0 ){
            System.out.println(errMsg);
            return;
        }

        SharedPreferences sp = this.getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putString("USER_NAME", userName);
        e.putString("USER_ID", userId);
        e.putString("USER_EMAIL", userEmail);
        e.putString("USER_PHONE", userPhone);
        e.putString("PASSWORD", userPW);
        e.putBoolean("REM_LOGIN", cbRemPawss.isChecked());
        e.putBoolean("REM_USER", cbRemUserId.isChecked());
        e.apply();

    }
    }
