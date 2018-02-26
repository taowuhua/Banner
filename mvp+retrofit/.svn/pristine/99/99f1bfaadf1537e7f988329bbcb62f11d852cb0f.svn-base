package com.ryx.credit.tnh.ui.pay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseAct;

import java.util.Timer;

import butterknife.BindView;
import rm.com.clocks.ClockImageView;

/***
 * 支付结果页面
 */
public class PayResultAct extends BaseAct {
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
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitleLayout("交易结果",true,false);
//        setImgState(2,"");
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
    public int getLayoutId() {
        return R.layout.activity_pay_result;
    }
}
