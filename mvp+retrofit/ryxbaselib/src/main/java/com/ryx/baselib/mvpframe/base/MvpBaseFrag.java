package com.ryx.baselib.mvpframe.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.baselib.R;
import com.ryx.baselib.utils.TUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MvpBaseFrag<P extends MvpBasePresenter, M extends MvpBaseModel> extends Fragment {
    public P mPresenter;
    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取出泛型中的业务处理类和数据获取类
        mPresenter = TUtil.getT(this, 0);//P
        mModel = TUtil.getT(this, 1);//M
        if (this instanceof MvpBaseView) {
            //presenter与view和model的关联
            mPresenter.setVM(this, mModel, getActivity());
        }
    }



}
