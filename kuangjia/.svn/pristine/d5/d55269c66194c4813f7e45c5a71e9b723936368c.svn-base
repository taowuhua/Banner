package com.bank.quickpay.activity.payment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.MainActivity;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.transactiondetails.TradeRecordActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rm.com.clocks.ClockImageView;

/**
 * 收款结果轮询页
 */
public class PaySuccessActivity extends BaseActivity {


    @BindView(R.id.clocks)
    ClockImageView clocks;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_info)
    TextView tvInfo;
String orderId,amount;
    Timer loadingStatetimer = new Timer();//请求计时器
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("收款结果", true, false);
        orderId = getIntent().getStringExtra("orderId");
        amount = getIntent().getStringExtra("amount");
        if (TextUtils.isEmpty(orderId)) {
            setResult(AppConfig.CLOSE_ALL);
            finish();
            return;
        }
        initQtPatParams();
        showLoadingStateDialog();
    }
        /**
         * 展示等待处理结果
         */
    public void showLoadingStateDialog() {
        clocks.animateIndeterminate();
        tvStatus.setText("处理中");
        //如果金额不为空，显示交易金额
        if (!TextUtils.isEmpty(amount)) {
            tvInfo.setText("¥"+amount);
        }
        if (loadingStatetimer != null) {
            loadingStatetimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            }, 3000);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                initiativeSaoMaPayStateCheckTag(orderId, msg.what);
            } else if (msg.what == 2) {
                initiativeSaoMaPayStateCheckTag(orderId, msg.what);
            }
            super.handleMessage(msg);
        }
    };

    public void initiativeSaoMaPayStateCheckTag(String orderId, final int number) {
            qtpayApplication.setValue("KJFXPayCheck.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("orderId", orderId));
        httpsPost(false, false, "KJFXPayCheckTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {

                try {
                    JSONObject jsonObject = new JSONObject(payResult.getData());
                    String code = JsonUtil.getValueFromJSONObject(jsonObject, "code");
                    String msg = JsonUtil.getValueFromJSONObject(jsonObject, "msg");
                    if (number == 1 && "9101".equals(code) && loadingStatetimer != null) {
                        loadingStatetimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(2);
                            }
                        }, 10000);
                    } else if (number == 2 && "9101".equals(code)) {
                        //第二次查询无结果
                        setImgState(3, msg);

                    } else if ("0000".equals(code)) {
                        LogUtil.showToast(PaySuccessActivity.this, msg);
                        setImgState(2, msg);
                    } else {
                        LogUtil.showToast(PaySuccessActivity.this, msg);
                        showMsgDialog(msg, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.showToast(PaySuccessActivity.this, "交易结果未知");
                    showMsgDialog("交易结果未知,是否查看交易明细?", false);
                }
            }

            /**
             * 展示提示信息退出对话框
             *
             * @param message
             */
            public void showMsgDialog(String message, boolean isOnlyOk) {

                QuickSimpleConfirmDialog ryxSimpleConfirmDialog = new QuickSimpleConfirmDialog(PaySuccessActivity.this, new ConFirmDialogListener() {

                    @Override
                    public void onPositiveActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                        ryxSimpleConfirmDialog.dismiss();
                        setResult(AppConfig.CLOSE_ALL);
                        finish();
                        startActivity(new Intent(PaySuccessActivity.this,TradeRecordActivity.class));
                    }

                    @Override
                    public void onNegativeActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                        ryxSimpleConfirmDialog.dismiss();
                        setResult(AppConfig.CLOSE_ALL);
                        finish();
                    }
                });
                ryxSimpleConfirmDialog.show();
                ryxSimpleConfirmDialog.setContent("交易结果未知,是否查看交易明细?");

            }
            @Override
            public void onTradeFailed() {
                super.onTradeFailed();
                if (loadingStatetimer != null) {
                    loadingStatetimer.cancel();
                    loadingStatetimer = null;
                }
                setImgState(3, "查询结果失败,请稍后再试");
            }

            @Override
            public void onOtherState() {
                super.onOtherState();
                if (loadingStatetimer != null) {
                    loadingStatetimer.cancel();
                    loadingStatetimer = null;
                }
                setImgState(3, "查询结果失败,请稍后再试");
            }

            @Override
            public void onLoginAnomaly() {
                super.onLoginAnomaly();
                if (loadingStatetimer != null) {
                    loadingStatetimer.cancel();
                    loadingStatetimer = null;
                }
                setImgState(3, "查询结果失败,请稍后再试");
            }

        });
    }


    /**
     * 动态设置处理状态
     *
     * @param code 1等待处理中,2处理OK 3处理失败
     */
    public void setImgState(int code, String msg) {
        if (code == 1) {
            clocks.animateIndeterminate();
            imgStatus.setVisibility(View.GONE);
            tvStatus.setText("处理中");
            if (!TextUtils.isEmpty(amount)) {
                tvInfo.setText("¥"+amount);}
        } else if (code == 2) {
            clocks.stop();
            clocks.setVisibility(View.GONE);
            imgStatus.setVisibility(View.VISIBLE);
            imgStatus.setBackgroundResource(R.drawable.circle_success);
            tvStatus.setText("交易成功");
            if (!TextUtils.isEmpty(amount)) {
                tvInfo.setText("¥"+amount);}
        } else if (code == 3) {
            clocks.stop();
            clocks.setVisibility(View.GONE);
            imgStatus.setVisibility(View.VISIBLE);
            imgStatus.setBackgroundResource(R.drawable.circle_fail);
            tvStatus.setText(msg);
            if (!TextUtils.isEmpty(amount)) {
                tvInfo.setText("¥"+amount);}
        }
    }
    @Override
    public void backUpImgOnclickListen(){
        setResult(AppConfig.CLOSE_ALL);
        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            setResult(AppConfig.CLOSE_ALL);
            finish();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
