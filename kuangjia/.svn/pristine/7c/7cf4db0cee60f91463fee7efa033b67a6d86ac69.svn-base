package com.bank.quickpay.activity.transactiondetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.utils.IDCardUtil;
import com.bank.quickpay.utils.LogUtil;
import com.rey.material.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *黑名单信息添加
 */
public class BlackCheckMsgActivity extends BaseActivity {
    private String cardIdjiami,md5Value,localtime,localDate,locallogo,msgid;

    @BindView(R.id.black_check_nextbtn)
    Button black_check_nextbtn;
    @BindView(R.id.tv_idcardid)
    TextView tv_idcardid;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    @BindView(R.id.et_pname)
    EditText et_pname;

    @BindView(R.id.et_pid)
    EditText et_pid;

    @BindView(R.id.et_phoneno)
    EditText et_phoneno;
    @BindView(R.id.et_smscode)
    EditText et_smscode;

    Timer timer = new Timer();
    Handler timehandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what > 0) {
                tv_prompt.setTextColor(getResources().getColor(R.color.text_a));
                tv_prompt.setText(getResources().getString(R.string.resend)
                        + "(" + msg.what + ")");
                tv_prompt.setClickable(false);
            } else {
                timer.cancel();
                tv_prompt.setText(getResources().getString(
                        R.string.resend_verification_code));
                tv_prompt.setClickable(true);
                tv_prompt.setTextColor(getResources().getColor(R.color.secondblack));
            }
        };
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_check_msg;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initQtPatParams();
        setTitleLayout("补充资料",true,false);
        qtpayApplication = new Param("application");
        Bundle bundle	=getIntent().getExtras();
        cardIdjiami=bundle.getString("cardIdjiami","");
        md5Value=bundle.getString("md5Value","");
        localtime=bundle.getString("localtime","");
        localDate=bundle.getString("localDate","");
        locallogo=bundle.getString("locallogo","");
        msgid=bundle.getString("msgid","");
        tv_idcardid.setText(cardIdjiami);
    }

    @OnClick(R.id.black_check_nextbtn)
    public void toBlackPhoto(){
        if(checkInput()){
            final String smscode=	et_smscode.getText().toString();
            if(!TextUtils.isEmpty(smscode)&&smscode.length()==4){
                String phoneMobile=	AppConfig.getInstance(BlackCheckMsgActivity.this)
                        .getMobileNo();
                String phoneno=	et_phoneno.getText().toString();
                if(phoneno.equals(phoneMobile)){
                    QuickSimpleConfirmDialog ryxSimpleConfirmDialog = new QuickSimpleConfirmDialog(BlackCheckMsgActivity.this, new ConFirmDialogListener() {

                        @Override
                        public void onPositiveActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                            ryxSimpleConfirmDialog.dismiss();
                            checkSMSCodeReq(smscode);
                        }
                        @Override
                        public void onNegativeActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                            ryxSimpleConfirmDialog.dismiss();
                        }
                    });
                    ryxSimpleConfirmDialog.show();
                    ryxSimpleConfirmDialog.setContent("手机号为银行卡预留手机号，是否确认？");

                }else{
                    checkSMSCodeReq(smscode);
                }

            }else{
                LogUtil.showToast(BlackCheckMsgActivity.this, "请正确输入验证码!");
            }
        }




    }

    /**
     * 验证验证码是否正确
     * @param smscode
     */
    private void checkSMSCodeReq(String smscode){
        qtpayApplication.setValue("CheckSMSCode.Req");
        Param phoneParam=new Param("bankTel");
        String phoneNumber=et_phoneno.getText().toString();
        phoneParam.setValue(phoneNumber);
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(phoneParam);
        qtpayParameterList.add(new Param("smsCode", smscode));
        httpsPost("CheckSMSCodeTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                String smscode=	et_smscode.getText().toString();
                if(checkInput()&&!TextUtils.isEmpty(smscode)){
                    String pname=	et_pname.getText().toString();
                    String pid=	et_pid.getText().toString();
                    String phoneno=	et_phoneno.getText().toString();
                    //校验验证码成功
                    Intent intent = new Intent(BlackCheckMsgActivity.this,BlackPhotoMsgActivity.class);
                    Bundle extras=new Bundle();
                    extras.putString("pname", pname);
                    extras.putString("pid", pid.toLowerCase());
                    extras.putString("phoneno", phoneno);
                    extras.putString("smscode", smscode);
                    extras.putString("md5Value", md5Value);
                    extras.putString("localtime", localtime);
                    extras.putString("localDate", localDate);
                    extras.putString("locallogo", locallogo);
                    extras.putString("msgid", msgid);
                    intent.putExtras(extras);
                    startActivityForResult(intent, 0x003);
                }
            }
        });
    }

    @OnClick(R.id.tv_prompt)
    public void tv_promptClick(){
        if(checkInput()){
            et_smscode.setText("");
            sendAdvancedVipSMS();
            timer = null;
            timer = new Timer();
            startCountdown();   // 开始倒计时
        }
    }
    /**
     * 发送验证码
     */
    private void sendAdvancedVipSMS() {

        qtpayApplication.setValue("SendAdvancedVipSMS.Req");
        Param phoneParam=new Param("bankTel");
        String phoneNumber=et_phoneno.getText().toString();
        phoneParam.setValue(phoneNumber);
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(phoneParam);
        httpsPost("SendAdvancedVipSMSTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                //获取验证码成功
                Toast.makeText(BlackCheckMsgActivity.this, getResources().getString(R.string.sms_has_been_issued_please_note_that_check), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 内容填写情况
     * @return
     */
    private boolean checkInput(){
        String pname=	et_pname.getText().toString();
        String pid=	et_pid.getText().toString();
        String phoneno=	et_phoneno.getText().toString();

        if(TextUtils.isEmpty(pname)){
            LogUtil.showToast(BlackCheckMsgActivity.this, "请输入交易卡卡主姓名!");
            return false;
        }
        if(!IDCardUtil.isIDCard(pid)){
            LogUtil.showToast(BlackCheckMsgActivity.this, "请正确输入交易卡卡主身份证号!");
            return false;
        }
        if(TextUtils.isEmpty(phoneno)||phoneno.length()!=11){
            LogUtil.showToast(BlackCheckMsgActivity.this, "请正确输入交易卡银行预留手机号!");
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
                timehandler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
