package com.ryx.baselib.mvpframe.exception;


/**
 * 封装服务端异常
 */
public class MvpServerException extends RuntimeException {
    private String code;
    private String msg;
    private Object error;

    public MvpServerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MvpServerException(String code, String msg, Object error) {
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "MvpServerException{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}
