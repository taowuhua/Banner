package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bank.quickpay.R;
import com.bank.quickpay.bean.CityInfo;

import java.util.ArrayList;

/**
 * Created by xiepingping.
 */
public class CityAdapter extends BaseAdapter {


    public CityAdapter(Context context) {
        this.context = context;
    }

    Context context;
    private ArrayList<CityInfo> cityList;

    public void setList(ArrayList<CityInfo> cityList){
        this.cityList = cityList;
    }
    @Override
    public int getCount() {
        return cityList.size();
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
        holder.locationName.setText(cityList.get(position).getCityName());
        return convertView;
    }

    class ViewHolder {
        TextView locationName;
    }
}
