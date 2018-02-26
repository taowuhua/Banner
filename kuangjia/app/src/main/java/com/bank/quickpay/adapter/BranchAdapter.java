package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bank.quickpay.R;
import com.bank.quickpay.bean.BankCardInfo;

import java.util.LinkedList;

/**
 */
public class BranchAdapter extends BaseAdapter {
    public BranchAdapter(Context context) {
        this.context = context;
    }

    Context context;
    private LinkedList<BankCardInfo> branchList;

    public void setList(LinkedList<BankCardInfo> branchList){
        this.branchList = branchList;
    }
    @Override
    public int getCount() {
        return branchList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bind_debit_card_province_item, null);
            holder.locationName= (TextView)convertView.findViewById(R.id.tv_province);
            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        holder.locationName.setText(branchList.get(position).getBranchBankName());
        return convertView;
    }

    class ViewHolder {
        TextView locationName;
    }
}
