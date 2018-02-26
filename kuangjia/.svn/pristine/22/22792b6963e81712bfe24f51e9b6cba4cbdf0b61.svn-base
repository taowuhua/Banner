package com.bank.quickpay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bank.quickpay.R;
import com.bank.quickpay.bean.IconBean;
import com.bank.quickpay.utils.GlideUtils;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.recyclerView.HelperAdapter;
import com.ryx.quickadapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * 首页九宫格适配器
 *
 * @time 2016-11-02 14:23
 */
public class MainGridAdapter extends HelperAdapter<IconBean.IconMsgBean> {
    private OnListItemClickListener mOnItemClickListener = null;
    private View view;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public MainGridAdapter(List<IconBean.IconMsgBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final IconBean.IconMsgBean item) {
        if (null != item) {
            view = viewHolder.itemView;
            viewHolder.setText(R.id.tvIcon, item.getName());
            int id = view.getResources().getIdentifier(item.getRes(), "drawable", view.getContext().getPackageName());
            GlideUtils.getInstance().load(view.getContext(), id, (ImageView) viewHolder.getView(R.id.ivIcon));
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
