package com.bank.quickpay.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laomao on 16/4/27.
 */
public class IconBean {

    private String serviceid = "";
    private String result = "";
    private String desc = "";
    private String mainbg = "";

    private List<IconMsgBean> getIconList = new ArrayList<IconMsgBean>();

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<IconMsgBean> getGetIconList() {
        return getIconList;
    }

    public void setGetIconList(List<IconMsgBean> getIconList) {
        this.getIconList = getIconList;
    }

    public String getMainbg() {
        return mainbg;
    }

    public void setMainbg(String mainbg) {
        this.mainbg = mainbg;
    }

    public static class IconMsgBean implements Serializable {

        private String idx;
        private String id;
        private String name;
        private String res;
        private String flag;
        private String show;
        private String permission;

        public IconMsgBean(String idx, String id, String res, String name, String flag) {
            this.idx = idx;
            this.id = id;
            this.res = res;
            this.name = name;
            this.flag = flag;
        }

        public IconMsgBean(String permission, String show, String idx, String id, String res, String name, String flag) {
            this.permission = permission;
            this.show = show;
            this.flag = flag;
            this.name = name;
            this.id = id;
            this.idx = idx;
            this.res = res;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public IconMsgBean() {
        }

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

    }

}
