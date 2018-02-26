package com.bank.quickpay.bean;

public class CityInfo {
	private String   cityCode;
	private String   cityName;
	public CityInfo(){};
	public CityInfo(String cityCode, String cityName) {
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public String getCityName() {
		return cityName;
	}
}
