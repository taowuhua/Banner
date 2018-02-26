package com.bank.quickpay.config;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.bank.quickpay.bean.Param;
import com.bank.quickpay.utils.PreferenceUtil;

import java.util.ArrayList;

/**
 * 配置信息
 * Created by xpp on 2017/11/2 0002.
 */

public class AppConfig {

    private static Context context;
    private static AppConfig instance = null;

    public static String APPUSER = "kjfx";
    public static String BRANCHID = "00800321";
    public static String API_SIGN_KEY = "s0mvr01ga0mavhiiqh36lvl9ah800321";
    public static final String CLIENTVERSION = "5.0.0";
    public static final String CLIENTTYPE = "02";
    private String token = "0000";
    private String phone = ""; // 目前设置为跟登录号一样， 以后要获取登录的手机的手机号
    private String mobileNo = ""; // 代表登录账户手机号
    private String customerId; // 用户ID；
    public static final String QTNET_TRANSLOGFAIL = "9987";
    public static final String QTNET_SUCCESS2 = "0";
    public static final String QTNET_OUTLOGIN2 = "0001";
    public static final String QTNET_OUTLOGIN1 = "0002";
    public static final String QTNET_SUCCESS = "0000";
    public static final int WILL_BE_CLOSED = 8888;// activity关闭控制
    public static final int CLOSE_ALL = 8886; // 退出代号
    public static final int UPLOAD_FINISH = 0x556;//上传照片成功
    public final static int TO_UPLOAD = 0X550;//去上传照片
    public static final int TO_AUTH = 0x557;//去实名认证
    public static final int TO_BINDCARD = 0x558;//去绑定结算卡
    public static final int UPLOAD_BACK = 0x559;//上传照片返回
    public static final boolean DEBUG = false;
    private String realName = ""; // 真实姓名/商户名称
    private int authenFlag; // 用户是否已认证
    private String customerName = ""; // 法人名称
    public static final int TOLOGINACT = 0x566;//跳转登录页requestCode
    public static final int TASKSUCCESS = 0x001;//页面resutCode:任务完成code
    public static final String TEMP_IMAGENAME = "temp_image_name";
    public static final String IMAG_IDENTITY2 = "identity_img2";
    public static final String IMAG_IDENTITY1 = "identity_img1";
    public static final String IMAG_PROFILE = "profile_img1";
    public static final String LICENSEINFORMATIONAGREEMENT="LicenseInformationAgreement.info";//授权信息使用协议（快捷开通）

    public static final String Notes_Tips = "AuthTips.info";//拍照提示
    public static final String HELPEXPLAIN="HelpExplain.info";//帮助说明
    public static final String UPLOADIMAGE="UploadImage.info";//上传图片帮助说明
    public static final String SUPPORTBANKSLIMIT="SupportBanksLimit.info";//支持银行卡及限额说明
    public static final String SUBRUN="SubRun.info";//收益圈收益说明
    public static final String RATE="Rate.info";//费率说明
    public static final String MYINVITATIONCODE="MyInvitationCode.info";//我的邀请码帮助说明
    public static final String REGISTRATIONAGREEMENT="RegistrationAgreement.info";//注册协议
    public static final String BINDSETTLEMENTCARD="BindSettlementCard.info";//绑卡结算支持卡列表说明
    public static final String FEE="Fee.info";//提现手续费说明
    public static final String Notes_BlackMSG = "Agreement.info";


    private String certPid; // 证件号码；

    //瑞刷生产URL
    public static final String BASE_RELEASE_URL = "https://mposprepo.ruiyinxin.com:443/unifiedAction.do";
//    public static final String BASE_RELEASE_URL = "https://mpostest.ruiyinxin.com:443/unifiedAction.do";
    /**
     * 银行图标URL
     */
    public static final String BANKIMG_URL = "https://mposprepo.ruiyinxin.com:443/banklogo/placeholder.png";

    public static final String imageCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/imob/imageCache/";
    public static String imageCachePath_data;
    private boolean login = false; // 是否登录
    public static final int LOGINACTFINISH = 0x567;//登录页finish

    public static AppConfig getInstance(Context mcontext) {

        context = mcontext.getApplicationContext();

        if (instance == null) {
            instance = new AppConfig();
        }

        return instance;
    }

    public static String gethost() {
            return BASE_RELEASE_URL;
    }

    private ArrayList<Param> qtpayPublicAttributeList = new ArrayList<Param>();

    public ArrayList<Param> getQtpayPublicAttributeList() {
        return qtpayPublicAttributeList;
    }

    public void setQtpayPublicAttributeList(
            ArrayList<Param> qtpayPublicAttributeList) {
        this.qtpayPublicAttributeList = qtpayPublicAttributeList;
    }

    public boolean isLogin() {
        login = PreferenceUtil.getInstance(context).getBoolean("qtpaylogin", false);
        return login;
    }

    public void setLogin(boolean login) {
        PreferenceUtil.getInstance(context).saveBoolean("qtpaylogin", login);
        this.login = login;
    }

    public String getToken() {
        token = PreferenceUtil.getInstance(context).getString("qtpaytoken", "0000");

        return token;
    }

    public void setToken(String token) {
        PreferenceUtil.getInstance(context).saveString("qtpaytoken", token);
        this.token = token;
    }

    public String getPhone() {
        phone = PreferenceUtil.getInstance(context).getString("qtpayphone", "");

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        PreferenceUtil.getInstance(context).saveString("qtpayphone", phone);
    }

    public String getMobileNo() {
        mobileNo = PreferenceUtil.getInstance(context).getString("qtpaymobileno", "");
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        PreferenceUtil.getInstance(context).saveString("qtpaymobileno", mobileNo);

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
        certPid = PreferenceUtil.getInstance(context).getString("certPid", "");
        return certPid;
    }

    public void setCertPid(String certPid) {
        PreferenceUtil.getInstance(context).saveString("certPid", certPid);
        this.certPid = certPid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static String getVersionCode(Context mContext) {
        String versionName = "";
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
