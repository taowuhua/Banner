package com.ryx.credit.tnh.ui.home.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.ryx.credit.tnh.R;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：xucc
 * date: 2017/12/7 14:15
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public class HomeCardAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private View view;
    private List<Map<String,String>> data;
    private Context context;
    private static int VIEW1=1;//普通列表布局
    private static int VIEW2=2;//ADD
    private OnItemClickListener onItemClickListener;
    private NoDoubleClickListener addcreditLayoutListener;

    public HomeCardAdapter(List<Map<String, String>> mList, Context context) {
        this.data=mList;
        this.context=context;
        //放一个空的最后一个布局
        data.add(new HashMap<String, String>());
    }
    public void setItemOnclick(OnItemClickListener onItemClickListener){
            this.onItemClickListener=onItemClickListener;
    }
    public void setAddcreditLayoutOnclick(NoDoubleClickListener addcreditLayoutListener){
            this.addcreditLayoutListener=addcreditLayoutListener;
    }
    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecarditemlayout, parent, false);
            ViewHolder1 viewHolder=   new ViewHolder1(view);
            if(onItemClickListener!=null){
                view.setOnClickListener(this);
            }
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecardaddlayout, parent, false);
            ViewHolder2 viewHolder=   new ViewHolder2(view);
            if(addcreditLayoutListener!=null){
                view.setOnClickListener(addcreditLayoutListener);
            }
            return viewHolder;
        }

    }
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //显示布局UI
        if(holder instanceof ViewHolder1){
            //View1
            holder.itemView.setTag(position);

        }else{

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==data.size()-1){
            return VIEW2;
        }else{
            return VIEW1;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    static class ViewHolder1 extends RecyclerView.ViewHolder {
        public ViewHolder1(View itemView) {
            super(itemView);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder {
        public ViewHolder2(View itemView) {
            super(itemView);
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

}
