package com.ryx.baselib.mvpframe.rx;

import android.content.Context;
import android.widget.Toast;


import com.google.gson.JsonParseException;
import com.ryx.baselib.mvpframe.base.MvpApiException;
import com.ryx.baselib.mvpframe.exception.MvpERROR;
import com.ryx.baselib.mvpframe.exception.MvpServerException;
import com.ryx.baselib.widget.RyxLoadDialogBuilder;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 封装订阅 统一处理异常及loading
 *
 * @author muxin
 * @time 2016-11-16 15:20
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean showDialog = true;//默认显示loading

    public RxSubscriber(Context context, boolean showDialog) {
        this.mContext = context;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            RyxLoadDialogBuilder.getInstance(mContext).dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                RyxLoadDialogBuilder.getInstance(mContext).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            RyxLoadDialogBuilder.getInstance(mContext).dismiss();
        }
        e.printStackTrace();
        if(e instanceof MvpApiException){
           Throwable throwable= ((MvpApiException) e).getThrowable();

            if (throwable instanceof HttpException) {             //HTTP错误
                MvpApiException   ex = new MvpApiException(throwable, MvpERROR.HTTP_ERROR,"请求服务器异常");
                doErrorMsg(ex);
            } else if (throwable instanceof MvpServerException) {    //服务器返回的错误
                MvpServerException resultException = (MvpServerException) throwable;
                MvpApiException ex = new MvpApiException(resultException, resultException.getCode(),resultException.getMsg());
                doErrorMsg(ex);
                _onMvpBusinessError(resultException);
            } else if (throwable instanceof JsonParseException
                    || throwable instanceof JSONException
                    || throwable instanceof ParseException) {
                MvpApiException ex = new MvpApiException(throwable, MvpERROR.PARSE_ERROR,"数据格式解析错误");
                doErrorMsg(ex);
            } else if (throwable instanceof ConnectException) {
                MvpApiException   ex = new MvpApiException(throwable, MvpERROR.NETWORD_ERROR,"网络连接失败");
                doErrorMsg(ex);
            } else if (throwable instanceof SocketTimeoutException) {
                MvpApiException ex = new MvpApiException(throwable, MvpERROR.NETWORD_ERROR,"连接服务器超时");
                doErrorMsg(ex);
            }else {
                MvpApiException ex = new MvpApiException(throwable, MvpERROR.UNKNOWN,"未知异常");
                doErrorMsg(ex);
            }
        }else{
            MvpApiException ex = new MvpApiException(e, MvpERROR.UNKNOWN,"未知BUG异常");
            doErrorMsg(ex);
        }
    }

    /**
     * 处理错误异常信息
     * @param ex
     */
    private void doErrorMsg(MvpApiException ex){
        if(_onIsShowError()){
            Toast.makeText(mContext, ex.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
        _onError(ex);
    }

    /**
     * 数据正常返回Body信息
     * @param t
     */
    protected abstract void _onNext(T t);

    /**
     * 所有错误异常情况监听
     * @param ex
     */
    protected  void _onError(MvpApiException ex){}

    /**
     * 是否父类进行展示错误信息
     * @return
     */
    protected boolean _onIsShowError(){
        return true;
    }

    /**
     * 业务逻辑异常
     * @param ex
     */
    protected  void _onMvpBusinessError(MvpServerException ex){}

    /**
     *非业务逻辑异常
     * @param ex
     */
    protected  void _onOtherNoError(MvpApiException ex){}




}
