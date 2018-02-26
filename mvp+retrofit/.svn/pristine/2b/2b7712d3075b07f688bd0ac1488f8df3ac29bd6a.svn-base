package com.ryx.credit.tnh.ui.demo;

import com.ryx.baselib.mvpframe.base.MvpApiFactory;
import com.ryx.baselib.mvpframe.base.MvpBaseResponse;
import com.ryx.baselib.mvpframe.rx.RxSchedulers;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：xucc
 * date: 2017/11/30 14:36
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public class DemoModel implements DemoContract.Model {
    @Override
    public Observable<MvpBaseResponse<Object>> getMobileMac(String params) {
//        return MvpApiFactory.getService(DemoService.class).getMobileMac(params).compose(RxSchedulers.io_main());
        return MvpApiFactory.getService(DemoService.class).getMobileMac(params)
                .subscribeOn(Schedulers.io())
                //消费线程
                .observeOn(AndroidSchedulers.mainThread());
    }
}
