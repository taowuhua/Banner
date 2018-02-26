package com.ryx.credit.tnh.ui.creditcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseAct;

/**
 * 修改信用卡信息
 */
public class UpdateCardAct extends BaseAct {
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTitleLayout("修改信用卡",true,false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_card;
    }
}
