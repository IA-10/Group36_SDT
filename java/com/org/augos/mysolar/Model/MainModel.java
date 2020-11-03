package com.org.augos.mysolar.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainModel implements Serializable {
    @SerializedName("temp")
    public String temp = "";
    @SerializedName("feels_like")
    public String feels_like = "";
    @SerializedName("temp_min")
    public String temp_min = "";
    @SerializedName("temp_max")
    public String temp_max = "";
    @SerializedName("pressure")
    public String pressure = "";
    @SerializedName("sea_level")
    public String sea_level = "";
    @SerializedName("grnd_level")
    public String grnd_level = "";
    @SerializedName("humidity")
    public String humidity = "";
    @SerializedName("temp_kf")
    public String temp_kf = "";
}
