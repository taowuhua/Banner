package com.bank.quickpay.bean;

/**
 * Created by xpp on 2017/11/2 0002.
 */

public class EarningCircleBean{

    private String localdate ;//收益日期
    private String amount;//收益数量
    private String amount1 ;//一级圈友
    private String amount2 ;//二级圈友

    public String getLocaldate() {
        return localdate;
    }

    public void setLocaldate(String localdate) {
        this.localdate = localdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }
}
