package com.bank.quickpay.activity.earningscircle;

import android.os.Bundle;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收益圈圈友数量明细
 */
public class CircleTheDetailActivity extends BaseActivity {


    @BindView(R.id.one_circle_allcount_tv)
    TextView oneCircleAllcountTv;
    @BindView(R.id.one_circle_validitycount_tv)
    TextView oneCircleValiditycountTv;
    @BindView(R.id.two_circle_allcount_tv)
    TextView twoCircleAllcountTv;
    @BindView(R.id.two_circle_validitycount_tv)
    TextView twoCircleValiditycountTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_the_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("圈友", true, false);

        String bank1 = getIntent().getStringExtra("bank1");
        String bank2 = getIntent().getStringExtra("bank2");
        String valid_level1 = getIntent().getStringExtra("valid_level1");
        String valid_level2 = getIntent().getStringExtra("valid_level2");
        oneCircleAllcountTv.setText(MessageFormat.format(getResources().getString(R.string.circle_allcount), bank1));
        twoCircleAllcountTv.setText(MessageFormat.format(getResources().getString(R.string.circle_allcount), bank2));
        oneCircleValiditycountTv.setText(MessageFormat.format(getResources().getString(R.string.circle_validitcount), valid_level1));
        twoCircleValiditycountTv.setText(MessageFormat.format(getResources().getString(R.string.circle_validitcount), valid_level2));

    }

}
