package com.org.augos.mysolar.View.Fargment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.augos.mysolar.Base.BaseFragment;
import com.org.augos.mysolar.Helper.Params;
import com.org.augos.mysolar.Model.DataModel;
import com.org.augos.mysolar.Model.PriceModel;
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

public class FragmentPricing extends BaseFragment {
    LineChart generation_chart;
    LineChart price_chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pricing, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View view){
        generation_chart = view.findViewById(R.id.generation_chart);
        price_chart = view.findViewById(R.id.price_chart);
        call_price();
    }
    private void call_price(){
        if (!Backend.isNetworkAvailable()) {
            showMessage("Not network connection !");
            return;
        }
        dialogHelper.showProgressDialog();
        final HashMap<String, String> params = new HashMap<String, String>();
        VolleyCall.getResponse(getContext(), Params.CLOUD_URL + "get_price", Request.Method.POST, params, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                dialogHelper.closeDialog();
                try {
                    JSONObject reponse_obj = new JSONObject(response);
                    Gson gson = new Gson();
                    Type ListType = new TypeToken<ArrayList<PriceModel>>(){}.getType();
                    List<PriceModel> price_list = gson.fromJson(reponse_obj.getString("price"), ListType);
                    ListType = new TypeToken<ArrayList<DataModel>>(){}.getType();
                    List<DataModel> generation_list = gson.fromJson(reponse_obj.getString("generation"), ListType);
                    initPriceChart(price_list);
                    initGenerationChart(generation_list);
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
    private void initPriceChart(List<PriceModel> list){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList labels = new ArrayList();
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            float val = list.get(i).price;
            NoOfEmp.add(new BarEntry(val, i));
            sum += val;
            labels.add("");
        }
        LineDataSet bardataset = new LineDataSet(NoOfEmp, "Pricing($/MWh)");
        price_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        price_chart.setData(data);
        price_chart.setDescription("");
        price_chart.setVisibleXRangeMaximum(30);
    }
    private void initGenerationChart(List<DataModel> list){
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
        generation_chart.animateY(1500);
        LineData data = new LineData(labels, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        bardataset.setDrawValues(false);
        bardataset.setDrawCircles(false);
        bardataset.setDrawFilled(true);
        bardataset.setDrawCubic(false);
        bardataset.setCubicIntensity(0.2f);
        generation_chart.setData(data);
        generation_chart.setDescription("");
        generation_chart.setVisibleXRangeMaximum(30);
    }
}
