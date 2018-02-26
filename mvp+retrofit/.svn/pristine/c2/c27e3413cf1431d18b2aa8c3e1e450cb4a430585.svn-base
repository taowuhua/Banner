package com.ryx.credit.tnh;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ryx.baselib.utils.MvpAppConfig;
import com.ryx.payment.ryxhttp.OkHttpUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by laomao on 2017/11/28.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHostUrl();
        initOkHttp();
        initLoger();
    }

    private void initHostUrl() {
        MvpAppConfig.setHostUrl(AppConfig.BASE_URL);
    }

    private void initOkHttp() {
        final int CONNECT_TIMEOUT = 45;
        final int READ_TIMEOUT = 45;
        final int WRITE_TIMEOUT = 45;
        OkHttpUtils httpUtils = OkHttpUtils.getInstance().debug("OkHttpUtils");
        httpUtils.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpUtils.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        httpUtils.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
//        httpUtils.setCertificates();//忽略证书
        try {
            OkHttpUtils.getInstance().setCertificates(getAssets().open("STAR.ruiyinxin.com.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initLoger(){
        Logger
        .init("ryx")              // 默认为PRETTYLOGGER，可以设置成为自定义tag
        .methodCount(2)             // logger所在方法显示开关 0 为不显示，1、2 为不同的方法信息显示样式
//       .hideThreadInfo()              // 线程信息显示，默认打开
        .logLevel(BuildConfig.DEBUG? LogLevel.FULL:LogLevel.NONE);    // 默认是打开日志显示（FULL），关闭（NONE）
//        .methodOffset(0);日志展示默认模式就是0
    }

}
