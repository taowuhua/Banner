package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.bean.EarningCircleBean;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by xpp on 2017/11/2 0002.
 */

public class EarningCircleAdapter extends HelperAdapter<EarningCircleBean>  {

    private View view;

    public EarningCircleAdapter(List<EarningCircleBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final EarningCircleBean item) {
        if (item != null) {
            view = viewHolder.getItemView();
            TextView tv_date =  (TextView)view.findViewById(R.id.tv_date);
            tv_date.setText( DateUtil.StrToDateStr(item.getLocaldate(),"yyyyMMdd",
                    "yyyy年MM月dd日")+"");
            TextView tv_money =  (TextView)view.findViewById(R.id.tv_money);
            tv_money.setText("收入"+ QuickMoneyEncoder.decodeToyuan(item.getAmount())+"元");
            TextView tv_circle1 =  (TextView)view.findViewById(R.id.tv_circle1);
            tv_circle1.setText("一级圈友："+QuickMoneyEncoder.decodeToyuan(item.getAmount1()));
            TextView tv_circle2 =  (TextView)view.findViewById(R.id.tv_circle2);
            tv_circle2.setText("二级圈友："+QuickMoneyEncoder.decodeToyuan(item.getAmount2()));
        }
    }
}


