package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by xpp on 2017/11/2 0002.
 */

public class TradeRecordAdapter extends HelperAdapter<TradeDetailInfo> {

    private OnListItemClickListener mOnItemClickListener = null;
    private View view;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public TradeRecordAdapter(List<TradeDetailInfo> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, final int position, final TradeDetailInfo item) {
        if (null != item) {
            view = viewHolder.getItemView();

            TextView states_tv =  (TextView)view.findViewById(R.id.tv_state);
            TextView pay_income_tv =  (TextView)view.findViewById(R.id.tv_money);
            TextView date_tv =  (TextView)view.findViewById(R.id.tv_date);
            states_tv.setText("交易成功");
            pay_income_tv.setText(DataUtil.getRightValue(item.getTransName())+"  "+ QuickMoneyEncoder.decodeFormat(
                    DataUtil. getRightValue(item.getAmount())).replace(
                    "￥", "+"));
            date_tv.setText(fomatDate(DataUtil.getRightValue(item.getLocalDate())
                    + DataUtil.getRightValue(item.getLocalTime())));
            view.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if(mOnItemClickListener!=null) {
                        mOnItemClickListener.onItemClick(view, position, item);
                    }
                }
            });
        }
    }

    public static String fomatDate(String date) {
        return DateUtil.DateToString(DateUtil.StrToDate(date));
    }
}
