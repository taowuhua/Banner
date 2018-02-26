package com.bank.quickpay.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/10.
 */
public class JsonUtil {
    /**
     * jsonObject中获取字符串值
     * @param jsonObject
     * @param key
     * @return
     * @throws JSONException
     */
        public static String getValueFromJSONObject(JSONObject jsonObject, String key) throws JSONException {
            String result = "";
            if (jsonObject!=null&&jsonObject.has(key)) {
                result = jsonObject.getString(key);
            }
            return result;
        }

    /**
     * JsonObjet中获取JsonObject值
     * @param jsonObject
     * @param key
     * @return
     * @throws JSONException
     */
        public static JSONObject getJSONObjectFromJsonObject(JSONObject jsonObject, String key) throws JSONException {
            JSONObject result=null;
            if (jsonObject!=null&&jsonObject.has(key)) {
                result = jsonObject.getJSONObject(key);
            }
            return result;
        }

        public static boolean isArrayEmpty(JSONArray array){
            return array==null||array.length()==0;
        }
    }
