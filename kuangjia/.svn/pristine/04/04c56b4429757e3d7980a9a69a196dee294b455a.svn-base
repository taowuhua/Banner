package com.bank.quickpay.bean;

import java.io.Serializable;

public class MsgInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String  noticeCode;
	String  title ;
	String  time ;
	String  content ;
	boolean isReaded;
	String popup;//1是弹出，0是不弹出
	String xdtype;//是否小贷长弹出消息

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	String activeTime;
	String noticeType;
	String readFlag;
	public MsgInfo(){}
	public MsgInfo(String  noticeCode, String title, String time, String content) {
		super();
		this.noticeCode = noticeCode;
		this.title = title;
		this.time = time;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}
	public boolean isReaded() {
		return isReaded;
	}
	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getXdtype() {
		return xdtype;
	}

	public void setXdtype(String xdtype) {
		this.xdtype = xdtype;
	}

	@Override
	public String toString() {
		return "MsgInfo{" +
				"noticeCode='" + noticeCode + '\'' +
				", title='" + title + '\'' +
				", time='" + time + '\'' +
				", isReaded=" + isReaded +
				", popup='" + popup + '\'' +
				", xdtype='" + xdtype + '\'' +
				", activeTime='" + activeTime + '\'' +
				", noticeType='" + noticeType + '\'' +
				", readFlag='" + readFlag + '\'' +
				'}';
	}
}
