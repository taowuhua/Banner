package com.bank.quickpay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 */

public class PreferenceUtil {

    private static final String PREFERENCE_NAME = "quickpay";

    private static PreferenceUtil preferenceUtil;

    private SharedPreferences shareditorPreferences;

    private Editor editor;

    private PreferenceUtil(Context context) {
        init(context);
    }

    public void init(Context context) {
        if (shareditorPreferences == null || editor == null) {
            try {
                shareditorPreferences = context.getSharedPreferences(
                        PREFERENCE_NAME, 0);
                editor = shareditorPreferences.edit();
            } catch (Exception e) {
            }
        }
    }

    public static PreferenceUtil getInstance(Context context) {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferenceUtil(context);
        }
        return preferenceUtil;
    }


    public static boolean checkIsFirstLogin(Context context, String activityname) {

        preferenceUtil = PreferenceUtil.getInstance(context);
        boolean isfirstin = preferenceUtil.getBoolean(activityname, true);
        if (isfirstin) {    // 如果是第一次登陆，则保存为false
            preferenceUtil.saveBoolean(activityname, false);
            LogUtil.showLog(activityname, " first come");

        } else {
            LogUtil.showLog(activityname, "not first come");

        }
        return isfirstin;

    }


    public void saveLong(String key, long l) {
        editor.putLong(key, l);
        editor.commit();
    }

    public long getLong(String key, long defaultlong) {
        return shareditorPreferences.getLong(key, defaultlong);
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultboolean) {
        return shareditorPreferences.getBoolean(key, defaultboolean);
    }

    public void saveInt(String key, int value) {
        if (editor != null) {
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public int getInt(String key, int defaultInt) {
        return shareditorPreferences.getInt(key, defaultInt);
    }

    public String getString(String key, String defaultInt) {
        return shareditorPreferences.getString(key, defaultInt);
    }

    public String getString(Context context, String key, String defaultValue) {
        if (shareditorPreferences == null || editor == null) {
            shareditorPreferences = context.getSharedPreferences(
                    PREFERENCE_NAME, 0);
            editor = shareditorPreferences.edit();
        }
        if (shareditorPreferences != null) {
            return shareditorPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public boolean hasKey(String key){
        return  shareditorPreferences.contains(key);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void destroy() {
        shareditorPreferences = null;
        editor = null;
        preferenceUtil = null;
    }
}

