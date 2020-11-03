package com.org.augos.mysolar.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherMain implements Serializable {
    @SerializedName("main")
    public MainModel main = new MainModel();
    @SerializedName("weather")
    public List<Weather> weather_list = new ArrayList<>();
    @SerializedName("wind")
    public Wind wind = new Wind();
    @SerializedName("dt_txt")
    public String date = "";
}
