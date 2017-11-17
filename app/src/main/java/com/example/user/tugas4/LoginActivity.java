package com.example.user.tugas4;

import android.app.AlertDialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity  {

    private DatabaseHelper databaseHelper;
    ProgressDialog pDialog;

    AlertDialog alertDialog;
    Button login;
    TextView register;
    EditText username_et, password_et;
    String username,password;
    String type ="login";
    String login_url;
    String getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(LoginActivity.this);
        login = (Button) findViewById(R.id.b_login);
        register = (TextView) findViewById(R.id.register);
        username_et = (EditText) findViewById(R.id.et_username);
        password_et = (EditText) findViewById(R.id.et_password);

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(forget);

            }
        });

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                username = username_et.getText().toString().toLowerCase();
                password = password_et.getText().toString().toLowerCase();
                getPassword= password;
                if (TextUtils.isEmpty(username)) {
                    Toast msg = Toast.makeText(LoginActivity.this, "Username is Empty.", Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
                    msg.show();
                }else if (TextUtils.isEmpty(password)){
                    Toast msg = Toast.makeText(LoginActivity.this, "Password is Empty.", Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
                    msg.show();
                }else{
                    alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Login Status");
                    pDialog = new ProgressDialog(LoginActivity.this);
                    pDialog.setMessage("Wait a Moment...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    if (databaseHelper.checkUser(username, password)) {
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                pDialog.dismiss();
                                alertDialog.setMessage("Login Berhasil");
                                alertDialog.show();
                                Intent accountsIntent = new Intent(LoginActivity.this, MainActivity.class);
                                accountsIntent.putExtra("EMAIL", username_et.getText().toString().trim());
                                startActivity(accountsIntent);
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                            }
                        }.start();


                    } else {
                        new CountDownTimer(1500, 1000) {
                            public void onFinish() {
                                pDialog.dismiss();
                                alertDialog.setMessage("Login Gagal");
                                alertDialog.show();
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                            }
                        }.start();

                        // Snack Bar to show success message that record is wrong
                        //Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }




}
