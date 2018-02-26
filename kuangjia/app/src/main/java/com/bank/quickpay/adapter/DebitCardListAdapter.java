package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bank.quickpay.R;
import com.bank.quickpay.bean.BankCardInfo;
import com.bank.quickpay.utils.BanksUtils;
import com.bank.quickpay.utils.StringUnit;

import java.util.ArrayList;

/**
 */
public class DebitCardListAdapter extends BaseAdapter {

    private ArrayList<BankCardInfo> cardInfoList;
    private View contentView;

    private int height, width;
    private ViewHolder holder = null;

    Context context;

    public void setList(ArrayList<BankCardInfo> banklist) {
        this.cardInfoList = banklist;
    }

    void init() {
        cardInfoList = new ArrayList<BankCardInfo>();
    }

    @Override
    public int getCount() {
        return cardInfoList.size();
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
        holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.adapter_binded_card_debit_item, null);
            contentView = convertView;
            holder.default_bankaccout=(TextView) convertView.findViewById(R.id.default_bankaccout);
            holder.iv_ico = (ImageView) convertView.findViewById(R.id.iv_logo);
            holder.bankName = (TextView) convertView.findViewById(R.id.tv_bankname);
            holder.bankAccount = (TextView) convertView.findViewById(R.id.tv_bankno);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
//            holder.autoRelativeLayout = (AutoRelativeLayout) convertView.findViewById(R.id.lay_logo_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String bankId = cardInfoList.get(
                position).getBankId();
        if(bankId.length()>6){
            bankId = bankId.substring(1,4);
        }
        BanksUtils.selectIcoidToImgView(context,bankId, holder.iv_ico);
        holder.bankName.setText(cardInfoList.get(position).getBankName());
        if( "1".equals(cardInfoList.get(position).getFlagInfo())){
            holder.default_bankaccout.setText("默认结算卡");
        }else{
            holder.default_bankaccout.setText("");
        }
//        if ("102".equals(bankId) || "104".equals(bankId) || "302".equals(bankId)
//                || "304".equals(bankId) || "306".equals(bankId) || "308".equals(bankId)) {
//            holder.autoRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.red_bank));
//        } else if ("105".equals(bankId) || "301".equals(bankId) || "309".equals(bankId)
//                || "310".equals(bankId)) {
//            holder.autoRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.blue_bank));
//        } else if ("103".equals(bankId) || "305".equals(bankId) || "403".equals(bankId)) {
//            holder.autoRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.green_bank));
//        } else if ("303".equals(bankId) || "307".equals(bankId) || "".equals(bankId)) {
//            holder.autoRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow_bank));
//        }
        holder.bankAccount.setText(StringUnit.cardJiaMi(cardInfoList.get(
                position).getAccountNo()));

        holder.tv_userName.setText(StringUnit.realNameJiaMi(cardInfoList.get(position).getName()));
        if (null != cardInfoList.get(position).getFlagInfo() && "01".equals(cardInfoList.get(position).getFlagInfo())) {
            holder.iv_hasbound.setVisibility(View.GONE);
        }
        return convertView;
    }
    @Override
    public int getViewTypeCount() {
        // menu type count
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type
        String flagInfo=cardInfoList.get(position).getFlagInfo();
        if("1".equals(flagInfo)){
            return 1;
        }else{
            return 0;
        }
    }
    class ViewHolder {
        ImageView iv_ico;
        ImageView iv_arrow;
        TextView bankName;
        TextView bankAccount;
        TextView tv_userName;
//        AutoRelativeLayout autoRelativeLayout;
        ImageView iv_hasbound;
        TextView default_bankaccout;
    }


}