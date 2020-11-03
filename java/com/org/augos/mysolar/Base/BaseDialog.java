package com.org.augos.mysolar.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.org.augos.mysolar.Helper.DialogHelper;
import com.org.augos.mysolar.Helper.SessionManager;
import com.org.augos.mysolar.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class BaseDialog extends DialogFragment implements View.OnClickListener{

    public DialogHelper dialogHelper;
    public SessionManager sessionManager;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogHelper = new DialogHelper(getContext());
        sessionManager = new SessionManager(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.round_dialog_background));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return super.getView();
    }
    @Override
    public void onClick(View view){

    }
    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    public void startIntent(Class<?> cls){
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }
}
