package com.bank.quickpay.utils;


import android.text.TextUtils;

/**
 * Creaed by Administrator on 2016/11/24.
 */

public class CNummberUtil {

    public static double parseDouble(String doubleStr,double defaultNum) {
        if(TextUtils.isEmpty(doubleStr)) {
            return defaultNum;
        }
        double num = defaultNum;
        try{
            num = Double.parseDouble(doubleStr);
        }catch(NumberFormatException e){
            e.printStackTrace();

        }
        return num;
    }
    public static int parseInt(String intStr,int defaultNum) {
        if(TextUtils.isEmpty(intStr)) {
            return defaultNum;
        }
        int num = defaultNum;
        try{
            num = Integer.parseInt(intStr);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return num;
    }
    public static int parseIntRadix(String intStr,int radix,int defaultNum) {
        if(TextUtils.isEmpty(intStr)) {
            return defaultNum;
        }
        int num = defaultNum;
        try{
            num = Integer.parseInt(intStr,radix);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return num;
    }

    public static float parseFloat(String floatStr,float defaultNum) {
        if(TextUtils.isEmpty(floatStr)) {
            return defaultNum;
        }
        float num = defaultNum;
        try{
            num = Float.parseFloat(floatStr);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return num;
    }

}
