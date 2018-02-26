package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.bean.Param;

import java.util.ArrayList;

/**
 * 交易明细
 * Created by xiepp on 2016/8/3.
 */
public class TradeRecordDetailAdapter extends BaseAdapter {

    Context mcontext;
    private LayoutInflater mInflater;
    ArrayList<Param> details = new ArrayList<Param>();
    private ViewHolder viewHolder = null;

    public TradeRecordDetailAdapter(Context context,ArrayList<Param> list){
        mcontext = context;
        details = list;
    }


    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.fragment_detail_trade_adapter, null);
            holder = new ViewHolder();
            holder.tv_left = (TextView) view.findViewById(R.id.tv_KeyName);
            holder.tv_right = (TextView) view.findViewById(R.id.tv_ValName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_left.setText(details.get(position).getKey());
        holder.tv_right.setText(details.get(position).getValue());
        return view;
    }

    class ViewHolder {
        TextView tv_left;
        TextView tv_right;
    }
}
