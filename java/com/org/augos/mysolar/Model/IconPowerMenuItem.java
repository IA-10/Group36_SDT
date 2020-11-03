package com.org.augos.mysolar.Model;

import android.graphics.drawable.Drawable;

public class IconPowerMenuItem {
    private Drawable icon;
    private String title;

    public IconPowerMenuItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public Drawable getIcon(){
        return icon;
    }
}
