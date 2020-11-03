package com.org.augos.mysolar.Base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.org.augos.mysolar.Helper.DialogHelper;
import com.org.augos.mysolar.Helper.SessionManager;
import com.org.augos.mysolar.Server.Backend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements View.OnClickListener {
    public DialogHelper dialogHelper;
    public SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogHelper = new DialogHelper(getContext());
        sessionManager = new SessionManager(getContext());
        Backend.context = getContext();
    }
    @Override
    public void onClick(View view){

    }
    public void startIntent(Class<?> cls){
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }
    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
