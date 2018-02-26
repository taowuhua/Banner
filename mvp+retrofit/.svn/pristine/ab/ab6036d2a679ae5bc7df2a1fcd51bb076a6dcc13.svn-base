package com.ryx.credit.tnh.ui.creditcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseAct;
import com.ryx.credit.tnh.ui.home.HomeAct;
import com.ryx.credit.tnh.ui.pay.RechargePlanAct;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 信用卡详细信息
 */
public class CreditCardContentAct extends BaseAct {
@BindView(R.id.update_date_tv)
    TextView updateDateTv;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitleLayout("信用卡信息",true,false);

    }
    @OnClick(R.id.update_date_tv)
    public void updateDateTvClick(){
        Intent intent=new Intent(CreditCardContentAct.this, UpdateCardAct.class);
        startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_credit_card_content;
    }
}
