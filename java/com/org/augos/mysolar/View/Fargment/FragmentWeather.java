package com.org.augos.mysolar.View.Fargment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.augos.mysolar.Base.BaseFragment;
import com.org.augos.mysolar.Helper.LocationTracker;
import com.org.augos.mysolar.Helper.Params;
import com.org.augos.mysolar.Model.WeatherMain;
import com.org.augos.mysolar.R;
import com.org.augos.mysolar.Server.Backend;
import com.org.augos.mysolar.network.VolleyCall;
import com.org.augos.mysolar.network.VolleyCallback;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentWeather extends BaseFragment {
    TextView date_view;
    TextView time_view;
    TextView temp_view;
    ImageView icon_view;
    TextView high_temp_view;
    TextView wind_view;
    LinearLayout weather_list_view;
    LayoutInflater inflater;

    String symbol = "°C";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    private void initView(View view){
        date_view = view.findViewById(R.id.date_view);
        time_view = view.findViewById(R.id.time_view);
        temp_view = view.findViewById(R.id.temp_view);
        high_temp_view = view.findViewById(R.id.high_temp_view);
        icon_view = view.findViewById(R.id.weather_icon);
        wind_view = view.findViewById(R.id.wind_view);
        weather_list_view = view.findViewById(R.id.weather_list);

        inflater = this.getLayoutInflater();

        initTime();
        forecastWeather();
    }
    private HashMap<String, String> getWeatherParam(){
        HashMap<String, String> params = new HashMap<String, String>();

        LocationTracker tracker = new LocationTracker(getContext());
        Location location = tracker.getLocation();
        if(location == null){
            params.put("lat", "33.44179");
            params.put("lon", "-94.037689");
        }else{
            params.put("lat", location.getLatitude() + "");
            params.put("lon", location.getLongitude() + "");
        }
        params.put("exclude", "daily");
        params.put("units", "metric");
        params.put("appid", Params.API_KEY);
        return params;
    }
    private void forecastWeather(){
        if (!Backend.isNetworkAvailable()) {
            showMessage("Not network connection !");
            return;
        }
        dialogHelper.showProgressDialog();
        final HashMap<String, String> params = getWeatherParam();
        VolleyCall.getResponse(getContext(), Params.WEATHER_URL, Request.Method.GET, params, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                dialogHelper.closeDialog();
                try {
                    JSONObject response_obj = new JSONObject(response);
                    String res = response_obj.getString("cod");
                    if(res.equals("200")){
                        Gson gson = new Gson();
                        Type ListType = new TypeToken<ArrayList<WeatherMain>>(){}.getType();
                        List<WeatherMain> list = gson.fromJson(response_obj.getString("list"), ListType);
                        if(list != null && !list.isEmpty()){
                            initCurrentWeather(list.get(0));
                            weather_list_view.removeAllViews();
                            for(int i = 1; i < list.size(); i++){
                                addWeather(list.get(i));
                            }
                        }
                    }else{

                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onVolleyError(String error){
                dialogHelper.closeDialog();
            }
        });
    }
    private void initTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        format = new SimpleDateFormat("MM/dd");
        String date_str = format.format(date);
        format = new SimpleDateFormat("HH:mm");
        String time_str = format.format(date);

        date_view.setText(date_str);
        time_view.setText(time_str);
    }
    private void initCurrentWeather(WeatherMain model){
        try{

            temp_view.setText(model.main.temp);
            high_temp_view.setText("High: " + model.main.temp_max + symbol + "  |  " + "Low: " + model.main.temp_min + symbol);
            wind_view.setText("Wind: " + model.wind.speed + "m/s" + "  |  " + "Humidity: " + model.main.humidity + "%");

            Glide.with(getContext())
                    .load(Params.ICON_URL + model.weather_list.get(0).icon + ".png")
                    .into(icon_view);

        }catch (Exception e){

        }
    }
    private void addWeather(WeatherMain model){

        View view = inflater.inflate(R.layout.layout_weather_item, null);
        TextView date_view = view.findViewById(R.id.date_view);
        TextView temp_view = view.findViewById(R.id.temp_view);
        ImageView icon_view = view.findViewById(R.id.weather_icon);

        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(model.date);
            format = new SimpleDateFormat("MM/dd HH:mm");
            date_view.setText(format.format(date));
            temp_view.setText(model.main.temp_min + symbol + "  " + model.main.temp_max + symbol);
            Glide.with(getContext())
                    .load(Params.ICON_URL + model.weather_list.get(0).icon + ".png")
                    .into(icon_view);
            weather_list_view.addView(view);
        }catch (Exception e){

        }
    }
}
