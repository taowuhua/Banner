package com.bank.quickpay.activity.psw;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.qtpay.qtjni.QtPayEncode;
import com.rey.material.widget.Button;
import com.ryx.ryxkeylib.listener.EditPwdListener;
import com.ryx.ryxkeylib.service.CustomKeyBoardService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码第二步设置密码
 */
public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.et_password_again)
    EditText mSetAgainPwd;
    @BindView(R.id.btn_setpwd_done)
    Button mBtnSetDone;
    @BindView(R.id.iv_show_pwd_status)
    ImageView mShowPwdStatusImgView;
    private String mPhoneNumber;
    private String mUserName;
    private String mCertType;
    private String mCertPid;
    private String mMobileMac;
    private Param qtpayOrderId;
    private Param qtpayRealName;
    private Param qtpayNewPassword;
    private Param qtpayCertType;
    private Param qtpayCertPid;
    private Param qtpayMobileMac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("设置密码",true,false);
        mPhoneNumber = getIntent().getStringExtra("phone");
        mUserName = getIntent().getStringExtra("realName");
        mCertType = getIntent().getStringExtra("certType");
        mCertPid = getIntent().getStringExtra("certPid");
        mMobileMac = getIntent().getStringExtra("mobileMac");
        initQtPatParams();
        iniRyxKeyWord();
    }
    private void iniRyxKeyWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(SetPasswordActivity.this, true, mSetAgainPwd, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                mSetAgainPwd.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                mSetAgainPwd.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
                setDoneClick();
            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }

    @OnClick(R.id.iv_show_pwd_status)
    public void setPwdStatus() {
        if (mSetAgainPwd.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            //当前是密文切换成明文
            mSetAgainPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mShowPwdStatusImgView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_user_eye_close));
        } else {
            mSetAgainPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mShowPwdStatusImgView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_user_eye_open));
        }
        mSetAgainPwd.setSelection(mSetAgainPwd.getText().length());
    }

    @OnClick(R.id.btn_setpwd_done)
    public void setDoneClick() {
        String pwdString = mSetAgainPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdString)) {
            LogUtil.showToast(this,"请输入密码");
            return;
        }
        if (pwdString.length() < 6) {
            LogUtil.showToast(this,"密码至少6位");
            return;
        }
        String flag = checkPassword(pwdString);
        if ("0".equals(flag) || "1".equals(flag)) {
            LogUtil.showToast(this,"您的密码太过于简单,请使用复杂密码");
            return;
        }
        doResetPassWord(pwdString);
    }
    private void doResetPassWord(String password) {
        qtpayApplication.setValue("RetrievePassword.Req");
        qtpayAttributeList.add(qtpayApplication);
        QuickPayAppData.getInstance(SetPasswordActivity.this).setPhone(mPhoneNumber);
        QuickPayAppData.getInstance(SetPasswordActivity.this).setMobileNo(mPhoneNumber);
        qtpayRealName.setValue(mUserName);
        qtpayNewPassword.setValue(QtPayEncode.encryptUserPwd(password, mPhoneNumber, false));// 新密码
        // 加密后的密码，使用前置平台公钥(UKEY1)加密(HEX)
        qtpayCertType.setValue(mCertType);// 证件类型
        qtpayCertPid.setValue(mCertPid);// 证件号码
        qtpayMobileMac.setValue(mMobileMac);// 短信验证码
        qtpayParameterList.add(qtpayRealName);
        qtpayParameterList.add(qtpayNewPassword);
        qtpayParameterList.add(qtpayCertType);
        qtpayParameterList.add(qtpayCertPid);
        qtpayParameterList.add(qtpayMobileMac);

        httpsPost("doResetPwd", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                if ("RetrievePassword.Req".equals(qtpayApplication.getValue())) {
                    LogUtil.showToast(SetPasswordActivity.this,
                            getResources().getString(R.string.password_has_been_set_successfully));
                    finish();
                }
            }
        });
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
        qtpayApplication = new Param("application");
        qtpayOrderId = new Param("orderId", "");
        qtpayRealName = new Param("realName");
        qtpayNewPassword = new Param("newPassword");
        // 证件类型
        qtpayCertType = new Param("certType");
        // 证件号码
        qtpayCertPid = new Param("certPid");
        // 短信验证码
        qtpayMobileMac = new Param("mobileMac");
    }
}
