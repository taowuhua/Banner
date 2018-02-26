package com.bank.quickpay.activity.transactiondetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.utils.BitmapUntils;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.widget.FingerPaintView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class BlackCheckSignActivity extends BaseActivity {

    @BindView(R.id.black_fingerpaint)
    FingerPaintView fingerpaint;
    Bitmap bitmap;    // 签名照片
    private Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_sign;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        intent = getIntent();
        initFingerView();
    }

    @OnClick(R.id.black_iv_left)
    public void blackIvLeftClick() {
        finish();
    }

    public void initFingerView() {
        fingerpaint.setMenuText((MessageFormat.format(getResources().getString(R.string.agree_agreement),
                getResources().getString(R.string.app_name))),
                getCurrentTime(), getResources().getString(R.string.clear_signature)
                , getResources().getString(R.string.sign_an_agreement),
                R.drawable.bg_anniu_blank, R.drawable.bg_anniu_blue);
        fingerpaint.SetLeftClick(new FingerPaintView.FingerPaintClickListener() {

            @Override
            public void onClick(View iv) {
                if (fingerpaint.isSigned()) {
                    fingerpaint.clearSign();    // 清除签名
                    fingerpaint.setSigned(false);
                } else {
                    LogUtil.showToast(BlackCheckSignActivity.this, "请先签名");
                }
            }
        });
        fingerpaint.SetRightClick(new FingerPaintView.FingerPaintClickListener() {

            @Override
            public void onClick(View iv) {
                // TODO Auto-generated method stub
                if (fingerpaint.isSigned()) {

                    showLoading();
                    //设置为不可操作
                    fingerpaint.SetRightClickable(false);
                    new Thread() {
                        @Override
                        public void run() {
                            fingerpaint.setlockSignature(true);
                            bitmap = fingerpaint.SaveAsBitmap(); //保存图片
                            if (AppConfig.getInstance(BlackCheckSignActivity.this).isLogin()) {
                                if (bitmap != null) {
                                    byte[] bitmapBytes = BitmapUntils.getBitmapByte(bitmap);
                                    Message msg = new Message();
                                    msg.what = 0x001;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("signPicAscii", BitmapUntils.changeBytesToHexString(bitmapBytes));
                                    msg.setData(bundle);
                                    mhandler.sendMessage(msg);
                                } else {
                                    mhandler.sendEmptyMessage(0x002);
                                }
                            } else {
                                toAgainLogin(BlackCheckSignActivity.this.getApplicationContext(),
                                        AppConfig.TOLOGINACT);
                            }

                        }
                    }.start();

                } else {
                    LogUtil.showToast(BlackCheckSignActivity.this, "请先签名!");
                }
            }
        });
    }

    private Handler mhandler = new Handler() {

        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    cancleLoading();
                    Bundle dataBundle = msg.getData();
                    String signPicAsciiData = dataBundle.getString("signPicAscii", "");
                    intent.putExtra("signPicAscii", signPicAsciiData);
                    LogUtil.showLog("发送signPicAscii==" + signPicAsciiData);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 0x002:
                    cancleLoading();
                    LogUtil.showToast(BlackCheckSignActivity.this, "签名图片有误,请重新签名保存!");
                    fingerpaint.setSigned(true);
                    fingerpaint.SetRightClickable(true);
                    break;
            }
        }

    };

    public String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);//设置日期格式
        return df.format(new Date());
    }
}
