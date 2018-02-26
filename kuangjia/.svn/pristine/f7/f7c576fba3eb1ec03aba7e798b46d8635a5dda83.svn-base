package com.bank.quickpay.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Description：JSON转换工具类
 */
public class FastJSONUtils {

    private static FastJSONUtils instance=new FastJSONUtils ();
    public  synchronized  static  FastJSONUtils getInstance(){

        return instance;
    }
    /**
     * json字符串转换成json对象
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseJSONObject(String jsonStr,Class<T> clazz){
        return JSON.parseObject (jsonStr,clazz);
    }

    public  <T> List<T> parseJSONArray(String jsonStr, Class<T> clazz){
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * json对象转换成json字符串
     * @param result
     * @param <T>
     * @return
     */
    public <T> String toJSONString(T result){

       return JSON.toJSONString (result);
    }
}
