package com.bank.quickpay.fragment.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.bank.quickpay.R;
import com.bank.quickpay.adapter.TradeRecordDetailAdapter;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.fragment.base.BaseFragment;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.bank.quickpay.utils.StringUnit;

import java.util.ArrayList;

import butterknife.BindView;

public class DetailsContentFragment extends BaseFragment {
    private TradeDetailInfo tradeDetailInfo;

    ArrayList<Param> details = new ArrayList<Param>();

    private TradeRecordDetailAdapter detailAdapter;
    @BindView(R.id.lv_detaile)
    ListView lv_detaile;
    public DetailsContentFragment() {
    }

    /**
     * 创建Fragement实例
     *
     * @param tradeDetailInfo
     * @return
     */
    public static DetailsContentFragment newInstance(TradeDetailInfo tradeDetailInfo) {
        DetailsContentFragment fragment = new DetailsContentFragment();
        Bundle args = new Bundle();
        args.putSerializable("tradeDetailInfo", tradeDetailInfo);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_details_content;
    }

    @Override
    public void afterViews(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tradeDetailInfo = (TradeDetailInfo) getArguments().getSerializable("tradeDetailInfo");
        initInfo();
        detailAdapter = new TradeRecordDetailAdapter(getContext(),details);
        lv_detaile.setAdapter(detailAdapter);
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



}
