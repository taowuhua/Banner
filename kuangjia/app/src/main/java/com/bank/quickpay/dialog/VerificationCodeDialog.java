package com.bank.quickpay.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.psw.ResetPasswordActivity;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.interfaces.VerificationCodeDialogListener;
import com.rey.material.widget.Button;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 验证码弹出输入框
 */
public class VerificationCodeDialog extends Dialog {
    List<EditText> verificationCodeEditList=new ArrayList<>();
    EditText verificationCode_edit1,verificationCode_edit2,verificationCode_edit3,verificationCode_edit4,verificationCode_edit5,verificationCode_edit6;
    private VerificationCodeDialog verificationCodeDialog;
    private VerificationCodeDialogListener verificationCodeDialogListener;
    private Timer mTimer = new Timer();
    Button sendaginbtn;
    Context context;
    private boolean iscanClick=true;
    private ImageView closeDialog_iv;
    private int msgcount;
    public VerificationCodeDialog(int msgcount,Context context) {
        super(context,R.style.quickDialogMsgDialog);
        this.context=context;
        this.msgcount=msgcount;
    }
    public VerificationCodeDialog(int msgcount,Context context, VerificationCodeDialog listener) {
        super(context,R.style.quickDialogMsgDialog);

        this.verificationCodeDialog=listener;
        this.context=context;
        this.msgcount=msgcount;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        layout.width = d.getWidth() * 90 / 100;
        layout.height=d.getHeight()* 45 / 100;
        getWindow().setAttributes(layout);
        setContentView(R.layout.verificationcodeinputlayout);
        verificationCode_edit1=(EditText) findViewById(R.id.verificationCode_edit1);
        verificationCode_edit2=(EditText) findViewById(R.id.verificationCode_edit2);
        verificationCode_edit3=(EditText) findViewById(R.id.verificationCode_edit3);
        verificationCode_edit4=(EditText) findViewById(R.id.verificationCode_edit4);
        verificationCode_edit5=(EditText) findViewById(R.id.verificationCode_edit5);
        verificationCode_edit6=(EditText) findViewById(R.id.verificationCode_edit6);
        closeDialog_iv=(ImageView) findViewById(R.id.closeDialog_iv);
        verificationCodeEditList.add(verificationCode_edit1);
        verificationCodeEditList.add(verificationCode_edit2);
        verificationCodeEditList.add(verificationCode_edit3);
        verificationCodeEditList.add(verificationCode_edit4);
        if(msgcount==6){
            verificationCode_edit5.setVisibility(View.VISIBLE);
            verificationCode_edit6.setVisibility(View.VISIBLE);
            verificationCodeEditList.add(verificationCode_edit5);
            verificationCodeEditList.add(verificationCode_edit6);
        }

        sendaginbtn= (Button) findViewById(R.id.sendagin_nextbtn);
        sendaginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iscanClick){
                    mTimer = null;
                    mTimer = new Timer();
                    if( verificationCodeDialogListener!=null){
                        verificationCodeDialogListener.onaginBtnClicked(VerificationCodeDialog.this);
                    }
                    startCountdown();
                }
            }
        });
        closeDialog_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( verificationCodeDialogListener!=null){
                    verificationCodeDialogListener.onCloseClicked(VerificationCodeDialog.this);
                }
            }
        });
        for(int i=0;i<verificationCodeEditList.size();i++){
            EditText editText= verificationCodeEditList.get(i);
            editText.addTextChangedListener(textWatcher);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editText.setOnKeyListener(onKeyListener);
        }
    }
    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                sendaginbtn.setTextColor(ContextCompat.getColor(context, R.color.fourblack));
                sendaginbtn.setText(context.getResources().getString(R.string.resend) + "(" + msg.what + ")");
                sendaginbtn.setClickable(false);
                iscanClick=false;
            } else {
                mTimer.cancel();
                sendaginbtn.setText(context.getResources().getString(R.string.resend_verification_code));
                sendaginbtn.setClickable(true);
                sendaginbtn.setTextColor(ContextCompat.getColor(context, R.color.secondblack));
                iscanClick=true;
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

    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public synchronized boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                backFocus();
            }
            return false;
        }
    };
    private void focus() {
        for(int i=0;i<verificationCodeEditList.size();i++){
            EditText editText= verificationCodeEditList.get(i);
            if (editText.getText().length() < 1) {
                editText.requestFocus();
                return;
            }
        }
    }

    private void backFocus() {
        int count = verificationCodeEditList.size();
        EditText editText ;
        for (int i = count-1; i>= 0; i--) {
            editText = verificationCodeEditList.get(i);
            if (editText.getText().length() == 1) {
                editText.requestFocus();
                editText.setSelection(1);
                return;
            }
        }
    }


    private void checkAndCommit() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean full = true;
        for (int i = 0 ;i < verificationCodeEditList.size(); i++){
            EditText editText = verificationCodeEditList.get(i);
            String content = editText.getText().toString();
            if ( content.length() == 0) {
                full = false;
                break;
            } else {
                stringBuilder.append(content);
            }

        }
        if (full){
            if( this.verificationCodeDialogListener!=null){
                this.verificationCodeDialogListener.onCodeInputOked(VerificationCodeDialog.this, stringBuilder.toString());
            }
        }
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
            } else {
                focus();
                checkAndCommit();
            }

        }

    };
    /**
     * 设置手机号提示信息
     * @param msg
     */
    public void setphoneNumberMsg(String msg){
       TextView phoneText=(TextView) findViewById(R.id.verificationcode_tv_display);
        phoneText.setText(msg);
    }

    /**
     * 设置重发按钮和输入完验证码后监听
     * @param verificationCodeDialogListener
     */
    public void setClickListen(VerificationCodeDialogListener verificationCodeDialogListener){
        this.verificationCodeDialogListener=verificationCodeDialogListener;
    }


}
