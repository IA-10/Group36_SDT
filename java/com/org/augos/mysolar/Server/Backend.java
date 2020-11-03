package com.org.augos.mysolar.Server;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Backend {

    public static Context context;
    public static ProgressDialog mProgress = null;

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void alert(String meg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(meg);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static void initProgress(){
        mProgress = new ProgressDialog(context);
        mProgress.setCancelable(false);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
    }
    public static void showProgressDialog(String title) {
        if (mProgress.isShowing())
            return;
        mProgress.setMessage(title);
        mProgress.show();
    }

    public static void hideProgressDialog() {
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
}
