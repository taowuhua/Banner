package com.ryx.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryx.payment.ryxhttp.OkHttpUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2工具类
 *
 * @author muxin
 * @time 2016-07-21 15:44
 */
public class MvpRetrofitUtil {
    private MvpRetrofitUtil() {
    }
    private static Retrofit retrofit = null;
    public static <T> T createAPI(Class<T> clz) {
        if (retrofit == null) {
            synchronized (MvpRetrofitUtil.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                            .serializeNulls().create();
                    //创建Retrofit2
                    retrofit = new Retrofit.Builder()
                            .baseUrl(MvpAppConfig.getHost())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            //gson解析为java对象,可以自定义gson对象比如格式化时间等
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(OkHttpUtils.getInstance().getOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit.create(clz);
    }
}
