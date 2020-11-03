package com.org.augos.mysolar.Helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;


import com.org.augos.mysolar.R;

import androidx.fragment.app.FragmentActivity;

public class DialogHelper {

    private Context context;
    private ProgressDialog dialog;
    public static int NOTIFICATION_ID = 10;

    public interface MyDialogListener{
        void onClickOk();
        void onClickNo();
    }

    public DialogHelper(Context context){
        this.context = context;
        dialog = new ProgressDialog(context, R.style.DialogTheme);
    }

    public void setDialogInfo(String title, String message){
        dialog.setCancelable(false);

        dialog.setTitle(title);
        dialog.setMessage(message);

    }

    public void showProgressDialog(){
        if(dialog != null){
            dialog.setCancelable(false);
            dialog.show();
            dialog.setContentView(R.layout.layout_progress_view);
        }
    }

    public void closeDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    public static void showMessageDialog(Context context, String title, String message, final MyDialogListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onClickOk();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    listener.onClickNo();
                }
            }
        });
        builder.create().show();
    }

    public static void showNotificationDialog(final Context context, String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
//    public void showCustomAlertDialog(Context context, String title, String message, ConfirmDialog.Listener mListener){
//        ConfirmDialog alert_dlg = new ConfirmDialog();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        args.putString("message", message);
//        alert_dlg.setArguments(args);
//        alert_dlg.setListener(mListener);
//        alert_dlg.setCancelable(false);
//        alert_dlg.show(((FragmentActivity)context).getSupportFragmentManager(), "ALERT_DLG");
//    }
    private void ring() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(context, alarmSound);
        mp.start();
    }
}
