package com.bank.quickpay.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by XCC on 2017/3/20.
 */

public class CardBeanMap implements Serializable {

    public CardBeanMap(Map<String, String> map) {
        this.map = map;
    }
    public CardBeanMap() {
        super();
    }

    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }


}
