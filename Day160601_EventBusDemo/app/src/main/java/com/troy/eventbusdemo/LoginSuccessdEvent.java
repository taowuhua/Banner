package com.troy.eventbusdemo;

/**
 * Created by Administrator on 2016/6/1.
 */
public class LoginSuccessdEvent {
    private String msg;

    public
    LoginSuccessdEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
