package com.ryx.credit.tnh.ui.demo;

import com.ryx.baselib.mvpframe.base.MvpBaseResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author：xucc
 * date: 2017/11/30 15:35
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：
 */
public interface DemoService {
    @POST("insteadRepayment/")
    Observable<MvpBaseResponse<Object>> getMobileMac(@Query("params") String params);

}
