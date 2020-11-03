package com.org.augos.mysolar.View.Fargment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.org.augos.mysolar.Base.BaseFragment;
import com.org.augos.mysolar.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentDashBoard extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View view){

    }
}
