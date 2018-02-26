package com.bank.quickpay.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bank.quickpay.QuickPayApplication;
import com.bank.quickpay.R;
import com.bank.quickpay.activity.MainActivity;
import com.bank.quickpay.activity.SplashActivity;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.mymsg.MyInfoActivity;
import com.bank.quickpay.activity.psw.ResetPasswordActivity;
import com.bank.quickpay.activity.regist.RegistActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.HttpUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PhoneinfoUtils;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.qtpay.qtjni.QtPayEncode;
import com.rey.material.widget.Button;
import com.ryx.ryxkeylib.listener.EditPwdListener;
import com.ryx.ryxkeylib.service.CustomKeyBoardService;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_resetPwd)
    TextView tvResetPwd;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.edt_accout)
    EditText edt_accout;
    @BindView(R.id.edt_paswd)
    EditText edt_paswd;
    @BindView(R.id.btn_pop_list)
    ImageView btn_pop_list;
    private LoginAccountListAdapter accountListAdapter;
    protected ArrayList<String> userList = new ArrayList<String>();//账号列表
    @BindView(R.id.lay_list)
    AutoLinearLayout lay_list;
    private boolean isShowList = false;
    @BindView(R.id.lv_account)
    ListView lv_account;
    String phoneNumber;
    String paswdStr,phoneMsg;
    protected boolean isTokenIntent=false;
    private long exitTime = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        initUserList();
        accountListAdapter = new LoginAccountListAdapter(this);
        accountListAdapter.setList(userList);
        lv_account.setAdapter(accountListAdapter);
        iniRyxKeyWord();
        isTokenIntent = getIntent().getBooleanExtra("tokenIntent", false);
    }

    @OnClick(R.id.btn_pop_list)
    public void popAccountList() {
        if (userList.size() <= 0) {
            return;
        }
        //如果用户没
        if (!isShowList) {
            isShowList = true;
            lay_list.setVisibility(View.VISIBLE);
            accountListAdapter.setList(userList);
            accountListAdapter.notifyDataSetChanged();
            btn_pop_list.setBackgroundResource(R.drawable.login_pull_up_img);
        } else {
            isShowList = false;
            lay_list.setVisibility(View.GONE);
            btn_pop_list.setBackgroundResource(R.drawable.login_drop_down_img);
        }

    }
    @OnItemClick(R.id.lv_account)
    public void setAccount(int position) {
        lay_list.setVisibility(View.GONE);
        String account = userList.get(position);
        edt_accout.setText(account);
        edt_accout.setSelection(account.length());
        btn_pop_list.setBackgroundResource(R.drawable.login_drop_down_img);
    }
    @OnClick({R.id.tv_resetPwd, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_resetPwd:
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                break;
//
//                finish();
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是token失效跳转到的登录界面，操作后退时会提示两次操作
        if (isTokenIntent && keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                LogUtil.showToast(LoginActivity.this, getResources()
                        .getString(R.string.press_again));
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                if (HttpUtil.checkNet(getApplicationContext())) {
                    qtpayApplication = new Param("application");
                    qtpayApplication.setValue("UserLoginExit.Req");
                    qtpayAttributeList.add(qtpayApplication);
                    httpsPost(false, true, "UserLoginExit", new XmlCallback() {
                        @Override
                        public void onTradeSuccess(QuickPayResult payResult) {
                            doExit();
                            QuickPayApplication.getInstance().exit();
                        }
                    });
                } else {
                    doExit();
                    QuickPayApplication.getInstance().exit();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public void jumpPage() {
        if (isTokenIntent) {
            //如果是token失效跳转到的登录，则跳转到瑞刷主页面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            setResult(AppConfig.LOGINACTFINISH, intent);
        }
        LogUtil.showLog("isTokenIntent==" + isTokenIntent);
        finish();
    }

    @OnClick(R.id.login_btn)
    public void setBtnLogin() {
        if (lay_list.getVisibility() == View.VISIBLE) {
            lay_list.setVisibility(View.GONE);
        }
        if(postCheck()){
            disabledTimerView(loginBtn);
            doLogin();
//            saveAccount(phoneNumber);
        }

    }

    private void doLogin() {
        qtpayApplication.setValue("UserLogin.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("password", QtPayEncode
                .encryptUserPwd(paswdStr, phoneNumber, false)));
        qtpayParameterList.add(new Param("verifiCode",""));
        try {
            qtpayParameterList.add(new Param("phoneModel", android.os.Build.MODEL.trim().replace(" ","")));
        }catch (Exception e){
            qtpayParameterList.add(new Param("phoneModel",""));
        }
        try {
            phoneMsg = DataUtil.encode(getPhoneInfo().toString().trim().replace(" ", ""));
            qtpayParameterList
                    .add(new Param("phoneMsg", phoneMsg));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            qtpayParameterList
                    .add(new Param("phoneMsg", ""));
        }
        QuickPayAppData.getInstance(LoginActivity.this).setPhone(phoneNumber);
        QuickPayAppData.getInstance(LoginActivity.this).setMobileNo(phoneNumber);

        httpsPost("UserLoginTag", new XmlCallback() {
            @Override
            public void onLoginAnomaly() {

            }
            @Override
            public void onTradeSuccess(QuickPayResult qtpayResult) {
                    LogUtil.showToast(LoginActivity.this, "登录成功！");
                saveUserInfo(phoneNumber,qtpayResult);
            }

            @Override
            public void onOtherState(String code,String msg) {
//                if("6543".equals(code)){
//                    //需要验证码
//                    startToLoginVerificationCode();
//                }else{
//                    RyxAppdata.getInstance(LoginActivity.this).resetCurrentBranchConfig();
//                }
            }

            @Override
            public void onTradeFailed() {
                LogUtil.showToast(LoginActivity.this, "登录失败！");
            }
        });

    }

    //保存用户信息
    protected void saveUserInfo(String account, QuickPayResult qtpayResult) {
        saveAccount(account);
        saveAccount();
        //是否打开商户认证功能1打开，0不打开
//        try {
//            RyxAppdata.getInstance(LoginBaseAct.this).setbeanStatusDesc(qtpayResult.getBean_status_desc());
//            RyxAppdata.getInstance(LoginBaseAct.this).setkjzfTouchPayTag(qtpayResult.getKjzf_touch_pay());
//            RyxAppdata.getInstance(LoginBaseAct.this).setIsOpenMerchantFlag(Integer.parseInt(qtpayResult.getMerchant_status()));
//        } catch (Exception e) {
//
//        }
        String verifiedSwitch = qtpayResult.getVerifiedSwitch();
       String amoubtLow= qtpayResult.getAmountLow();
       String fixedStr=qtpayResult.getFeeFixed();
       String feeRate= qtpayResult.getFeeRate();
       if(!TextUtils.isEmpty(amoubtLow)){
           PreferenceUtil.getInstance(LoginActivity.this).saveString("amountLow", amoubtLow);
       }
       if(!TextUtils.isEmpty(fixedStr)){
           PreferenceUtil.getInstance(LoginActivity.this).saveString("feeFixed", fixedStr);
       }
       if(!TextUtils.isEmpty(feeRate)){
           PreferenceUtil.getInstance(LoginActivity.this).saveString("feeRate", feeRate);
           PreferenceUtil.getInstance(LoginActivity.this).saveString("fee_rate",feeRate+"%"+(TextUtils.isEmpty(fixedStr)?"":"+"+fixedStr));
       }




        QuickPayAppData.getInstance(LoginActivity.this).setToken(qtpayResult.getToken());
        QuickPayAppData.getInstance(LoginActivity.this).setMobileNo(
                qtpayResult.getMobileNo());
        QuickPayAppData.getInstance(LoginActivity.this)
                .setPhone(qtpayResult.getMobileNo());
        QuickPayAppData.getInstance(LoginActivity.this).setCustomerName(
                "" + qtpayResult.getUserName());
        QuickPayAppData.getInstance(LoginActivity.this).setRealName(
                "" + qtpayResult.getRealName());
        QuickPayAppData.getInstance(LoginActivity.this).setLogin(true);

        QuickPayAppData.getInstance(LoginActivity.this).setAuthenFlag(
                qtpayResult.getAuthenFlag());
        QuickPayAppData.getInstance(LoginActivity.this).setCustomerId(
                qtpayResult.getCustomerId());
        QuickPayAppData.getInstance(LoginActivity.this).setCertPid(
                qtpayResult.getCertPid());
        QuickPayAppData.getInstance(LoginActivity.this).setCertType(
                qtpayResult.getCertType());
        QuickPayAppData.getInstance(LoginActivity.this).setUserType(
                qtpayResult.getUserType());
        QuickPayAppData.getInstance(LoginActivity.this).setEmail(
                "" + qtpayResult.getEmail());
        QuickPayAppData.getInstance(LoginActivity.this).setTransLogNo(
                qtpayResult.getTransLogNo());
        QuickPayAppData.getInstance(LoginActivity.this).setTagDesc(qtpayResult.getTagDesc());
        finishSelf();
//        String data = qtpayResult.getData();
//        try {
//            JSONObject noticeObj = new JSONObject(data);
//            String rsfee = noticeObj.getString("rsfee");
//            String rsRate = noticeObj.getString("rsRate");
//            String productId = noticeObj.getString("productId");
//            String merchantId = noticeObj.getString("merchantId");
//        } catch (Exception e) {
//            e.getLocalizedMessage();
//        }
    }


    public JSONObject getPhoneInfo() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        JSONObject jsObject = new JSONObject();
        try {
            // 设备参数
            jsObject.put("Host", Build.HOST);
            // 获取手机唯一标识，GSM手机为IMEI码，CDMA手机为MEID码。
            jsObject.put("IMEI_or_MEID", PhoneinfoUtils.getDeviceId(LoginActivity.this));
            // UUID
            jsObject.put("UUID", PhoneinfoUtils.getUUid(LoginActivity.this));
            // MAC地址
            jsObject.put("MacAddress", PhoneinfoUtils.getMacAddress(LoginActivity.this));
            jsObject.put("AndroidId", PhoneinfoUtils.getAndroidId(LoginActivity.this));
            // android系统定制商
            jsObject.put("BRAND", Build.BRAND);
            // 系统版本
            jsObject.put("os_version", Build.VERSION.RELEASE);
            jsObject.put("TotalMemory",
                    PhoneinfoUtils.getTotalMemory(LoginActivity.this));
            // 版本
            jsObject.put("MODEL", Build.MODEL);
            jsObject.put("bankProvinceId",baseprovinceid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.showLog("phoneMsg=="+jsObject.toString());
        return jsObject;
    }

    private boolean postCheck(){
        phoneNumber=edt_accout.getText().toString();
        paswdStr=edt_paswd.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            LogUtil.showToast(LoginActivity.this,"请正确输入账号!");
            return false;
        }
        if(TextUtils.isEmpty(paswdStr)){
            LogUtil.showToast(LoginActivity.this,"请正确输入密码!");
            return false;
        }
        return true;
    }

    public void saveAccount() {
        ObjectOutputStream out = null;
        try {
            FileOutputStream os = openFileOutput("account.obj",
                    MODE_PRIVATE);
            out = new ObjectOutputStream(os);
            out.writeObject(userList);
        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //获取登录账号
    private void initUserList() {
        ObjectInputStream in = null;
        try {
            InputStream is = openFileInput("account.obj");
            in = new ObjectInputStream(is);
            if (in != null) {
                userList = (ArrayList<String>) in.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtil.showLog("userList", userList + "---");
        if (userList.size() > 0) {
            String accountstr = userList.get(userList.size() - 1);
            edt_accout.setText(accountstr);
            edt_accout.setSelection(accountstr.length());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edt_paswd.getWindowToken(), 0); //强制隐藏键盘
            btn_pop_list.setVisibility(View.VISIBLE);
            btn_pop_list.setBackgroundResource(R.drawable.login_drop_down_img);
        } else {
            btn_pop_list.setVisibility(View.GONE);
            edt_accout.requestFocus();//账号编辑框获取焦点 弹出软键盘
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) edt_accout
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(edt_accout, InputMethodManager.SHOW_FORCED);
                }
            }, 500);
        }
    }

    public class LoginAccountListAdapter extends BaseAdapter {
        private ArrayList list = new ArrayList();
        private ViewHolder viewHolder = null;
        Context context;

        public LoginAccountListAdapter(Context context) {
            this.context = context;
        }

        public void setList(ArrayList list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adapter_login_account_list_item, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_account);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img_delete);
            viewHolder.tv.setText((String) list.get(position));
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAccount(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView tv;
        }
    }
    //保存账号
    protected void saveAccount(String account) {
        if (!TextUtils.isEmpty(account)) {
            userList.remove(account);
            userList.add(account);
            if (userList.size() > 5) {
                userList.remove(0);
            }
        }
    }

    public void removeAccount(int position) {
        userList.remove(position);
        accountListAdapter.notifyDataSetChanged();
        if (userList.size() <= 0) {
            isShowList = false;
            lay_list.setVisibility(View.GONE);
            btn_pop_list.setBackgroundResource(R.drawable.login_drop_down_img);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveAccount();
    }

    private void iniRyxKeyWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(LoginActivity.this, true, edt_paswd, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                edt_paswd.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                edt_paswd.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
                setBtnLogin();
            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }

    /**
     * 登录完成后
     */
    private void finishSelf(){
        jumpPage();
    }
}

