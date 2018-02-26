package com.bank.quickpay.activity.psw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.IDCardUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.rey.material.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码重置
 */
public class ResetPasswordActivity extends BaseActivity {


    @BindView(R.id.et_mac)
    EditText mMacEditText;
    @BindView(R.id.tv_again_mac)
    TextView mAgainMacTextView;
    @BindView(R.id.et_phonenumber)
    EditText mPhoneNumberEditText;
    @BindView(R.id.btn_reset_next)
    Button mResetNextBtn;
    @BindView(R.id.et_username)
    EditText mUserNameEdit;
    @BindView(R.id.et_identification_card)
    EditText mIdentificationCardEdit;

    @BindView(R.id.ll_phonenumber)
    LinearLayout mPhoneNumberLL;
    @BindView(R.id.ll_username)
    LinearLayout mUserNameLL;
    @BindView(R.id.ll_mac)
    LinearLayout mMacLL;

    TextView mBranchNametv;
    private Param qtpayAppType;
    private Param qtpayOrderId;
    private Timer mTimer = new Timer();
    private String numberString;
    private String mUserName="";
    private String mIdCard="";
    private String macString;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("找回密码",true,false);
        initQtPatParams();
    }
    @OnClick(R.id.tv_again_mac)
    public void sendMac() {
        if (checkInput()) {
            mTimer = null;
            mTimer = new Timer();
            String phoneNumber = mPhoneNumberEditText.getText().toString().trim();
            QuickPayAppData.getInstance(ResetPasswordActivity.this).setPhone(phoneNumber);
            QuickPayAppData.getInstance(ResetPasswordActivity.this).setMobileNo(phoneNumber);
            getMobileMac();
        }
    }

    private void getMobileMac() {
        qtpayApplication = new Param("application", "GetMobileMac.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayAppType);
        qtpayParameterList.add(qtpayOrderId);
        httpsPost("getMobileMac", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                LogUtil.showToast(ResetPasswordActivity.this,
                        getResources().getString(R.string.sms_has_been_issued_please_note_that_check));
                startCountdown();
            }
        });
    }
    private boolean checkInput() {
        numberString = mPhoneNumberEditText.getText().toString().trim();
        mUserName = mUserNameEdit.getText().toString().trim();
        mIdCard = mIdentificationCardEdit.getText().toString().trim();
        if (TextUtils.isEmpty(numberString)) {
            LogUtil.showToast(this,"请输入手机号码");
            return false;
        }
        if (numberString.length() != 11) {
            LogUtil.showToast(this,"手机号码为11位");
            return false;
        }
        if(!DataUtil.isMobileNO(numberString)){
            LogUtil.showToast(this,"请输入正确手机号");
            return false;
        }
        if(!TextUtils.isEmpty(mIdCard)){
            if (mIdCard.length() != 18 || !IDCardUtil.isIDCard(mIdCard)) {
                LogUtil.showToast(this,"请输入正确的身份证号码");
                return false;
            }
        }

        return true;
    }
    @OnClick(R.id.btn_reset_next)
    public void onViewClicked() {

        if (checkInput()) {
            if (checkMobileMac()) {
                Intent intent = new Intent(ResetPasswordActivity.this, SetPasswordActivity.class);
                intent.putExtra("phone", numberString);
                intent.putExtra("realName", mUserName);
                intent.putExtra("certType", "01");
                intent.putExtra("certPid", mIdCard.toLowerCase());
                intent.putExtra("mobileMac", macString);
                startActivity(intent);
                finish();
            }
        }
    }
    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                mAgainMacTextView.setTextColor(ContextCompat.getColor(ResetPasswordActivity.this, R.color.fourblack));
                mAgainMacTextView.setText(getResources().getString(R.string.resend) + "(" + msg.what + ")");
                mAgainMacTextView.setClickable(false);
            } else {
                mTimer.cancel();
                mAgainMacTextView.setText(getResources().getString(R.string.resend_verification_code));
                mAgainMacTextView.setClickable(true);
                mAgainMacTextView.setTextColor(ContextCompat.getColor(ResetPasswordActivity.this, R.color.btnbackg));
            }
        }
    };
    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        qtpayAppType = new Param("appType", "RetrievePassword");
        qtpayOrderId = new Param("orderId", "");
    }
    private boolean checkMobileMac() {
        macString = mMacEditText.getText().toString().trim();
        if (TextUtils.isEmpty(macString)) {
            LogUtil.showToast(this,"请输入验证码");
            return false;
        }
        if (macString.length() != 4) {
            LogUtil.showToast(this,"验证码为4位数字");
            return false;
        }
        return true;
    }
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
}
