package com.bank.quickpay;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.bank.quickpay.utils.QuickPayAppData;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/10/31.
 */

public class QuickPayApplication extends MultiDexApplication {
    public static QuickPayApplication instance;
    @Override
    public void onCreate() {
        initLoger();
        initUmeng();
        super.onCreate();
    }

    private void initLoger() {
        Logger.init("quickpay")              // 默认为PRETTYLOGGER，可以设置成为自定义tag
                .methodCount(2)             // logger所在方法显示开关 0 为不显示，1、2 为不同的方法信息显示样式
//       .hideThreadInfo()              // 线程信息显示，默认打开
                .logLevel(BuildConfig.DEBUG? LogLevel.FULL:LogLevel.NONE);    // 默认是打开日志显示（FULL），关闭（NONE）
//        .methodOffset(0);日志展示默认模式就是0
    }

    /**
     * 初始化友盟友盟
     */
    private void initUmeng() {
        //设置场景类型
        // EScenarioType. E_UM_NORMAL　　普通统计场景类型
        //  EScenarioType. E_UM_GAME     　　游戏场景类型
        //  EScenarioType. E_UM_ANALYTICS_OEM  统计盒子场景类型
        //  EScenarioType. E_UM_GAME_OEM      　 游戏盒子场景类型
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
    // 单例模式中获取唯一的MyApplication实例
    public static QuickPayApplication getInstance() {
        if (null == instance) {
            instance = new QuickPayApplication();
        }
        return instance;
    }
    public void exit() {
        //杀死进程前保存统计数据
        MobclickAgent.onKillProcess(instance);
        System.exit(0);
    }
}
