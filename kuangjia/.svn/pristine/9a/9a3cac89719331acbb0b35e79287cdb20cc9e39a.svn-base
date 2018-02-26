package com.bank.quickpay.activity.base;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bank.quickpay.BuildConfig;
import com.bank.quickpay.R;
import com.bank.quickpay.activity.helping.HtmlMessageActivity;
import com.bank.quickpay.activity.login.LoginActivity;
import com.bank.quickpay.activity.mymsg.CardBindActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.http.XmlParse;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.interfaces.IPermission;
import com.bank.quickpay.utils.CryptoUtils;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.HttpUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.bank.quickpay.utils.PhoneinfoUtils;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.widget.ProgressLoadDialog;
import com.bank.quickpay.widget.ProgressLoadDialogBuilder;
import com.ryx.payment.ryxhttp.callback.StringCallback;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.sobot.chat.SobotApi;
import com.umeng.analytics.MobclickAgent;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.bank.quickpay.config.AppConfig.TOLOGINACT;

/**
 * 快捷分销APP父类,所有项目activity必须继承此Act
 */
public abstract class BaseActivity extends AppCompatActivity implements AMapLocationListener {
    public String baseprovinceid = "", basecityid = "";
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public String baselongitude="", baselatitude="";
    protected Param qtpayApplication;
    protected Param qtpayMobileNO;
    protected Param qtpayTransDate;
    protected Param qtpayTransTime;
    protected Param qtpayTransLogNo;
    protected Param qtpayToken;

    protected Param qtpayblongitude, qtpayblatitude;
    protected Param qtpayCustomerId;
    protected Param qtpayPhone;
    protected Param qtpaySign;
    protected ArrayList<Param> qtpayAttributeList; // qtpay的属性参数列表
    protected ArrayList<Param> qtpayParameterList; // qtpay 的下级条目参数列表
    public boolean isNeedThread = true;
    private ProgressLoadDialogBuilder loading;
    public ProgressDialog progressDialog;

    /**
     * 子类请勿实现此方法,initViews为初始方法
     * @param savedInstanceState
     */
    @Deprecated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        LogUtil.setLogdebug(BuildConfig.DEBUG);
        setContentView(getLayoutId());
        ButterKnife.bind(BaseActivity.this);
        initViews(savedInstanceState);
        locationPermissionCheck();
        LogUtil.showLog("CurrentActivity=="+this);
    }
    /**
     * 设置当前页面的标题
     *
     * @param title           标题
     * @param leftRightisShow 左侧右侧是否显示,
     * @author xucc
     */
    public void setTitleLayout(String title, boolean... leftRightisShow) {
        try {
            RelativeLayout quickpaytitlelyout=(RelativeLayout) findViewById(R.id.quickpaytitlelyout);
            quickpaytitlelyout.setBackgroundResource(R.color.colorPrimary);
            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            tv_title.setText(title);
            ImageView leftImageView = (ImageView) findViewById(R.id.tileleftImg);
            ImageView rightImageView = (ImageView) findViewById(R.id.tilerightImg);
            if (leftRightisShow.length > 0) {
                //第一个代表左侧返回图标
                boolean leftIshow = leftRightisShow[0];
                if (leftIshow) {
                    leftImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            backUpImgOnclickListen();
                        }
                    });
                    leftImageView.setVisibility(View.VISIBLE);
                } else {
                    leftImageView.setVisibility(View.INVISIBLE);
                }
                //第二个代表右侧帮助图标
                boolean rightIshow = leftRightisShow[leftRightisShow.length - 1];
                if (rightIshow) {
                    rightImageView.setVisibility(View.VISIBLE);
                } else {
                    rightImageView.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception e){

        }
    }
    /**
     * 获取右侧按钮View对象
     */
    public ImageView getRightImgView(){
        ImageView rightImageView = (ImageView) findViewById(R.id.tilerightImg);
        return rightImageView;
    }
    protected void backUpImgOnclickListen() {
        finish();
    }

    /**
     * 右侧图片点击事件
     * @param urlKey
     * @param title
     */
    protected void onRightImgViewToHtmlMsgAct(final String urlKey, final String title){
        ImageView rightImageView = (ImageView) findViewById(R.id.tilerightImg);
        rightImageView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                toHtmlMessageAct(urlKey,title);
            }
        });

    }

    /**
     * 去帮助说明页面
     * @param urlKey
     * @param title
     */
    protected void toHtmlMessageAct(String urlKey,String title){
    Intent intent=new Intent(BaseActivity.this, HtmlMessageActivity.class);
    intent.putExtra("urlkey",urlKey);
    intent.putExtra("title",title);
    startActivity(intent);
}
    /**
     * 6.0权限检查
     */
    private void locationPermissionCheck() {
        final String  waring = MessageFormat.format(getResources().getString(R.string.locationwaringmsg),getResources().getString( R.string.app_name));
        PermissionUtil.checkPermission(BaseActivity.this, new IPermission() {
            @Override
            public void permissionSuccess() {
                initQtPatParams();
                onPermissionCheckSuccess();
                startbaseLocation();
            }

            @Override
            public void permissionFail() {
                showMissingPermissionDialog(waring);
            }
        }, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                READ_EXTERNAL_STORAGE);
    }

    /**
     * 权限判断完毕
     */
    protected void onPermissionCheckSuccess() {
    }

    /**
     * 定位成功
     */
    protected void onLocationSuccess() {
    }

    // 显示缺失权限提示
    public void showMissingPermissionDialog( final String warning) {
        QuickSimpleConfirmDialog quickSimpleConfirmDialog=new QuickSimpleConfirmDialog(BaseActivity.this);
        quickSimpleConfirmDialog.show();
        quickSimpleConfirmDialog.setContent(warning);
        quickSimpleConfirmDialog.setCancelable(false);
        quickSimpleConfirmDialog.setCanceledOnTouchOutside(false);
        quickSimpleConfirmDialog.setOnClickListen(new ConFirmDialogListener() {
            @Override
            public void onPositiveActionClicked(QuickSimpleConfirmDialog quickSimpleConfirmDialog) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                quickSimpleConfirmDialog.dismiss();
            }

            @Override
            public void onNegativeActionClicked(QuickSimpleConfirmDialog quickSimpleConfirmDialog) {
                quickSimpleConfirmDialog.dismiss();
                setResult(AppConfig.CLOSE_ALL);
                finish();
            }
        });

    }

    public void startbaseLocation() {
        LogUtil.showLog("ryx","startbaseLocation=="+ DateUtil.getDateTime(new Date()));
        baseprovinceid = PreferenceUtil.getInstance(BaseActivity.this).getString("baseprovinceid", "");
        baselongitude=PreferenceUtil.getInstance(BaseActivity.this).getString("baselongitude", "");
        baselatitude=PreferenceUtil.getInstance(BaseActivity.this).getString("baselatitude", "");
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        //false为持续定位,true为单次定位
        locationOption.setOnceLocation(false);
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);

        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置发送定位请求的时间间隔,最小值为1000
        locationOption.setInterval(Long.valueOf(1000));

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null && location.getErrorCode() == 0) {
            onLocationSuccess();
            
            String logit=location.getLongitude()+"";
            if(!TextUtils.isEmpty(logit)&&!"null".equals(logit)){
                baselongitude=logit;
                PreferenceUtil.getInstance(BaseActivity.this).saveString("baselongitude", baselongitude);
            }
            String lati=location.getLatitude() +"";
            if(!TextUtils.isEmpty(lati)&&!"null".equals(lati)){
                baselatitude=lati;
                PreferenceUtil.getInstance(BaseActivity.this).saveString("baselatitude", baselatitude);
            }
            String adCode = location.getAdCode();
            if (!TextUtils.isEmpty(adCode) && !"null".equals(adCode)) {
                baseprovinceid = adCode;
                PreferenceUtil.getInstance(BaseActivity.this).saveString("baseprovinceid", baseprovinceid);
            }
            String country= location.getCountry();//国家信息
            if (!TextUtils.isEmpty(country)) {
                PreferenceUtil.getInstance(BaseActivity.this).saveString("country", country);
            }
//            String street=location.getStreet();//街道信息
//            String streetnumber=location.getStreetNum();//街道门牌号信息

            String address=   location.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            if (!TextUtils.isEmpty(address)) {
                PreferenceUtil.getInstance(BaseActivity.this).saveString("address", address);
            }
            String provice= location.getProvince();//省信息
            if (!TextUtils.isEmpty(provice)) {
                PreferenceUtil.getInstance(BaseActivity.this).saveString("provice", provice);
            }

            String city=location.getCity();//城市信息
            if (!TextUtils.isEmpty(city)) {
                PreferenceUtil.getInstance(BaseActivity.this).saveString("city", city);
            }
            String district=location.getDistrict();//城区信息
            if (!TextUtils.isEmpty(district)) {
                PreferenceUtil.getInstance(BaseActivity.this).saveString("district", district);
            }
            LogUtil.showLog("location=="+location.toString());
            LogUtil.showLog("adCode==" + adCode + ",baselongitude==" + baselongitude + ",baselatitude==" + baselatitude);
            stopLocation();
        } else {
            if (location == null) {
                LogUtil.showLog("定位错误location为空");
            } else {
                LogUtil.showLog("=======定位失败=====" + "错误码:" + location.getErrorCode() + "," + "错误信息:" + location.getErrorInfo() + "," + "错误描述:" + location.getLocationDetail());
            }
            stopLocation();
        }
    }



    /**
     * 销毁定位
     */
    private void stopLocation() {
        LogUtil.showLog("停止定位==============");
        if (locationClient != null && locationClient.isStarted()) {
            // 停止定位
            locationClient.stopLocation();
        }
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 需要连网的页面初始化网络请求参数，使用之前记得初始化 context
     */
    public void initQtPatParams() {
        if (AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().size() == 0) {
            // 在第一次开启程序的时候 初始化公用请求参数
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("appUser", AppConfig.APPUSER));
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("version", AppConfig.CLIENTVERSION));
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("osType", "android" + android.os.Build.VERSION.RELEASE));
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("mobileSerialNum", PhoneinfoUtils.getMobileSerialNum(getApplicationContext())));
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("userIP", PhoneinfoUtils.getPsdnIp()));
            AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("clientType", AppConfig.CLIENTTYPE));
        }
        qtpayApplication=new Param("application");
        qtpayAttributeList = new ArrayList<Param>();
        qtpayParameterList = new ArrayList<Param>();
        qtpayMobileNO = new Param("mobileNo");
        qtpayTransDate = new Param("transDate");
        qtpayTransTime = new Param("transTime");
        qtpayTransLogNo = new Param("transLogNo");
        qtpayToken = new Param("token");

        qtpayblongitude = new Param("longitude");
        qtpayblatitude = new Param("latitude");

        qtpayCustomerId = new Param("customerId");
        qtpayPhone = new Param("phone");
        qtpaySign = new Param("sign", AppConfig.API_SIGN_KEY);
        if (isNeedThread) {
            progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setMessage("loading");
        }
    }

    public void showLoading(String... message) {
        LogUtil.showLog("showLoading----",message+"----");
        try {
            if (null != loading) {
                loading.dismiss();
            } else {
                loading = new ProgressLoadDialog().getInstance(BaseActivity.this);
                loading.setCanceledOnTouchOutside(false);
            }
            if (message.length > 0) {
                loading.setMessage(message[0]);
            } else {
                loading.setMessage("努力加载中...");
            }
            //不可取消
            loading.setCancelable(false);
            loading.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 取消等待框
     */
    protected void cancleLoading() {
        try {
            if (null != loading) {
                loading.dismiss();
            }
        }catch (Exception e){

        }

    }

    /**
     * 请求前参数检查
     */
    private String postCheck() {
        AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList().add(new Param("appUser", AppConfig.APPUSER));
        qtpaySign = new Param("sign", AppConfig.API_SIGN_KEY);
        if ("UserRegister.Req".equals(qtpayApplication.getValue())) {
            qtpayToken.setValue("0001"); // 注册
        } else if ("GetMobileMac.Req".equals(qtpayApplication.getValue())) {
            qtpayToken.setValue("0002");// 验证码
        } else if (false == AppConfig.getInstance(BaseActivity.this).isLogin()) {
            qtpayToken.setValue("0000");
        } else {
            qtpayToken.setValue(AppConfig.getInstance(BaseActivity.this).getToken());
        }

        qtpayPhone.setValue(AppConfig.getInstance(BaseActivity.this).getPhone());

        if (!"UserInfoQuery.Req".equals(qtpayApplication.getValue())) {
            qtpayMobileNO.setValue(AppConfig.getInstance(BaseActivity.this).getMobileNo());
        }


        if (AppConfig.getInstance(BaseActivity.this).isLogin() == false) {
            qtpayCustomerId.setValue("0000");
        } else {
                qtpayCustomerId.setValue(AppConfig.getInstance(BaseActivity.this).getCustomerId());
        }
        String transDate = CryptoUtils.getInstance().getTransDate();
        String transTime = CryptoUtils.getInstance().getTransTime();
        qtpayTransDate.setValue(transDate);
        qtpayTransTime.setValue(transTime);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String transTime2 = sDateFormat.format(new Date());
        LogUtil.showLog("ryx", "mytransTime2=" + transTime2);
        LogUtil.showLog("ryx", "transDate=" + transDate);
        LogUtil.showLog("ryx", "transTime=" + transTime);
        LogUtil.showLog("ryx", "qtpayTransDate=" + qtpayTransDate.getValue());
        LogUtil.showLog("ryx", "qtpayTransTime=" + qtpayTransTime.getValue());
        qtpayAttributeList.addAll(AppConfig.getInstance(BaseActivity.this).getQtpayPublicAttributeList()); // 添加6个公用属性
        qtpayAttributeList.add(qtpayToken);
        qtpayAttributeList.add(qtpayCustomerId);
        qtpayAttributeList.add(qtpayPhone);
        qtpayblongitude.setValue(baselongitude);
        qtpayblatitude.setValue(baselatitude);

        qtpayTransLogNo.setValue(CryptoUtils.getInstance().getTransLogNo());
//        }
        if (qtpayParameterList == null) {
            qtpayParameterList = new ArrayList<Param>();
        }
        qtpayParameterList.add(qtpayblongitude);
        qtpayParameterList.add(qtpayblatitude);
        qtpayParameterList.add(qtpayMobileNO);
        qtpayParameterList.add(qtpayTransDate);
        qtpayParameterList.add(qtpayTransTime);
        qtpayParameterList.add(qtpayTransLogNo);
        LogUtil.showLog("qtpayTransLogNo=="+qtpayTransLogNo.getValue()+",qtpayApplication=="+ qtpayApplication.getValue());
        qtpayParameterList.add(qtpaySign);

        long curLog = PreferenceUtil.getInstance(BaseActivity.this).getLong("TransLogNO", 0);

        if (!TextUtils.isEmpty(qtpayTransLogNo.getValue())&& Long.parseLong(qtpayTransLogNo.getValue()) > curLog) {
            PreferenceUtil.getInstance(BaseActivity.this).saveLong("TransLogNO", Long.parseLong(qtpayTransLogNo.getValue()));
        }

        String xmlString = null;

        LogUtil.showLog(qtpayApplication.getValue());
        upRequestParams();
        if ("UserIdentityPicUpload2.Req".equals(qtpayApplication.getValue()) || "UserIdentityPicUpload3.Req".equals(qtpayApplication.getValue())
                || "SaveGood.Req".equals(qtpayApplication.getValue()) || "UpdateGood.Req".equals(qtpayApplication.getValue())) { //
            xmlString = XmlParse.creatRequestWithPic(qtpayAttributeList, qtpayParameterList);
        } else {
            xmlString = XmlParse.creatRequest(qtpayAttributeList, qtpayParameterList);
        }
        qtpayAttributeList.clear();
        qtpayParameterList.clear();


        return xmlString;
    }

    /**
     * 修改请求参数(用于一些接口需要修改在发起网络请求前修改请求中某些本地存储的数据使用)
     */
    public void upRequestParams() {

    }


    /**
     * 新网络请求方法
     *
     * @param callback
     */
    public void httpsPost(String tag, final XmlCallback callback, String... message) {
        httpsPost(true,true,tag,callback,message);
    }

    public void httpsPost(boolean blShowLoading, final boolean blHideLoading, String tag, final XmlCallback callback, String... message)
    {
        if (HttpUtil.checkNet(getApplicationContext())) {
            if(isNeedThread) {
                if(blShowLoading) {
                    showLoading(message);
                }
            }
            String xmlString = postCheck();
            LogUtil.showLog("httprequest", xmlString);
            LogUtil.showLog("qtpayApplication.getValue==+"+xmlString);
            final String value = qtpayApplication.getValue();
            HttpUtil.getInstance().httpsPost(tag, xmlString, new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    cancleLoading();
                    //// TODO: 16/4/22 系统异常情况
                    LogUtil.showLog("httpresult", "系统异常--------" + e.getLocalizedMessage());
                    LogUtil.showToast(BaseActivity.this, "访问服务端超时,请检查网络是否正常!!!");
                    callback.onTradeFailed();
                }


                @Override
                public void onResponse(String response) {
                    if(blHideLoading) {
                        cancleLoading();
                    }
                    String resultString = response;
                    LogUtil.showLog("httpresult", response);
                    List list = XmlParse.getXmlList(resultString, QuickPayResult.class, "QtPay");
                    QuickPayResult qtpayResult = (QuickPayResult) list.get(0);
                    LogUtil.showLog("qtpayApplication.getValue==+"+value+"+==="+qtpayResult.getApplication());
                    if (!TextUtils.isEmpty(value)&&qtpayResult!=null) {

                        //返回数据分析
                        //如果是登录返回,则进行TransLogNo获取并保存
                        if(AppConfig.QTNET_TRANSLOGFAIL.equals(qtpayResult.getRespCode())){
                            if("UserLogin.Req".equals(value)){
                                //登录返回的transLog是最大值
                                if (qtpayResult != null && !TextUtils.isEmpty(qtpayResult.getTransLogNo())) {
                                    PreferenceUtil.getInstance(BaseActivity.this).saveLong("TransLogNO", Long.parseLong(qtpayResult.getTransLogNo()) + 1);
                                }
                            }else{
                                //其他接口返回的keyLog是最大值
                                if (qtpayResult != null && !TextUtils.isEmpty(qtpayResult.getKeyLogNo())) {
                                    PreferenceUtil.getInstance(BaseActivity.this).saveLong("TransLogNO", Long.parseLong(qtpayResult.getKeyLogNo()) + 1);
                                }
                            }
                            long curLog1 = PreferenceUtil.getInstance(BaseActivity.this).getLong("TransLogNO", 0);
                            CryptoUtils.getInstance().setTransLogNo(curLog1);
                        }
//                    }
                        // 处理交易成功
                        if (AppConfig.QTNET_SUCCESS2.equals( qtpayResult.getRespCode())|| qtpayResult.getRespCode().equals(AppConfig.QTNET_SUCCESS)) {
                            callback.onTradeSuccess(qtpayResult);
                        } else if (AppConfig.QTNET_OUTLOGIN2.equals(qtpayResult.getRespCode()) || AppConfig.QTNET_OUTLOGIN1.equals(qtpayResult.getRespCode())) {
                            // 处理登录异常
                            if ("UserLogin.Req".equals(value)) {
                                LogUtil.showToast(BaseActivity.this, "请重试!");
                            } else {
                                LogUtil.showToast(BaseActivity.this, "为保证账户安全，请你重新登录！");
                                subActivityRelease(new ReleaseResultListen() {
                                    @Override
                                    public void releaseResultok() {
                                        toAgainLogin(BaseActivity.this,TOLOGINACT,true);
                                    }
                                });
                            }
                            doExit();
                            LogUtil.showLog("onLoginAnomaly");
                            cancleLoading();
                            callback.onLoginAnomaly();
                        } else {
                            if(callback.isToastOtherMsg()){
                                LogUtil.showToast(BaseActivity.this, qtpayResult.getRespDesc());
                            }
                            cancleLoading();
                            callback.onOtherState();
                            callback.onOtherState(qtpayResult.getRespCode(),qtpayResult.getRespDesc());
                        }
                    }else{
                        cancleLoading();
                        LogUtil.showToast(BaseActivity.this, "请重试!!");
                        callback.onTradeFailed();
                    }
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.showToast(BaseActivity.this, "请检查网络！");
                }
            });
            callback.onTradeFailed();
        }
    }

    /**
     * 去重新登录
     */
    public void toAgainLogin(Context context, int requestCode, boolean... iscleartask){
        Intent intent = new Intent(context, LoginActivity.class);
        if(iscleartask.length>0&&iscleartask[0]){
            //清空activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加token失效的标示
            intent.putExtra("tokenIntent", true);
        }
        startActivityForResult(intent,requestCode);
    }

    public void doExit() {
        AppConfig.getInstance(this).setLogin(false);
        AppConfig.getInstance(this).setRealName("");
        AppConfig.getInstance(this).setMobileNo("");
        AppConfig.getInstance(this).setPhone("");
        AppConfig.getInstance(this).setCustomerId("");
        AppConfig.getInstance(this).setAuthenFlag(0);
        AppConfig.getInstance(this).setCustomerName("");
        AppConfig.getInstance(this).setToken("");
        SobotApi.exitSobotChat(BaseActivity.this);
    }


    /**
     *当用户Session失效后，跳转登录页前进行子类资源释放
     * (目前仅Swiper类调用)
     */
    protected void subActivityRelease(ReleaseResultListen releaseResultListen){
        releaseResultListen.releaseResultok();
    }

    public interface  ReleaseResultListen{
        /**
         * 释放资源完毕后监听
         */
        void releaseResultok();
    }
    /**
     * 获取资源ID信息
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化布局文件(定位信息之前)
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);


    public interface  CompleteResultListen{
        void compleResultok();
    }

    /**
     * 网页webview加载失败后默认页显示（调用当前方法页面布局一定要有<include layout="@layout/ryxpaynointernetlyout"></include>）
     * @param context Activty
     * @param webView webView对象
     * @param completeResultListen  点击刷新按钮后监听事件
     */
    protected void htmlNetworkFail(Activity context, final WebView webView, final CompleteResultListen completeResultListen){
        Button button= (Button)context.findViewById(R.id.button);
        final LinearLayout linearLayout= (LinearLayout)context.findViewById(R.id.ll_noInternet);
        webView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        button.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                LogUtil.showLog("htmlNetworkFail==刷新");
                webView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                completeResultListen.compleResultok();
            }
        });
    }


    /**
     * 设置View2秒内不能重复点击（经测试material布局因为有效果存在，此方法无法控制material布局重复点击，
     * 用disabledTimerAnyView接口控制material布局重复点击）
     * @param v
     */
    public  void disabledTimerView(final View v) {
        if(v!=null){
            v.setClickable(false);
            v.setEnabled(false);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    v.setClickable(true);
                    v.setEnabled(true);
                }
            }, 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppConfig.CLOSE_ALL == resultCode) {
            setResult(AppConfig.CLOSE_ALL,data);
            finish();
        }
    }
}
