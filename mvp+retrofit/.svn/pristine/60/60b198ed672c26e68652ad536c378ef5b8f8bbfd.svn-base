package com.ryx.baselib.mvpframe.base;

import android.content.Context;


/**
 *业务逻辑处理层
 * @param <M>
 * @param <V>
 */
public abstract class MvpBasePresenter<M, V> {
    public Context mContext;
    public M mModel;
    public V mView;
    public void setVM(V v, M m, Context context) {
        this.mView = v;
        this.mModel = m;
        this.mContext = context;
    }
}
