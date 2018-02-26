package com.ryx.baselib.mvpframe.exception;

import com.ryx.baselib.mvpframe.base.MvpApiException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Http响应Func
 * @author muxin
 * @time 2016-07-28 9:37
 */
public class MvpHttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(new MvpApiException(throwable));
    }
}