package com.bank.quickpay.http;


/**
 * Created by laomao on 16/4/19.
 */
public abstract class XmlCallback {
//     public void success(RyxPayResult payResult);
//    public void fail(String message);

    /**
     *  交易成功
     * @param payResult 返回结果
     */
    public abstract void onTradeSuccess(QuickPayResult payResult);

    /**
     * 登录异常
     */
    public  void onLoginAnomaly(){};

    /**
     * 其他状态
     */
    public  void onOtherState(){};

    /**
     * 是否Toast显示Other的msg
     * @return
     */
    public boolean isToastOtherMsg(){return true;};
    /**
     * 其他状态带code,Desc信息
     */
    public  void onOtherState(String rescode,String resDesc){

    };

    /**
     * 失败状态
     */
    public  void onTradeFailed(){};
}
