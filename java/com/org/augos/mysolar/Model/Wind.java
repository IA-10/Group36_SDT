package com.org.augos.mysolar.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {
    @SerializedName("speed")
    public String speed = "";
    @SerializedName("deg")
    public String deg = "";
}
