package com.org.augos.mysolar.View.Fargment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.augos.mysolar.Base.BaseFragment;
import com.org.augos.mysolar.R;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentActivity extends BaseFragment {
    TextView generation_view;
    TextView battery_view;
    TextView exchange_view;
    TextView consumption_view;

    Handler timer = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View view){
        generation_view = view.findViewById(R.id.generation_value);
        battery_view = view.findViewById(R.id.battery_value);
        exchange_view = view.findViewById(R.id.grid_value);
        consumption_view = view.findViewById(R.id.consumption_value);

        generateValue();
        timer.postDelayed(update_runner, 30 * 60 * 1000);
    }
    private Runnable update_runner = new Runnable() {
        public void run() {
            generateValue();
            timer.postDelayed(this, 30 * 60 * 1000);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try{
            if(timer != null){
                timer.removeCallbacks(update_runner);
            }
        }catch (Exception e){

        }
    }

    private void generateValue(){
        Random random = new Random();
        int max = 500;
        int min = 100;
        int randomInt = random.nextInt((max - min) + 1) + min;
        float random_generation = randomInt / 100.0f;

        float random_exchange = random_generation * 0.43f;
        float random_consumption = (random_generation - random_exchange) * 0.68f;

        generation_view.setText(String.format("%.2f", random_generation) + "KW");
        battery_view.setText(random.nextInt(100) + "%");
        exchange_view.setText(String.format("%.2f", random_exchange) + "KW");
        consumption_view.setText(String.format("%.2f", random_consumption) + "KW");
    }
}
