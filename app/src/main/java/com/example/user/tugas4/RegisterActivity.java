package com.example.user.tugas4;

import com.example.user.tugas4.DatabaseHelper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.telephony.TelephonyManager;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    User user1= new User();
    Button buttonRegister;
    private TelephonyManager mTelephonyManager;
    String device_token;
    TextView login;
    private EditText input_username, input_email, input_password, input_confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(RegisterActivity.this);
        input_username = (EditText) findViewById(R.id.signup_input_username);
        input_email = (EditText) findViewById(R.id.signup_input_email);
        input_password = (EditText) findViewById(R.id.signup_input_password);
        input_confirm_password = (EditText) findViewById(R.id.signup_input_confirm_password);
        final TextInputLayout con_pass = (TextInputLayout) findViewById(R.id.con_pass_lay);
        final TextInputLayout pass = (TextInputLayout) findViewById(R.id.pass_lay);
        final TextInputLayout user = (TextInputLayout) findViewById(R.id.user_lay);
        final TextInputLayout email = (TextInputLayout) findViewById(R.id.email_lay);

        input_password.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (input_password.getText().length()< 6 || input_password.getText().length()> 15 ){
                    pass.setError("Password between 6 and 10 alphanumeric characters.");
                    buttonRegister.setEnabled(false);
                }else {
                    pass.setError(null);
                    buttonRegister.setEnabled(true);
                }
            }
        });

        input_email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (TextUtils.isEmpty(input_email.getText().toString()) || !input_email.getText().toString().matches(emailPattern)){
                    email.setError("Enter a valid email address.");
                    buttonRegister.setEnabled(false);
                }else {
                    email.setError(null);
                    buttonRegister.setEnabled(true);
                }
            }
        });


        input_username.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (input_username.getText().toString().equals("")){
                    user.setError("Username is Empty.");
                    buttonRegister.setEnabled(false);
                }else {
                    user.setError(null);
                    buttonRegister.setEnabled(true);
                }
            }
        });

        input_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (!input_confirm_password.getText().toString().equals(input_password.getText().toString())){
                    con_pass.setError("Password Doesn't Match.");
                    buttonRegister.setEnabled(false);
                }else {
                    con_pass.setError(null);
                    buttonRegister.setEnabled(true);
                }
            }
        });

        login = (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = input_username.getText().toString().toLowerCase();
                String email = input_email.getText().toString().toLowerCase();
                String password = input_password.getText().toString();
                String confirm_password = input_confirm_password.getText().toString();
                TelephonyManager device_id = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                String type = "register";
                user1.setName(username);
                user1.setEmail(email);
                user1.setPassword(password);
                databaseHelper.addUser(user1);
            }
        });
    }




}
