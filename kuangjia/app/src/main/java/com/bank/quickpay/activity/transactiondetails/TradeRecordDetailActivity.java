package com.bank.quickpay.activity.transactiondetails;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.adapter.TradeRecordDetailAdapter;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.IntentUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.bank.quickpay.utils.StringUnit;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 交易明细
 * Created by xpp on 2017/11/6 0006.
 */

public class TradeRecordDetailActivity extends BaseActivity {

    @BindView(R.id.lv_detaile)
    ListView lv_detaile;

    @BindView(R.id.tv_tradeState)
    TextView tv_tradeState;

    private TradeDetailInfo tradeDetailInfo;

    ArrayList<Param> details = new ArrayList<Param>();

    private TradeRecordDetailAdapter detailAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("交易明细", true, false);
        tradeDetailInfo =(TradeDetailInfo)IntentUtil.getSerializableExtra(getIntent(),
                "tradeDetailInfo");
        initInfo();
        detailAdapter = new TradeRecordDetailAdapter(this,details);
        lv_detaile.setAdapter(detailAdapter);
        getBalanceStep();
    }

    private void initInfo(){
        details.add(new Param(getLeftValue(tradeDetailInfo.getOrderId()),
                getRightValue(tradeDetailInfo.getOrderId())));
        details.add(new Param(getLeftValue(tradeDetailInfo.getTransName()),
                getRightValue(tradeDetailInfo.getTransName())));
        details.add(new Param(getLeftValue(tradeDetailInfo.getAccount2()),
                StringUnit.phoneJiaMi(getRightValue(tradeDetailInfo
                        .getAccount2()))));
        details.add(new Param(getLeftValue(tradeDetailInfo.getLocalTime()),
                fomatDate(getRightValue(tradeDetailInfo.getLocalDate())
                        + getRightValue(tradeDetailInfo.getLocalTime()))));
        details.add(new Param(getLeftValue(tradeDetailInfo.getAmount()),
                QuickMoneyEncoder.decodeFormat(getRightValue(tradeDetailInfo
                        .getAmount()))));
        details.add(new Param(
                getLeftValue(tradeDetailInfo.getFee()),
                QuickMoneyEncoder.decodeFormat(getRightValue(tradeDetailInfo.getFee()))));
        details.add(new Param(getLeftValue(tradeDetailInfo.getMobileno()),
                StringUnit.phoneJiaMi(getRightValue(tradeDetailInfo
                        .getMobileno()))));
        details.add(new Param(getLeftValue(tradeDetailInfo.getPayType()), ("02"
                .equals(getRightValue(tradeDetailInfo.getPayType())) ? "账户支付"
                : "刷卡支付")));
        details.add(new Param(
                getLeftValue(tradeDetailInfo.getAccount()),
                StringUnit.cardJiaMi(getRightValue(tradeDetailInfo.getAccount()))));
    }

    public String getLeftValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            return values[0];
        }
        return "";
    }
    public String getRightValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            if (values.length == 2) {
                return values[1];
            }
            return "";
        }
        return "";
    }

    public static String fomatDate(String date) {
        return DateUtil.DateToString(DateUtil.StrToDate(date));
    }

    //获取资金动态
    public  void getBalanceStep(){
        initQtPatParams();
        qtpayApplication.setValue("GetBalanceStep.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("localdate", DataUtil.getRightValue(tradeDetailInfo.getLocalDate())));
        qtpayParameterList.add(new Param("locallogno",DataUtil.getRightValue(tradeDetailInfo.getLocalLogNo())));
        qtpayParameterList.add(new Param("orderId",DataUtil.getRightValue(tradeDetailInfo.getOrderId())));
        httpsPost("GetBalanceStepTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                String desc = payResult.getDesc();
                tv_tradeState.setText(desc);
            }
        });
    }
}
