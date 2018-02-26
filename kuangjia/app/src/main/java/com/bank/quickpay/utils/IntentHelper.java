
package com.bank.quickpay.utils;

import com.bank.quickpay.activity.earningscircle.EarningCircleActivity;
import com.bank.quickpay.activity.helping.HtmlMessageActivity;
import com.bank.quickpay.activity.invitecode.MyInviteCodeActivity;
import com.bank.quickpay.activity.mymsg.MyInfoActivity;
import com.bank.quickpay.activity.payment.CollectionActivity;
import com.bank.quickpay.activity.transactiondetails.TradeRecordActivity;

import java.util.HashMap;

public class IntentHelper {
    private static IntentHelper instance;

    public static IntentHelper getInstance() {
        return instance = (null != instance) ? instance : new IntentHelper();
    }

    private static final HashMap<String, Class> ACTIVITY_CLASS = new HashMap<String, Class>();
    {
        ACTIVITY_CLASS.put("CollectionActivity", CollectionActivity.class);//收款
        ACTIVITY_CLASS.put("EarningCircleActivity", EarningCircleActivity.class);//收益圈
        ACTIVITY_CLASS.put("TradeRecordActivity", TradeRecordActivity.class);//交易明细
        ACTIVITY_CLASS.put("MyInviteCodeActivity", MyInviteCodeActivity.class);//我的邀请码
        ACTIVITY_CLASS.put("MyInfoActivity", MyInfoActivity.class);//我的信息
        ACTIVITY_CLASS.put("HtmlMessageActivity", HtmlMessageActivity.class);//帮助说明
    }

    public Class getActivityClass(String name) {
        return ACTIVITY_CLASS.get(name);
    }

    public boolean contains(String name) {
        return ACTIVITY_CLASS.containsKey(name);
    }
}
