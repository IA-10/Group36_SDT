package com.org.augos.mysolar.Base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.org.augos.mysolar.Helper.DialogHelper;
import com.org.augos.mysolar.Helper.SessionManager;
import com.org.augos.mysolar.Server.Backend;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public Context context;
    public DialogHelper dialogHelper;
    public SessionManager sessionManager;

    public String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public final int LOCATION_REQUEST_CODE = 155;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        dialogHelper = new DialogHelper(context);
        sessionManager = new SessionManager(context);
        Backend.context = context;
    }
    @Override
    public void onClick(View view){

    }
    @Override
    public void onBackPressed(){
        finish();
    }

    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public void showMessageShort(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void startIntentAsFinishMode(Class<?> cls){
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        finish();
    }
    public void startIntentAsCleanMode(Class<?> cls){
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void startIntentLiveMode(Class<?> cls){
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }
}
