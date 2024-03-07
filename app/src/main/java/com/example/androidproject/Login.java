package com.example.androidproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText etaUserId, etPassword;
    private CheckBox cbRememberbUserId, cbRembmberPassowrd;
    private Button btnSignup, btn1Go, btn1Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etaUserId = findViewById(R.id.etaUserId);
        etPassword = findViewById(R.id.etPassword);

        cbRememberbUserId = findViewById(R.id.cbRememberbUserId);
        cbRembmberPassowrd = findViewById(R.id.cbRembmberPassowrd);

        btnSignup = findViewById(R.id.btnSignup);
        btn1Go = findViewById(R.id.btn1Go);
        btn1Exit = findViewById(R.id.btn1Exit);

        btn1Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
                finish();
            }
        });

        btn1Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });
    }

    private void processLogin() {
        String userId = etaUserId.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        SharedPreferences sp = this.getSharedPreferences("user_info", MODE_PRIVATE);
        String savedUserId = sp.getString("USER_ID", "");
        String savedPassword = sp.getString("PASSWORD", "");

        if (userId.equals(savedUserId) && pass.equals(savedPassword)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("REM_USER", cbRememberbUserId.isChecked());
            editor.putBoolean("REM_PASS", cbRembmberPassowrd.isChecked());
            editor.apply();

            // Show success message
            showSuccessDialog();

            Intent i = new Intent(Login.this, MainActivity.class);
            i.putExtra("User_Id", userId);
            startActivity(i);
            finish();
        } else {
            showErrorDialog("Invalid credentials. Please try again.");
        }
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errorMessage);
        builder.setTitle("Error");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Login Successful!");
        builder.setTitle("Success");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}