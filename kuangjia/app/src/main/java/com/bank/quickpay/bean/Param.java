package com.bank.quickpay.bean;

/**
 * Created by laomao on 16/4/19.
 */
public class Param {
    private String key;
    private String value;
    public Param(String key) {
        super();
        this.key = key;
    }
    public Param(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(String value) {
        this.value = value;
    }
}