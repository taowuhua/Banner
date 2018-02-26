package com.bank.quickpay.bean;

import java.util.List;

/**
 * Created by xucc on 2017/10/30.
 */

public class CarCityInfoBean {


    private List<CitiesBean> cities;
    private String h5url;

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * id : 2514
         * createDate : 1500887766000
         * modifyDate : 1500887766000
         * fullName : 贵州安顺
         * lat : 26.25
         * lng : 105.95
         * name : 安顺
         * treePath : ,2482,
         * zipCode : 5610
         * parent : 2482
         * weId : null
         * weName : null
         * isCharge : 1
         * isSupportQuery : 0
         * isSupportPay : 0
         * bodyLength : null
         * engineLength : null
         * licensePrefixes : null
         * pinyin : null
         * provinceName : null
         * provincePinyin : null
         */

        private String id;
        private String createDate;
        private String modifyDate;
        private String fullName;
        private String lat;
        private String lng;
        private String name;
        private String treePath;
        private String zipCode;
        private String parent;
        private String weId;
        private String weName;
        private String isCharge;
        private String isSupportQuery;
        private String isSupportPay;
        private String bodyLength;
        private String engineLength;
        private String licensePrefixes;
        private String pinyin;
        private String provinceName;
        private String provincePinyin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTreePath() {
            return treePath;
        }

        public void setTreePath(String treePath) {
            this.treePath = treePath;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getWeId() {
            return weId;
        }

        public void setWeId(String weId) {
            this.weId = weId;
        }

        public String getWeName() {
            return weName;
        }

        public void setWeName(String weName) {
            this.weName = weName;
        }

        public String getIsCharge() {
            return isCharge;
        }

        public void setIsCharge(String isCharge) {
            this.isCharge = isCharge;
        }

        public String getIsSupportQuery() {
            return isSupportQuery;
        }

        public void setIsSupportQuery(String isSupportQuery) {
            this.isSupportQuery = isSupportQuery;
        }

        public String getIsSupportPay() {
            return isSupportPay;
        }

        public void setIsSupportPay(String isSupportPay) {
            this.isSupportPay = isSupportPay;
        }

        public String getBodyLength() {
            return bodyLength;
        }

        public void setBodyLength(String bodyLength) {
            this.bodyLength = bodyLength;
        }

        public String getEngineLength() {
            return engineLength;
        }

        public void setEngineLength(String engineLength) {
            this.engineLength = engineLength;
        }

        public String getLicensePrefixes() {
            return licensePrefixes;
        }

        public void setLicensePrefixes(String licensePrefixes) {
            this.licensePrefixes = licensePrefixes;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getProvincePinyin() {
            return provincePinyin;
        }

        public void setProvincePinyin(String provincePinyin) {
            this.provincePinyin = provincePinyin;
        }

        @Override
        public String toString() {
            return "CitiesBean{" +
                    "id='" + id + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", modifyDate='" + modifyDate + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", name='" + name + '\'' +
                    ", treePath='" + treePath + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    ", parent='" + parent + '\'' +
                    ", weId='" + weId + '\'' +
                    ", weName='" + weName + '\'' +
                    ", isCharge='" + isCharge + '\'' +
                    ", isSupportQuery='" + isSupportQuery + '\'' +
                    ", isSupportPay='" + isSupportPay + '\'' +
                    ", bodyLength='" + bodyLength + '\'' +
                    ", engineLength='" + engineLength + '\'' +
                    ", licensePrefixes='" + licensePrefixes + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    ", provinceName='" + provinceName + '\'' +
                    ", provincePinyin='" + provincePinyin + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarCityInfoBean{" +
                "cities=" + cities.toString() +
                ", h5url='" + h5url + '\'' +
                '}';
    }
}
