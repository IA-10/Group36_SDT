package com.org.augos.mysolar.View.Fargment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.augos.mysolar.Base.BaseFragment;
import com.org.augos.mysolar.Helper.Params;
import com.org.augos.mysolar.Model.DataModel;
import com.org.augos.mysolar.R;
import com.org.augos.mysolar.Server.Backend;
import com.org.augos.mysolar.network.VolleyCall;
import com.org.augos.mysolar.network.VolleyCallback;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentActivityGraph extends BaseFragment {
    LineChart battery_chart;
    LineChart generate_chart;
    LineChart grid_chart;
    LineChart consumption_chart;

    LinearLayout hourly_option;
    LinearLayout daily_option;
    LinearLayout monthly_option;
    LinearLayout weekly_option;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ativity_graph, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View view){
        battery_chart = view.findViewById(R.id.battery_chart);
        generate_chart = view.findViewById(R.id.generation_chart);
        grid_chart = view.findViewById(R.id.grid_chart);
        consumption_chart = view.findViewById(R.id.consumption_chart);

        hourly_option = view.findViewById(R.id.hourly_option);
        daily_option = view.findViewById(R.id.daily_option);
        monthly_option = view.findViewById(R.id.monthly_option);
        weekly_option = view.findViewById(R.id.weekly_option);

        hourly_option.setOnClickListener(this);
        daily_option.setOnClickListener(this);
        monthly_option.setOnClickListener(this);
        weekly_option.setOnClickListener(this);

        initStyle();
        daily_option.setBackground(getResources().getDrawable(R.drawable.radio_selected));
        call_data("1", 1);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == hourly_option){
            initStyle();
            hourly_option.setBackground(getResources().getDrawable(R.drawable.radio_selected));
            call_data("0", 0);
        }else if(view == daily_option){
            initStyle();
            daily_option.setBackground(getResources().getDrawable(R.drawable.radio_selected));
            call_data("1", 1);
        }else if(view == weekly_option){
            initStyle();
            weekly_option.setBackground(getResources().getDrawable(R.drawable.radio_selected));
            call_data("2", 2);
        }else if(view == monthly_option){
            initStyle();
            monthly_option.setBackground(getResources().getDrawable(R.drawable.radio_selected));
            call_data("2", 3);
        }
    }
    private void initStyle(){
        hourly_option.setBackground(getResources().getDrawable(R.drawable.radio_regular));
        daily_option.setBackground(getResources().getDrawable(R.drawable.radio_regular));
        monthly_option.setBackground(getResources().getDrawable(R.drawable.radio_regular));
        weekly_option.setBackground(getResources().getDrawable(R.drawable.radio_regular));
    }
    private void call_data(String type, final int option){
        if (!Backend.isNetworkAvailable()) {
            showMessage("Not network connection !");
            return;
        }
        dialogHelper.showProgressDialog();
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        VolleyCall.getResponse(getContext(), Params.CLOUD_URL + "get_data", Request.Method.POST, params, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                dialogHelper.closeDialog();
                try {
                    Gson gson = new Gson();
                    Type ListType = new TypeToken<ArrayList<DataModel>>(){}.getType();
                    List<DataModel> list = gson.fromJson(response, ListType);

                    if(option != 3) {
                        drawBattery(list, option);
                        drawGeneration(list, option);
                        drawGrid(list, option);
                        drawConsumption(list, option);
                    }else{
                        List<DataModel> month_list = new ArrayList<>();
                        month_list.addAll(list);
                        month_list.addAll(list);
                        month_list.addAll(list);
                        month_list.addAll(list);

                        drawBattery(month_list, option);
                        drawGeneration(month_list, option);
                        drawGrid(month_list, option);
                        drawConsumption(month_list, option);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onVolleyError(String error){
                dialogHelper.closeDialog();
                showMessage(error);
            }
        });
    }

    private void drawGrid(List<DataModel> list, int option){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList labels = new ArrayList();
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            float val = list.get(i).grid_feed;
            NoOfEmp.add(new BarEntry(val, i));
            sum += val;
            labels.add("");
        }
        LineDataSet bardataset = new LineDataSet(NoOfEmp, "Grid Exchange");
        grid_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        grid_chart.setData(data);
        grid_chart.setDescription(sum + " KW");
        if(option == 0){
            bardataset.setDrawValues(true);
            grid_chart.setVisibleXRangeMaximum(25);
        }else if(option == 1){
            grid_chart.setVisibleXRangeMaximum(25);
        }else if(option == 2){
            grid_chart.setVisibleXRangeMaximum(80);
        }else if(option == 3){
            grid_chart.setVisibleXRangeMaximum(170);
        }

    }
    private void drawBattery(List<DataModel> list, int option){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList labels = new ArrayList();
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            float val = list.get(i).battery;
            NoOfEmp.add(new BarEntry(val, i));
            sum += val;
            labels.add("");
        }

        LineDataSet bardataset = new LineDataSet(NoOfEmp, "Battery State");
        battery_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        battery_chart.setData(data);
        battery_chart.setDescription((int)(sum / list.size()) + " %");
        if(option == 0){
            bardataset.setDrawValues(true);
            battery_chart.setVisibleXRangeMaximum(25);
        }else if(option == 1){
            battery_chart.setVisibleXRangeMaximum(25);
        }else if(option == 2){
            battery_chart.setVisibleXRangeMaximum(80);
        }else if(option == 3){
            battery_chart.setVisibleXRangeMaximum(170);
        }
    }
    private void drawGeneration(List<DataModel> list, int option){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList labels = new ArrayList();
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            float val = list.get(i).generation;
            NoOfEmp.add(new BarEntry(val, i));
            sum += val;
            labels.add("");
        }
        LineDataSet bardataset = new LineDataSet(NoOfEmp, "Generation");
        generate_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        generate_chart.setData(data);
        generate_chart.setDescription(sum + " KW");
        if(option == 0){
            bardataset.setDrawValues(true);
            generate_chart.setVisibleXRangeMaximum(25);
        }else if(option == 1){
            generate_chart.setVisibleXRangeMaximum(25);
        }else if(option == 2){
            generate_chart.setVisibleXRangeMaximum(80);
        }else if(option == 3){
            generate_chart.setVisibleXRangeMaximum(170);
        }
    }
    private void drawConsumption(List<DataModel> list, int option){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList labels = new ArrayList();
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            float val = list.get(i).consumption;
            NoOfEmp.add(new BarEntry(val, i));
            sum += val;
            labels.add("");
        }
        LineDataSet bardataset = new LineDataSet(NoOfEmp, "site Consumption");
        consumption_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        consumption_chart.setData(data);
        consumption_chart.setDescription(sum + " KW");
        if(option == 0){
            bardataset.setDrawValues(true);
            consumption_chart.setVisibleXRangeMaximum(25);
        }else if(option == 1){
            consumption_chart.setVisibleXRangeMaximum(25);
        }else if(option == 2){
            consumption_chart.setVisibleXRangeMaximum(80);
        }else if(option == 3){
            consumption_chart.setVisibleXRangeMaximum(170);
        }
    }
}
