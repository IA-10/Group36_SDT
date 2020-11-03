package com.org.augos.mysolar.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.org.augos.mysolar.Base.BaseActivity;
import com.org.augos.mysolar.R;

public class SupportActivity extends BaseActivity {
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view == back_btn){
            finish();
        }
    }
}
