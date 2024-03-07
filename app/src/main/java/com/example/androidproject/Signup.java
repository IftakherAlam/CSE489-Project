package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    private EditText etUserName, etEmail, etPhone, etUserId, etPW, etRPW;
    private CheckBox cbRemUserId, cbRemPawss;
    private Button btnLogin, btnGo, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decideNavigation();
        setContentView(R.layout.activity_signup);

        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etUserId = findViewById(R.id.etUserId);
        etPW = findViewById(R.id.etPW);
        etRPW = findViewById(R.id.etRPW);

        cbRemUserId = findViewById(R.id.cbRemUserId);
        cbRemPawss = findViewById(R.id.cbRemPass);

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

    private void processSignup() {
        String userName = etUserName.getText().toString().trim();
        String userEmail = etEmail.getText().toString().trim();
        String userPhone = etPhone.getText().toString().trim();
        String userId = etUserId.getText().toString().trim();
        String userPW = etPW.getText().toString().trim();
        String userRPW = etRPW.getText().toString().trim();
        String errMsg = "";

        // Check validation
        if (userName.isEmpty()) {
            errMsg += "Invalid name, ";
        }

        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            errMsg += "Invalid Email, ";
        }

        if ((userPhone.startsWith("+880") && userPhone.length() == 14) ||
                (userPhone.startsWith("880") && userPhone.length() == 13) ||
                (userPhone.startsWith("01") && userPhone.length() == 11)) {

        }
        else
        {
            errMsg += "Invalid phone number, ";
        }

        // Check other fields
        if (userPW.length() <= 4 || !userPW.equals(userRPW)) {
            errMsg += "Invalid password, ";
        }

        // Display error message if any
        if (!errMsg.isEmpty()) {
            showErrorDialog(errMsg);
            return; // Stop further execution if there are errors
        }

        // Save user information if there are no errors
        saveUserInfo(userName, userId, userEmail, userPhone, userPW, cbRemPawss.isChecked(), cbRemUserId.isChecked());

        // Show success message
        showSuccessDialog();
    }

    private void saveUserInfo(String userName, String userId, String userEmail, String userPhone,
                              String userPW, boolean rememberPassword, boolean rememberUserId) {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        e.putString("USER_NAME", userName);
        e.putString("USER_ID", userId);
        e.putString("USER_EMAIL", userEmail);
        e.putString("USER_PHONE", userPhone);
        e.putString("PASSWORD", userPW);
        e.putBoolean("REM_PASS", rememberPassword);
        e.putBoolean("REM_USER", rememberUserId);
        e.apply(); // Apply changes asynchronously
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errorMessage);
        builder.setTitle("Error");
        builder.setCancelable(true);
        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
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
        builder.setMessage("Signup Successful!");
        builder.setTitle("Success");
        builder.setCancelable(false);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void decideNavigation() {
        SharedPreferences sp = getSharedPreferences("my_info", MODE_PRIVATE);
        boolean isLogged = sp.getBoolean("REM_LOGIN", false);
        String userId = sp.getString("USER_ID", "");

        if (isLogged) {
            Intent i = new Intent(Signup.this, MainActivity.class);
            i.putExtra("USER_ID", userId);
            startActivity(i);
            finish();
        }
    }
}