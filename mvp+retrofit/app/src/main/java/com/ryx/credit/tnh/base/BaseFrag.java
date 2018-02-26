package com.ryx.credit.tnh.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.baselib.mvpframe.base.MvpBaseAct;
import com.ryx.baselib.mvpframe.base.MvpBaseFrag;
import com.ryx.baselib.mvpframe.base.MvpBaseModel;
import com.ryx.baselib.mvpframe.base.MvpBasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：xucc
 * date: 2017/12/1 17:29
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public abstract class BaseFrag <P extends MvpBasePresenter, M extends MvpBaseModel> extends MvpBaseFrag<P,M> {
    private Unbinder mUnBinder;

    @Deprecated
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  mLayoutView = getCreateView(inflater, container);
        mUnBinder=ButterKnife.bind(this, mLayoutView);
        return mLayoutView;
    }
    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();
    /**
     * 初始化视图
     */
    public abstract void initView();
    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onDestroyView() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
}
