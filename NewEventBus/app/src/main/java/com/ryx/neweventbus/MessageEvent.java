package com.ryx.neweventbus;

/**
 * Created by RYX on 2017/12/12.
 */

public  class MessageEvent {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageEvent(String name) {
        this.name = name;
    }

    private  String name ;
}
