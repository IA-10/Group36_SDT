package com.org.augos.mysolar.Helper;

import java.text.NumberFormat;
import java.util.Locale;

public class StringHelper {

    public static String[] days_array = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Everyday"};

    public static boolean isEmpty(String content){
        return content == null || content.trim().isEmpty();
    }
    public static String replaceComma(String content){
        if(isEmpty(content)) return content;
        return content.replaceAll(",", ".");
    }
    public static float getNumber(String num){
        if(isEmpty(num)) return 0;
        try{
            Number number = NumberFormat.getNumberInstance(Locale.UK).parse(replaceComma(num.trim()));
            return number.floatValue();
        }catch (Exception e){
            return 0;
        }
    }
}
