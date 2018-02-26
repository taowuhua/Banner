package com.ryx.credit.tnh.ui.pay;

import android.content.Intent;
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
import butterknife.OnClick;
import rm.com.clocks.ClockImageView;

/**
 *充值还款计划
 */
public class RechargePlanAct extends BaseAct {


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitleLayout("充值还款计划",true,false);
    }
    @OnClick(R.id.pay_btn)
    public void payBtnClick(){

        Intent intent=new Intent(RechargePlanAct.this,PayResultAct.class);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_plan;
    }

}
