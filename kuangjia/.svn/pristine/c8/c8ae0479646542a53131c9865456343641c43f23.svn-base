
package com.bank.quickpay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.bank.quickpay.bean.Param;

import java.util.ArrayList;

public class QuickPayAppData {

    private static Context context;

    private String token = "0000";
    private boolean login = false; // 是否登录
    private String mobileNo = ""; // 代表登录账户手机号
    private String phone = ""; // 目前设置为跟登录号一样， 以后要获取登录的手机的手机号
    private String realName = ""; // 真实姓名/商户名称

    private String customerName = ""; // 法人名称
    private int authenFlag; // 用户是否已认证

    private String customerId; // 用户ID；
    private String certPid; // 证件号码；
    private String certType; // 认证类型
    private String userType; // 用户类型
    private String email; // 用户email

    private String tagDesc; // 用户审核状态名

    private String transLogNo;

    private static QuickPayAppData instance = null;;
    private ArrayList<Param> qtpayPublicAttributeList = new ArrayList<Param>();
    // 存储公用的Qtpay里的属性

    private Param tokenafterlogin;
    private Param phoneafterlogin;
    private Param signbeforelogin;

    // 单例模式中获取唯一的MyApplication实例
    // public static QtpayAppData getInstance() {
    // if (null == instance) {
    // instance = new QtpayAppData();
    // }
    // return instance;
    // }

    private QuickPayAppData() {

    }

    public static QuickPayAppData getInstance(Context mcontext) {

        context = mcontext.getApplicationContext();

        if (instance == null) {
            instance = new QuickPayAppData();
        }

        return instance;
    }

    public String getToken() {
        token = PreferenceUtil.getInstance(context).getString("qtpaytoken", "0000");

        return token;
    }

    public boolean isLogin() {
        login = PreferenceUtil.getInstance(context).getBoolean("qtpaylogin", false);
        return login;
    }

    public void setLogin(boolean login) {
        PreferenceUtil.getInstance(context).saveBoolean("qtpaylogin", login);
        this.login = login;
    }

    public String getMobileNo() {
        mobileNo = PreferenceUtil.getInstance(context).getString("qtpaymobileno", "");
        return mobileNo;
    }

    public String getPhone() {
        phone = PreferenceUtil.getInstance(context).getString("qtpayphone", "");

        return phone;
    }

    public void setToken(String token) {
        PreferenceUtil.getInstance(context).saveString("qtpaytoken", token);
        this.token = token;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        PreferenceUtil.getInstance(context).saveString("qtpaymobileno", mobileNo);

    }

    public void setPhone(String phone) {
        this.phone = phone;
        PreferenceUtil.getInstance(context).saveString("qtpayphone", phone);
    }

    public String getCustomerId() {

        customerId = PreferenceUtil.getInstance(context).getString("qtpaycustomerid", "0000");

        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
        PreferenceUtil.getInstance(context).saveString("qtpaycustomerid", customerId);

    }

    public String getRealName() {

        realName = PreferenceUtil.getInstance(context).getString("qtpayrealname", realName);
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        PreferenceUtil.getInstance(context).saveString("qtpayrealname", realName);

    }

    public int getAuthenFlag() {
        authenFlag = PreferenceUtil.getInstance(context).getInt("qtpayauthenflag", 0);
        return authenFlag;
    }

    public void setAuthenFlag(int authenFlag) {
        PreferenceUtil.getInstance(context).saveInt("qtpayauthenflag", authenFlag);
        this.authenFlag = authenFlag;
    }

    public String getCertPid() {
        this.certPid = PreferenceUtil.getInstance(context).getString("certPid", "");
        return certPid;
    }

    public void setCertPid(String certPid) {
        PreferenceUtil.getInstance(context).saveString("certPid",certPid);
        this.certPid = certPid;
    }

    public String getCertType() {
        return certType;
    }

    public String getUserType() {
        userType = PreferenceUtil.getInstance(context).getString("qtpayusertype", "");
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public void setUserType(String userType) {
        PreferenceUtil.getInstance(context).saveString("qtpayusertype", userType);
        this.userType = userType;
    }

    public ArrayList<Param> getQtpayPublicAttributeList() {
        return qtpayPublicAttributeList;
    }

    public void setQtpayPublicAttributeList(
            ArrayList<Param> qtpayPublicAttributeList) {
        this.qtpayPublicAttributeList = qtpayPublicAttributeList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTransLogNo() {
        return transLogNo;
    }

    public void setTransLogNo(String transLogNo) {
        this.transLogNo = transLogNo;
    }

    public Param getTokenafterlogin() {
        return tokenafterlogin;
    }

    public Param getPhoneafterlogin() {
        return phoneafterlogin;
    }

    public Param getSignbeforelogin() {
        return signbeforelogin;
    }

    public void setTokenafterlogin(Param tokenafterlogin) {
        this.tokenafterlogin = tokenafterlogin;
    }

    public void setPhoneafterlogin(Param phoneafterlogin) {
        this.phoneafterlogin = phoneafterlogin;
    }

    public void setSignbeforelogin(Param signbeforelogin) {
        this.signbeforelogin = signbeforelogin;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTagDesc() {

        tagDesc = PreferenceUtil.getInstance(context).getString("qtpaytagDesc", "0000");

        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {

        PreferenceUtil.getInstance(context).saveString("qtpaytagDesc", tagDesc);

        this.tagDesc = tagDesc;
    }

    /**
     * 瑞刷有关
     * 
     * @param mobileNo
     * @param cardNo
     * @param cardIdx
     */
    public void saveRuishuaparam(String mobileNo, String cardNo, String cardIdx) {

        SharedPreferences shareditorPreferences = context.getSharedPreferences(mobileNo, 0);
        Editor editor = shareditorPreferences.edit();

        editor.putString("cardNo", cardNo);
        editor.putString("cardIdx", cardIdx);

        editor.commit();

    }

    public String getRuishuaParam(String mobileNo, String key, String defaultValue) {
        return context.getSharedPreferences(mobileNo, 0).getString(key, defaultValue);
    }

}
