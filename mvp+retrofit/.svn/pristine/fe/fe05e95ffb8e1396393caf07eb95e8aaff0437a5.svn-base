package com.ryx.baselib.mvpframe.base;

/**
 * 自定义异常类
 */
public class MvpApiException extends Exception {
    private String code;
    private String displayMessage;
    private  Throwable throwable;

    public MvpApiException(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
        this.throwable=throwable;
    }
    public MvpApiException(Throwable throwable) {
        super(throwable);
        this.throwable=throwable;

    }

    public MvpApiException(Throwable throwable,String code, String displayMessage) {
        super(throwable);
        this.throwable=throwable;
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public String getCode() {
        return code;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
