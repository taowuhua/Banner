package com.ryx.credit.tnh.ui.demo;

import com.ryx.baselib.mvpframe.base.MvpBaseModel;
import com.ryx.baselib.mvpframe.base.MvpBasePresenter;
import com.ryx.baselib.mvpframe.base.MvpBaseResponse;
import com.ryx.baselib.mvpframe.base.MvpBaseView;
import com.ryx.baselib.mvpframe.exception.MvpServerException;

import rx.Observable;

/**
 * Author：xucc
 * date: 2017/11/30 14:17
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：当前接口定义当前业务模块下所有接口契约
 */
public interface DemoContract {

    interface Model extends MvpBaseModel{
        Observable<MvpBaseResponse<Object>> getMobileMac(String params);
    }
    interface View extends MvpBaseView {
        void getMobileMacSuccess(String resut);
        void getMobileMacBusinessFailed(MvpServerException ex);
    }

    abstract class Presenter extends MvpBasePresenter<DemoContract.Model, DemoContract.View> {
        public abstract void getMobileMac(String params);

    }

}
