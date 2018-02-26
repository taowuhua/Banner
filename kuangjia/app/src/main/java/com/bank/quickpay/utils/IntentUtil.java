package com.bank.quickpay.utils;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by xpp on 2017/11/7 0007.
 */

public class IntentUtil {

    public static String  getStringExtra(Intent intent,String key){
        if(intent.hasExtra(key)){
            return intent.getStringExtra(key);
        }else{
            return "";
        }
    }

    public static Serializable getSerializableExtra(Intent intent, String key){
        if(intent.hasExtra(key)){
            return intent.getSerializableExtra(key);
        }else{
            return null;
        }
    }
}
