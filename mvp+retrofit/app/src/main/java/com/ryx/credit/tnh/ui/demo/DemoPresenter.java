package com.ryx.credit.tnh.ui.demo;

import com.ryx.baselib.mvpframe.exception.MvpERROR;
import com.ryx.baselib.mvpframe.exception.MvpHttpResponseFunc;
import com.ryx.baselib.mvpframe.exception.MvpServerException;
import com.ryx.baselib.mvpframe.exception.MvpServerResponseFunc;
import com.ryx.baselib.mvpframe.rx.RxSubscriber;

/**
 * Author：xucc
 * date: 2017/11/30 14:16
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public class DemoPresenter extends DemoContract.Presenter{

    @Override
    public void getMobileMac(String params) {
        mModel.getMobileMac(params)
                .map(new MvpServerResponseFunc<>())
                .onErrorResumeNext(new MvpHttpResponseFunc())
                .subscribe(new RxSubscriber<Object>(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        mView.getMobileMacSuccess(o.toString());
                    }

                    @Override
                    protected void _onMvpBusinessError(MvpServerException ex) {
                        mView.getMobileMacBusinessFailed(ex);
                    }

                });
    }
}
