package com.bank.quickpay.activity.regist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.qtpay.qtjni.QtPayEncode;
import com.rey.material.widget.Button;
import com.ryx.ryxkeylib.listener.EditPwdListener;
import com.ryx.ryxkeylib.service.CustomKeyBoardService;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegistActivity extends BaseActivity {


    @BindView(R.id.edt_accout)
    EditText edtAccout;
    @BindView(R.id.edt_paswd)
    EditText edtPaswd;
    @BindView(R.id.edt_msgcode)
    EditText edtmsgcode;
    @BindView(R.id.invitecode_et)
    EditText invitecode_et;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.tv_sendmsgcode)
    TextView mAgainSendMac;
    private Timer mTimer = new Timer();
    private String account,paswd,msgcode,invitecode;
    private Param qtpayAppType;
    private Param qtpayOrderId;
    private Param qtpayNewPassword;
    private Param qtpayMobileMac;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }
@OnClick(R.id.reghelp_layout)
public void reghelpLayout(){
        toHtmlMessageAct(AppConfig.REGISTRATIONAGREEMENT,"注册服务协议");
}
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("注册",true,false);
        iniRyxKeyWord();
        initQtPatParams();
    }
    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        qtpayApplication = new Param("application");
        qtpayOrderId = new Param("orderId", "");
        qtpayNewPassword = new Param("newPassword");
        qtpayMobileMac = new Param("mobileMac");// 短信验证码
    }
    @OnClick(R.id.tv_sendmsgcode)
    public void tv_sendVerificationcodeClick(View view){
         disabledTimerView(view);
        account= edtAccout.getText().toString();
        if(TextUtils.isEmpty(account)||!DataUtil.isMobileNO(account)){
            LogUtil.showToast(RegistActivity.this,"请正确输入手机号!");
            return ;
        }
        invitecode= invitecode_et.getText().toString();
        if(TextUtils.isEmpty(invitecode)){
            LogUtil.showToast(RegistActivity.this,"请输入邀请码!");
            return ;
        }
        mTimer = null;
        mTimer = new Timer();
        //重新建立网络请求
        QuickPayAppData.getInstance(RegistActivity.this).setPhone(
                account);
        QuickPayAppData.getInstance(RegistActivity.this).setMobileNo(
                account);
        qtpayApplication.setValue("GetMobileMac.Req");
        qtpayAppType = new Param("appType", "UserRegister");
        qtpayOrderId = new Param("orderId", "");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayAppType);
        qtpayParameterList.add(qtpayOrderId);
        httpsPost("getMobileMac", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                if ("GetMobileMac.Req".equals(qtpayApplication.getValue())) {
                    LogUtil.showToast(RegistActivity.this,
                            getResources().getString(R.string.sms_has_been_issued_please_note_that_check));
                }
            }

        });
        startCountdown(); // 开始倒计时
    }

    @OnClick(R.id.register_btn)
    public void register_btnClick(){
        if(checkInput()){
            //重新建立网络请求
            QuickPayAppData.getInstance(RegistActivity.this).setPhone(
                    account);
            QuickPayAppData.getInstance(RegistActivity.this).setMobileNo(
                    account);
            /**
             * 注册
             */
                qtpayApplication.setValue("UserRegister.Req");
                qtpayAttributeList.add(qtpayApplication);
                qtpayPhone.setValue(account);
                qtpayMobileNO.setValue(account);
                qtpayMobileMac.setValue(msgcode);// 短信验证码

                Param qtpayUserName = new Param("userName", account);
                Param qtpayPassword = new Param("password", QtPayEncode.encryptUserPwd(paswd, account,
                        false));
                Param codeStr = new Param("invitationCode", invitecode);
                qtpayParameterList.add(codeStr);

                Param qtpayReferrerMobileNo = new Param("referrerMobileNo", "");

                qtpayParameterList.add(qtpayUserName);
                qtpayParameterList.add(qtpayPassword);
                qtpayParameterList.add(qtpayReferrerMobileNo);
                qtpayParameterList.add(qtpayMobileMac);

                httpsPost("doUserRegister", new XmlCallback() {
                    @Override
                    public void onTradeSuccess(QuickPayResult payResult) {
                        LogUtil.showToast(RegistActivity.this,"恭喜您注册成功");
                        finish();
                    }
                });
            }
    }
    private boolean checkInput(){
        account= edtAccout.getText().toString();
        if(TextUtils.isEmpty(account)||!DataUtil.isMobileNO(account)){
            LogUtil.showToast(this, "请正确输入手机号!");
            return false;
        }
        paswd= edtPaswd.getText().toString();
        String flag = checkPassword(paswd);
        if ("0".equals(flag) || "1".equals(flag)) {
            LogUtil.showToast(this, "您的密码太过于简单,请使用复杂密码");
            return false;
        }
        msgcode= edtmsgcode.getText().toString();
        if(TextUtils.isEmpty(msgcode)){
            LogUtil.showToast(this, "验证码不能为空!");
            return false;
        }
        invitecode= invitecode_et.getText().toString();
        if(TextUtils.isEmpty(invitecode)){
            LogUtil.showToast(RegistActivity.this,"请输入邀请码!");
            return false;
        }
        return true;
    }
    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what > 0) {
                mAgainSendMac.setTextColor(getResources().getColor(R.color.text_a));
                mAgainSendMac.setText(getResources().getString(R.string.resend)
                        + "(" + msg.what + ")");
                mAgainSendMac.setClickable(false);
            } else {
                mTimer.cancel();
                mAgainSendMac.setText(getResources().getString(
                        R.string.resend_verification_code));
                mAgainSendMac.setClickable(true);
                mAgainSendMac.setTextColor(Color.parseColor("#1db7f0"));
            }
        }
    };
    /**
     * 开始倒计时60秒
     */

    public void startCountdown() {
        TimerTask task = new TimerTask() {
            int secondsRremaining = 60;

            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = secondsRremaining--;
                timeHandler.sendMessage(msg);
            }
        };
        mTimer.schedule(task, 1000, 1000);
    }
    private void iniRyxKeyWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(RegistActivity.this, true, edtPaswd, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                edtPaswd.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                edtPaswd.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
//                onNextClick();
            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }
    /**
     * 采用正则表达式检查密码
     */
    public String checkPassword(String passwordStr) {
        // 输入单数字或英文或符号 密码强度显示 弱
        // 输入数字、英文、符号中的任意2种 密码强度显示 中
        // 输入数字、英文、符号 三种 密码强度显示 强
        final String str1 = "[0-9]{1,20}$"; // 不超过20位的数字组合
        final String str2 = "^[a-zA-Z]{1,20}$"; // 由字母不超过20位
        final String str3 = "^[0-9|a-z|A-Z]{1,20}$"; // 由字母、数字组成，不超过20位
        final String str4 = "^[0-9|a-z|A-Z|[^0-9|^a-z|^A-Z]]{1,20}$"; // 由字母、数字、符号
        // 三种组成，不超过20位

        if (passwordStr == null || passwordStr.length() == 0) {
            return "0";
        }
        if (passwordStr.matches(str1) || passwordStr.matches(str2)) {
            return "1";
        }
        if (passwordStr.matches(str3)) {
            return "2";
        }
        if (passwordStr.matches(str4)) {
            return "3";
        }
        return "3";
    }
}
