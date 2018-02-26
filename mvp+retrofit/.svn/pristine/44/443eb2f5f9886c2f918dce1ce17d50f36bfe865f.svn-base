package com.ryx.baselib.mvpframe.exception;

import com.ryx.baselib.mvpframe.base.MvpBaseResponse;

import rx.functions.Func1;

/**
 * 服务端响应Func
 * 拦截固定格式的公共数据类型BaseResponse<T>,判断里面的状态码
 *
 * @author muxin
 * @time 2016-10-28 9:23
 */
public class MvpServerResponseFunc<B> implements Func1<MvpBaseResponse<B>, B> {
    @Override
    public B call(MvpBaseResponse<B> response) {
        //对返回码进行判断，如果不是0000，则证明服务器端返回错误信息
        if (!"0000".equals(response.respCode)) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new MvpServerException(response.respCode, response.respDesc,response.error);
        }
        //服务器请求数据成功，返回里面的数据实体
        return response.body;
    }

}