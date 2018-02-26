package com.bank.quickpay.activity.psw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.LogUtil;
import com.qtpay.qtjni.QtPayEncode;
import com.rey.material.widget.Button;
import com.ryx.ryxkeylib.listener.EditPwdListener;
import com.ryx.ryxkeylib.service.CustomKeyBoardService;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/*
* 修改密码
* */
public class ChangePassWord extends BaseActivity {

    @BindView(R.id.et_old_password)
    EditText mOldPwd;
    @BindView(R.id.et_new_password)
    EditText mNewPwd;
    @BindView(R.id.iv_show_old_pwd_status)
    ImageView mShowOldPwdStatus;
    @BindView(R.id.iv_show_new_pwd_status)
    ImageView mShowNewPwdStatus;
    @BindView(R.id.et_mac)
    EditText mMacEditText;
    @BindView(R.id.tv_again_mac)
    TextView mMacAgainText;
    @BindView(R.id.btn_save_pwd)
    Button mSavePwdBtn;
    @BindView(R.id.ll_old_pwd_line)
    LinearLayout mOldPwdLL;
    @BindView(R.id.ll_new_pwd_line)
    LinearLayout mNewPwdLL;
    @BindView(R.id.ll_mac_line)
    LinearLayout mMacLineLL;


    private String macString;
    private Timer mTimer = new Timer();
    private Param qtpayAppType;
    private Param qtpayOrderId;
    private Param qtpayPassword;
    private String oldPwd;
    private String newPwd;
    private Param qtpayNewPassword;
    private Param qtpayCertPid;
    private Param qtpayMobileMac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pass_word;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("修改密码", true, false);
        initQtPatParams();
        iniRyxOldPwdWord();
        iniRyxNewPwdWord();
        allLineLostFocus();
    }

    public void allLineLostFocus() {
        mOldPwdLL.setBackgroundResource(R.color.login_edt_lostfocus);
        mNewPwdLL.setBackgroundResource(R.color.login_edt_lostfocus);
        mMacLineLL.setBackgroundResource(R.color.login_edt_lostfocus);
    }

    @OnFocusChange(R.id.et_mac)
    public void macFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            mMacLineLL.setBackgroundResource(R.color.light_brown);
        } else
        {
            mMacLineLL.setBackgroundResource(R.color.login_edt_lostfocus);
        }

    }

    private void iniRyxOldPwdWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(ChangePassWord.this, true, mOldPwd, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                mOldPwd.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                mOldPwd.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }

    private void iniRyxNewPwdWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(ChangePassWord.this, true, mNewPwd, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                mNewPwd.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                mNewPwd.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }

    /**
     * 采用正则表达式检查密码强度
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

    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        qtpayAppType = new Param("appType", "UserUpdatePwd");
        qtpayOrderId = new Param("orderId", "");
    }

    @OnClick(R.id.iv_show_old_pwd_status)
    public void showOldPwd() {
        if (mOldPwd.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            //当前是密文切换成明文
            mOldPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mShowOldPwdStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_user_eye_close));
        } else {
            mOldPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mShowOldPwdStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_user_eye_open));
        }
        mOldPwd.setSelection(mOldPwd.getText().length());
    }

    @OnClick(R.id.iv_show_new_pwd_status)
    public void showNewPwd() {
        if (mNewPwd.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            //当前是密文切换成明文
            mNewPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mShowNewPwdStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_user_eye_close));
        } else {
            mNewPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mShowNewPwdStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_user_eye_open));
        }
        mNewPwd.setSelection(mNewPwd.getText().length());
    }

    @OnClick(R.id.btn_save_pwd)
    public void savePwdBtn() {
        if (checkInput()) {
            if (checkMobileMac()) {
                doSavePwd();
            }
        }
    }

    /**
     * 保存密码
     */
    private void doSavePwd() {
        qtpayApplication = new Param("application", "UserUpdatePwd.Req");
        // 原密码
        qtpayPassword = new Param("password", QtPayEncode.encryptUserPwd(oldPwd, AppConfig
                .getInstance(ChangePassWord.this).getMobileNo(), AppConfig.DEBUG));
        // 新密码
        qtpayNewPassword = new Param("newPassword", QtPayEncode.encryptUserPwd(newPwd, AppConfig
                .getInstance(ChangePassWord.this).getMobileNo(), AppConfig.DEBUG));
        // 加密后的密码，使用前置平台公钥(UKEY1)加密(HEX)
        // 证件号码
        qtpayCertPid = new Param("certPid", AppConfig.getInstance(ChangePassWord.this)
                .getCertPid());
        // 短信验证码
        qtpayMobileMac = new Param("mobileMac", macString);
        qtpayAttributeList.clear();
        qtpayParameterList.clear();
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayPassword);
        qtpayParameterList.add(qtpayNewPassword);
        qtpayParameterList.add(qtpayCertPid);
        qtpayParameterList.add(qtpayMobileMac);
        httpsPost("doChangePwd", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                LogUtil.showToast(ChangePassWord.this,
                        getResources().getString(R.string.password_has_been_changed_successfully));

                AppConfig.getInstance(ChangePassWord.this).setLogin(false);
                AppConfig.getInstance(ChangePassWord.this).setRealName("");
                AppConfig.getInstance(ChangePassWord.this).setMobileNo("");
                AppConfig.getInstance(ChangePassWord.this).setPhone("");
                AppConfig.getInstance(ChangePassWord.this).setCustomerId("");
                AppConfig.getInstance(ChangePassWord.this).setAuthenFlag(0);
                AppConfig.getInstance(ChangePassWord.this).setCustomerName("");
                AppConfig.getInstance(ChangePassWord.this).setToken("");
                toAgainLogin(ChangePassWord.this, AppConfig.TOLOGINACT);
                finish();
            }

            @Override
            public void onLoginAnomaly() {

            }

            @Override
            public void onOtherState() {

            }

            @Override
            public void onTradeFailed() {

            }
        });
    }

    @OnClick(R.id.tv_again_mac)
    public void sendMacText() {
        if (checkInput()) {
            mTimer = null;
            mTimer = new Timer();
            getMobileMac();
        }
    }

    /**
     * 获得验证码
     */
    private void getMobileMac() {
        qtpayApplication = new Param("application", "GetMobileMac.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayAppType);
        qtpayParameterList.add(qtpayOrderId);
        httpsPost("getMobileMac", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                LogUtil.showToast(ChangePassWord.this,
                        getResources().getString(R.string.sms_has_been_issued_please_note_that_check));
                startCountdown();
            }

            @Override
            public void onLoginAnomaly() {

            }

            @Override
            public void onOtherState() {

            }

            @Override
            public void onTradeFailed() {

            }
        });
    }

    public boolean checkInput() {
        oldPwd = mOldPwd.getText().toString().trim();
        newPwd = mNewPwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            LogUtil.showToast(this, "请输入原密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd)) {
            LogUtil.showToast(this, "请输入新密码");
            return false;
        }
        if (oldPwd.length() < 6) {
            LogUtil.showToast(this, "密码至少6位");
            return false;
        }
        if (newPwd.length() < 6) {
            LogUtil.showToast(this, "密码至少6位");
            return false;
        }
        if(oldPwd.equals(newPwd)){
            LogUtil.showToast(this, "新旧密码不可以相同");
            return false;
        }
        String flag = checkPassword(newPwd);
        if ("0".equals(flag) || "1".equals(flag)) {
            LogUtil.showToast(this, "您的密码太过于简单,请使用复杂密码");
            return false;
        }
        return true;
    }

    private boolean checkMobileMac() {
        macString = mMacEditText.getText().toString().trim();
        if (TextUtils.isEmpty(macString)) {
            LogUtil.showToast(this, "请输入验证码");
            return false;
        }
        if (macString.length() != 4) {
            LogUtil.showToast(this, "验证码为4位数字");
            return false;
        }
        return true;
    }

    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                mMacAgainText.setTextColor(ContextCompat.getColor(ChangePassWord.this, R.color.text_a));
                mMacAgainText.setText(getResources().getString(R.string.resend) + "(" + msg.what + ")");
                mMacAgainText.setClickable(false);
            } else {
                mTimer.cancel();
                mMacAgainText.setText(getResources().getString(R.string.resend_verification_code));
                mMacAgainText.setClickable(true);
                mMacAgainText.setTextColor(ContextCompat.getColor(ChangePassWord.this, R.color.colorPrimary));
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
                Message msg = new Message();
                msg.what = secondsRremaining--;
                timeHandler.sendMessage(msg);
            }
        };
        mTimer.schedule(task, 1000, 1000);
    }

    @OnClick(R.id.tileleftImg)
    public void backBtn() {
        finish();
    }
}
