package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bank.quickpay.R;
import com.bank.quickpay.bean.CardBeanMap;
import com.bank.quickpay.utils.MapUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by XCC on 2017/3/20.
 * 首页广告位适配器
 */

public class PaySelectCardAdapter extends HelperAdapter<CardBeanMap> {
    private OnListItemClickListener mOnItemClickListener = null;
    private int selectedposition=-1;
    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public PaySelectCardAdapter(List<CardBeanMap> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }
    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setSelectedposition(int selectedposition){
        this.selectedposition=selectedposition;
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final CardBeanMap item) {
        if (null != item&&item.getMap()!=null) {
           Map<String,String> aditemMap= item.getMap();
             View   view = viewHolder.itemView;
            String bankName=    MapUtil.get(aditemMap,"bankName","");
            viewHolder.setText(R.id.bankname_tv, bankName);
            String imgurl=    MapUtil.get(aditemMap,"bankLogo","");
            Glide.with(view.getContext())
                    .load(imgurl)
                    .placeholder(R.drawable.bank_default)//显示加载时的图片
                    .error(R.drawable.bank_default)//加载失败默认显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                    .dontAnimate()//无动画
                    .into((ImageView)viewHolder.getView(R.id.bankImg_iv));
//           String isseleced= MapUtil.get(aditemMap,"selected","false");
           if(selectedposition==position){
               viewHolder.setVisible(R.id.selectedImg_iv,true);
           }else{
               viewHolder.setVisible(R.id.selectedImg_iv,false);
           }
        }
        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
                }
            }
        });
    }

}
