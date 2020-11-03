package com.org.augos.mysolar.View.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.org.augos.mysolar.Base.BaseActivity;
import com.org.augos.mysolar.Helper.StringHelper;
import com.org.augos.mysolar.R;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {
    TextView login_btn;
    EditText email_view;
    EditText password_view;
    RadioButton basic_user;
    RadioButton premium_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sessionManager.IsLogin()){
            startIntentAsFinishMode(MainActivity.class);
        }
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        login_btn = findViewById(R.id.login_btn);
        email_view = findViewById(R.id.user_email);
        password_view = findViewById(R.id.user_password);
        basic_user = findViewById(R.id.basic);
        premium_user = findViewById(R.id.premium);

        login_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if(view == login_btn){
            loginUser();
        }
    }
    private void loginUser(){
        String user_email = email_view.getText().toString().trim();
        String user_password = password_view.getText().toString().trim();
        if(StringHelper.isEmpty(user_email)){
            email_view.setError("Please enter email !");
            return;
        }
        if(StringHelper.isEmpty(user_password)){
            password_view.setError("Please enter password !");
            return;
        }
        if(basic_user.isChecked()){
            if(!user_email.equals("ishita") || !user_password.equals("123")){
                showMessage("Invalid User !");
                return;
            }
            sessionManager.setLevel(0);
        }else{
            if(!user_email.equals("anonna") || !user_password.equals("123")){
                showMessage("Invalid User !");
                return;
            }
            sessionManager.setLevel(1);
        }
        sessionManager.login();
        startIntentAsFinishMode(MainActivity.class);
    }
}
