package com.org.augos.mysolar.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.org.augos.mysolar.View.Activity.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // All Shared Preferences Keys
    public static final String KEY_USER = "username";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_LOGIN = "isLogin";
    public static final String KEY_LEVEL= "user_level";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setName(String name){
        editor.putString(KEY_USER, name);
        editor.commit();
    }
    public void setPhone(String phone){
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }
    public void login(){
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
    }
    public void setLevel(int level){
        editor.putInt(KEY_LEVEL, level);
        editor.commit();
    }
    public boolean IsLogin(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
    public int getLevel(){
        return pref.getInt(KEY_LEVEL, 0);
    }
    public String getName(){
        return pref.getString(KEY_USER, "");
    }
    public String getPhone(){
        return pref.getString(KEY_PHONE, "");
    }
    public void logout(){
        editor.putBoolean(KEY_LOGIN, false);
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
        ((Activity)_context).finish();
    }
}
