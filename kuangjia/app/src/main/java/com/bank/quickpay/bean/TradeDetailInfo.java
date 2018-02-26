package com.bank.quickpay.bean;

import java.io.Serializable;


public class TradeDetailInfo implements Serializable{

	/**

	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	private String status="";	//交易状态|0                      
	private String signPic="";		//签名                      
	
	private String localDate="";	//交易日期|20140522           
	private String termId="";		//终端号|001917201312200318522
	private String localLogNo="";		//交易流水|737743         
	private String fee="";		//手续费|0                       
	private String amount="";		//总金额|1                   
	private String bizAmount="";		//金额|1                
	private String branchId="";		//机构ID|     
	private String branchName="";//"机构名称|",(商户名称)          
	private String payType="";		//支付方式                    
	private String account="";		//卡号|6227001216440147415  
	private String account2="";		//卡号|6227001216440147415  
	private String merchantId="";		//交易类型编号|0002000002   
	private String localTime="";		//交易时间|141539         
	private String orderId="";		//订单号                     
	private String transName="";		//交易名称|信用支付 
	private String mobileno="";//付款账号|11122255533",
	
	private String deawstatus="";//"deawstatus":"资金动态|11"",
	
	private String longitude="0";//经度
	private String latitude="0";//纬度
	
	private String paytag="";
	
	
	
	
//	public TradeDetailInfo(String status, String signPic, String localDate,
//			String termId, String localLogNo, String fee, String amount,
//			String bizAmount, String branchId, String payType, String account,
//			String merchantId, String localTime, String orderId,
//			String transName) {
//		super();
//		this.status = status;
//		this.signPic = signPic;
//		this.localDate = localDate;
//		this.termId = termId;
//		this.localLogNo = localLogNo;
//		this.fee = fee;
//		this.amount = amount;
//		this.bizAmount = bizAmount;
//		this.branchId = branchId;
//		this.payType = payType;
//		this.account = account;
//		this.merchantId = merchantId;
//		this.localTime = localTime;
//		this.orderId = orderId;
//		this.transName = transName;
//	}
	public TradeDetailInfo() {
		super();
	}
	public String getStatus() {
		return status;
	}
	public String getSignPic() {
		return signPic;
	}
	public String getLocalDate() {
		return localDate;
	}
	public String getTermId() {
		return termId;
	}
	public String getLocalLogNo() {
		return localLogNo;
	}
	public String getFee() {
		return fee;
	}
	public String getAmount() {
		return amount;
	}
	public String getBizAmount() {
		return bizAmount;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getPayType() {
		return payType;
	}
	public String getAccount() {
		return account;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getLocalTime() {
		return localTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getTransName() {
		return transName;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSignPic(String signPic) {
		this.signPic = signPic;
	}
	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public void setLocalLogNo(String localLogNo) {
		this.localLogNo = localLogNo;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getAccount2() {
		return account2;
	}
	public void setAccount2(String account2) {
		this.account2 = account2;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setBizAmount(String bizAmount) {
		this.bizAmount = bizAmount;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getDeawstatus() {
		return deawstatus;
	}
	public void setDeawstatus(String deawstatus) {
		this.deawstatus = deawstatus;
	}
	public String getPaytag() {
		return paytag;
	}
	public void setPaytag(String paytag) {
		this.paytag = paytag;
	}
	
}
