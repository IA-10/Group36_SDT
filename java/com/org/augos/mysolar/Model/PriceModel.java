package com.org.augos.mysolar.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceModel implements Serializable {
    @SerializedName("id")
    public String id = "";
    @SerializedName("price")
    public float price;
}
