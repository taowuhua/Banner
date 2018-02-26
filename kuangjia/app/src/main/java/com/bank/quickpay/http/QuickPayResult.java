package com.bank.quickpay.http;

/**
 * Created by laomao on 16/4/20.
 */

public class QuickPayResult {

    private String application;
    private String mobileSerialNum;
    private String osType;
    private String version;
    private String appUser;
    private String respCode;
    private String respDesc;
    private String sign;		//

    private String transLogNo;
    private String transDate;
    private String transTime;

    private String certPid;
    private String certType;
//	private String authenFlag;

    private String customerId;
    private String userType;
    private String email;
    private String userName;
    private String merchantName;//完善信息后商家名字
    private String realName;
    private String customerName;
    private String mobileNo;
    private String appType;
    private String referrerMobileNo;
    private String mobileMac;

    private String data;
    private String dataType;
    private String orderId;	// 系统返回的订单号
    private String orderDesc;	//
    private String realAmt;	//
    private String orderAmt;	//
    private String payTool;	//
    private String productId;

    private String cashAvailableAmt;
    private String availableAmt;


    private String printInfo;

    private int authenFlag;
    private String token;
    private String merchant_status;

    private String balance;//卡余额
    private String bankName;//添加信用卡后 新增返回值
    private String bankId;//添加信用卡后 新增返回值


    private String icData;//ic回写数据

    private String tagDesc;

    private String tradeDesc;

    private String laslogininfo;

    private String payStatus;
    private String oldText;

    private String authKey;
    private String desc;
    private String allcount;
    private String dayTradeAmt;
    private String VerifiedSwitch;
    private int kjzf_touch_pay;
    private String bean_status_desc;
    private String feeFixed;
    private String feeRate;
    private String amountLow;

    public String getFeeFixed() {
        return feeFixed;
    }

    public void setFeeFixed(String feeFixed) {
        this.feeFixed = feeFixed;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getAmountLow() {
        return amountLow;
    }

    public void setAmountLow(String amountLow) {
        this.amountLow = amountLow;
    }

    public int getQuickPaymentSwitch() {
        return QuickPaymentSwitch;
    }

    public void setQuickPaymentSwitch(int quickPaymentSwitch) {
        QuickPaymentSwitch = quickPaymentSwitch;
    }

    private int QuickPaymentSwitch;
    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge;
    }

    private String isMerge;
    /***
     * added for credit_manager
     * 王海军
     * 2015.6.19
     */
    private String cardNo;  //卡号
    private String cardHolder;  //本人还是他人

    public void setCardNo(String no)
    {
        cardNo = no;
    }

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardHolder(String holder)
    {
        cardHolder = holder;
    }

    public String getCardHolder()
    {
        return cardHolder;
    }

    /***********信用卡接口添加完毕***********/

    private String dtOrct;

    private String memberId;
    private String keyLogNo;


    public String getKeyLogNo() {
        return keyLogNo;
    }

    public void setKeyLogNo(String keyLogNo) {
        this.keyLogNo = keyLogNo;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String id)
    {
        memberId = id;
    }

    public String getDtOrct()
    {
        return dtOrct;
    }

    public void setDtOrct(String flag)
    {
        dtOrct = flag;
    }

    public String getAuthKey() {
        return authKey;
    }
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    public String getApplication() {
        return application;
    }
    public String getMobileSerialNum() {
        return mobileSerialNum;
    }
    public String getOsType() {
        return osType;
    }
    public String getVersion() {
        return version;
    }
    public String getAppUser() {
        return appUser;
    }
    public String getRespCode() {
        return respCode;
    }
    public String getRespDesc() {
        return respDesc;
    }
    public String getSign() {
        return sign;
    }
    public String getTransLogNo() {
        return transLogNo;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getTransDate() {
        return transDate;
    }
    public String getTransTime() {
        return transTime;
    }
    public String getCertPid() {
        return certPid;
    }
    public String getCertType() {
        return certType;
    }
    public String getUserType() {
        return userType;
    }
    public String getEmail() {
        return email;
    }
    public String getUserName() {
        return userName;
    }
    public String getRealName() {
        return realName;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public String getAppType() {
        return appType;
    }
    public String getReferrerMobileNo() {
        return referrerMobileNo;
    }
    public String getMobileMac() {
        return mobileMac;
    }
    public String getData() {
        return data;
    }
    public String getDataType() {
        return dataType;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getOrderDesc() {
        return orderDesc;
    }
    public String getBankName() {
        return bankName;
    }
    public String getBankId() {
        return bankId;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    public String getRealAmt() {
        return realAmt;
    }
    public String getPayTool() {
        return payTool;
    }
    public int getAuthenFlag() {
        return authenFlag;
    }
    public String getToken() {
        return token;
    }
    public String getBalance() {
        return balance;
    }
    public String getCashAvailableAmt() {
        return cashAvailableAmt;
    }
    public String getAvailableAmt() {
        return availableAmt;
    }
    public void setApplication(String application) {
        this.application = application;
    }
    public void setMobileSerialNum(String mobileSerialNum) {
        this.mobileSerialNum = mobileSerialNum;
    }
    public void setOsType(String osType) {
        this.osType = osType;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public void setTransLogNo(String transLogNo) {
        this.transLogNo = transLogNo;
    }
    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }
    public void setCertPid(String certPid) {
        this.certPid = certPid;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public void setAppType(String appType) {
        this.appType = appType;
    }
    public void setReferrerMobileNo(String referrerMobileNo) {
        this.referrerMobileNo = referrerMobileNo;
    }
    public void setMobileMac(String mobileMac) {
        this.mobileMac = mobileMac;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
    public void setRealAmt(String realAmt) {
        this.realAmt = realAmt;
    }
    public void setPayTool(String payTool) {
        this.payTool = payTool;
    }
    public void setAuthenFlag(int authenFlag) {
        this.authenFlag = authenFlag;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public void setCashAvailableAmt(String cashAvailableAmt) {
        this.cashAvailableAmt = cashAvailableAmt;
    }
    public void setAvailableAmt(String availableAmt) {
        this.availableAmt = availableAmt;
    }
    public String getPrintInfo() {
        return printInfo;
    }
    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getIcData() {
        return icData;
    }
    public void setIcData(String icData) {
        this.icData = icData;
    }
    public String getTagDesc() {
        return tagDesc;
    }
    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }
    public String getTradeDesc() {
        return tradeDesc;
    }
    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }
    public String getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getOldText() {
        return oldText;
    }
    public void setOldText(String oldText) {
        this.oldText = oldText;
    }
    public String getDayTradeAmt() {
        return dayTradeAmt;
    }
    public void setDayTradeAmt(String dayTradeAmt) {
        this.dayTradeAmt = dayTradeAmt;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMerchant_status() {
        return merchant_status;
    }

    public void setMerchant_status(String merchant_status) {
        this.merchant_status = merchant_status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLaslogininfo() {
        return laslogininfo;
    }

    public void setLaslogininfo(String laslogininfo) {
        this.laslogininfo = laslogininfo;
    }

    public String getVerifiedSwitch() {
        return VerifiedSwitch;
    }

    public void setVerifiedSwitch(String verifiedSwitch) {
        VerifiedSwitch = verifiedSwitch;
    }

    public int getKjzf_touch_pay() {
        return kjzf_touch_pay;
    }

    public void setKjzf_touch_pay(int kjzf_touch_pay) {
        this.kjzf_touch_pay = kjzf_touch_pay;
    }

    public String getBean_status_desc() {
        return bean_status_desc;
    }

    public void setBean_status_desc(String bean_status_desc) {
        this.bean_status_desc = bean_status_desc;
    }
}
