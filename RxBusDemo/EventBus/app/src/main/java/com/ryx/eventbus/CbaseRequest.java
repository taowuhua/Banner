package com.ryx.eventbus;

import java.io.Serializable;

/**
 * Created by RYX on 2017/12/12.
 */

public class CbaseRequest implements Serializable {

    /**
     * sign : 3de2897c9622f44630c7cfc5b2177239
     * appUser : ruishua
     * phone : 15866674053
     * transTime : 20160813141213
     * transLogNo : 000012
     * token : 541A421FA98012C47058AE1AB54DAA41
     * params : 7b2253455256494345223a7b22534552564943455f424f4459223a7b2252455155455354223a7b224c4f414e5f414d54223a2231313131227d7d2c22534552564943455f484541444552223a7b224143515f4944223a223030313330303030222c224348414e4e454c5f4944223a2253554e53222c224d4143223a226d6163222c224f505f4944223a226f704964222c224f5247223a22303030303030303030303031222c22524551554553545f54494d45223a223230313630383133313431323133222c2253455256494345534e223a2259473230313530383235313132313332313233343634222c22534552564943455f4944223a22544e51504c53506279416d745465726d222c225355425f5445524d494e414c5f54595045223a224f5443222c2256455253494f4e5f4944223a223031227d7d7d
     */

    private String sign;
    private String appUser;
    private String phone;
    private String transTime;
    private String transLogNo;
    private String token;
    private String params;
    private String appVersion;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    private String urlType;

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    private String systemVersion;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransLogNo() {
        return transLogNo;
    }

    public void setTransLogNo(String transLogNo) {
        this.transLogNo = transLogNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}

