package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bank.quickpay.R;
import com.bank.quickpay.bean.ProvinceInfo;

import java.util.ArrayList;

/**
 */
public class ProvinceAdapter extends BaseAdapter {

    public ProvinceAdapter(Context context,ArrayList<ProvinceInfo> provinceList){
        this.context=context;
        this.provinceList=provinceList;
    }

    Context context;
    private ArrayList<ProvinceInfo> provinceList;

    public void setList(ArrayList<ProvinceInfo> provincelist){
        this.provinceList = provincelist;
    }
    @Override
    public int getCount() {
        return provinceList.size();
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
        holder.locationName.setText(provinceList.get(position).getProvinceName());
        return convertView;
    }

    class ViewHolder {
        TextView locationName;
    }
}
