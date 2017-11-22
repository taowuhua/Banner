package com.ryx.bannerutil;

/**
 * Created by RYX on 2017/11/22.
 */

public enum  SingletonEnum {
    /**
     * 1.从Java1.5开始支持;
     * 2.无偿提供序列化机制;
     * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
     */

    instance;

    private String others;

    SingletonEnum() {

    }

    public void method() {
        System.out.println("SingletonEnum");
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
