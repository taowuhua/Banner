package com.ryx.baselib.mvpframe.base;

import com.ryx.baselib.utils.MvpRetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

/**
 * Author：xucc
 * date: 2017/11/30 14:53
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public class MvpApiFactory {
    private static  Map<String,Object> aipserviceMap=new HashMap<>();

    /**
     * 获取ServiceAPI
     * @param clz
     * @param <T>
     * @return
     */
    public static  <T> T getService(Class<T> clz){
        if(clz==null){
            return null;
        }
        if(aipserviceMap.get(clz.getName())==null){
            T clzClass=    MvpRetrofitUtil.createAPI(clz);
            aipserviceMap.put(clz.getName(),clzClass);
            return clzClass;
        }else{
            return (T)aipserviceMap.get(clz.getName());
        }
    }
}
