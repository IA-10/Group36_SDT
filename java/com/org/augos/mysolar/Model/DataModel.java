package com.org.augos.mysolar.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {
    @SerializedName("id")
    public String id = "";
    @SerializedName("grid_rate")
    public float gird_rate;
    @SerializedName("grid_feed")
    public float grid_feed;
    @SerializedName("generation")
    public float generation;
    @SerializedName("consumption")
    public float consumption;
    @SerializedName("battery")
    public float battery;
    @SerializedName("type")
    public String type;
}
