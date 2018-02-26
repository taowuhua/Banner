package com.ryx.baselib.mvpframe.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Rx线程调度器
 * @author muxin
 * @time 2016-11-15 12:38
 */
public class RxSchedulers {

    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        //生产线程
                        .subscribeOn(Schedulers.io())
                        //消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
//                .map(new MvpServerResponseFunc<>())
//                .onErrorResumeNext(new MvpHttpResponseFunc<>());
    }

}