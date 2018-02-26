package com.ryx.credit.tnh.ui.demo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryx.baselib.mvpframe.exception.MvpServerException;
import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseFrag;
import com.ryx.credit.tnh.ui.demo.DemoContract;
import com.ryx.credit.tnh.ui.demo.DemoModel;
import com.ryx.credit.tnh.ui.demo.DemoPresenter;
import com.ryx.credit.tnh.utils.LogUtil;

public class DemoFragment extends BaseFrag<DemoPresenter, DemoModel>
        implements DemoContract.View {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_demo;
    }

    @Override
    public void initView() {

    }


    @Override
    public void getMobileMacSuccess(String resut) {
        LogUtil.showToast(getContext(),resut);

    }

    @Override
    public void getMobileMacBusinessFailed(MvpServerException ex) {
        LogUtil.showToast(getContext(),ex.getMsg());
    }
}
