
package com.bank.quickpay.bean;

import java.io.Serializable;

public class BankCardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name; // 用户姓名

    private String repaydate = ""; // 还款日期

    // 储值卡信息
    private String bankCity;
    private String remark;
    private String bankProvinceId;
    private String accountNo; // 卡号
    private String bankName;
    private String bankProvince;
    private String bankId;
    private String flagInfo;
    private String bankCityId;
    private String cardIdx;
    private String branchBankId;
    private String branchBankName;
    private String cardHolder; // 判断是否为持卡本人
    private String customerPid; // 获取身份证信息

    private String type;
    private String id;// 卡片id 用于信用卡高级认证的重新认证、删除
    private String creditCardTime;
    private String addFlag;
    private String status;// 信用卡状态 0待审核 1审核通过 2审核失败 3 重新认证 4逻辑删除

    private String rejectReason;

    private String renewFee;// 续费
    private String feeDate;// 到期日

    private String statusDesc;// 审核描述
    private String feeDesc;// 日期描述
    private String cardLastNumber;
    private String branchNumber;

    private String checkStatus; // 检查卡是否被绑定并冻结
    private String mark; // 表示

    private String quick;//快捷支付是否开通0,1
    private String daikou;//代扣是否开通0,1
    private String cardtype;//卡类型01储蓄卡，02境外卡,03信用卡
    private String daifustatus;//
    private String cardstatus;//1为当前卡所属银行有问题暂不可用
    private String cardnote;//当前卡cardstatus为1的时候进行提示的信息

    public String getMark()
    {
        return mark;
    }

    public void setMark(String m)
    {
        mark = m;
    }

    public String getCheckStatus()
    {
        return checkStatus;
    }

    public void setCheckStatus(String status)
    {
        checkStatus = status;
    }

    public String getCardLastNumber()
    {
        return cardLastNumber;
    }

    public void setcardLastNumber(String number)
    {
        cardLastNumber = number;
    }

    public String getBranchNumber()
    {
        return branchNumber;
    }

    public void setBranchNumber(String number)
    {
        branchNumber = number;
    }

    // public String getCardnum() {
    // return cardnum;
    // }
    // public void setCardnum(String cardnum) {
    // this.cardnum = cardnum;
    // }

    public String getCustomerPid()
    {
        return customerPid;
    }

    public void setCustomerPid(String id)
    {
        customerPid = id;
    }

    public String getCardHolder()
    {
        return cardHolder;
    }

    public void setCardHolder(String holder)
    {
        cardHolder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public int getSubBranchid() {
    // return subBranchid;
    // }
    // public void setSubBranchid(int subBranchid) {
    // this.subBranchid = subBranchid;
    // }
    // public String getSubBranchName() {
    // return subBranchName;
    // }
    // public void setSubBranchName(String subBranchName) {
    // this.subBranchName = subBranchName;
    // }
    public String getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(String repaydate) {
        this.repaydate = repaydate;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankProvinceId() {
        return bankProvinceId;
    }

    public void setBankProvinceId(String bankProvinceId) {
        this.bankProvinceId = bankProvinceId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getFlagInfo() {
        return flagInfo;
    }

    public void setFlagInfo(String flagInfo) {
        this.flagInfo = flagInfo;
    }

    public String getBankCityId() {
        return bankCityId;
    }

    public void setBankCityId(String bankCityId) {
        this.bankCityId = bankCityId;
    }

    public String getCardIdx() {
        return cardIdx;
    }

    public void setCardIdx(String cardIdx) {
        this.cardIdx = cardIdx;
    }

    public String getBranchBankId() {
        return branchBankId;
    }

    public void setBranchBankId(String branchBankId) {
        this.branchBankId = branchBankId;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditCardTime() {
        return creditCardTime;
    }

    public void setCreditCardTime(String creditCardTime) {
        this.creditCardTime = creditCardTime;
    }

    public String getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRenewFee() {
        return renewFee;
    }

    public void setRenewFee(String renewFee) {
        this.renewFee = renewFee;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getFeeDesc() {
        return feeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        this.feeDesc = feeDesc;
    }

    public String getQuick() {
        return quick;
    }

    public void setQuick(String quick) {
        this.quick = quick;
    }

    public String getDaikou() {
        return daikou;
    }

    public void setDaikou(String daikou) {
        this.daikou = daikou;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getDaifustatus() {
        return daifustatus;
    }

    public void setDaifustatus(String daifustatus) {
        this.daifustatus = daifustatus;
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus;
    }

    public String getCardnote() {
        return cardnote;
    }

    public void setCardnote(String cardnote) {
        this.cardnote = cardnote;
    }

    @Override
    public String toString() {
        return "BankCardInfo{" +
                "name='" + name + '\'' +
                ", repaydate='" + repaydate + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", remark='" + remark + '\'' +
                ", bankProvinceId='" + bankProvinceId + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankId='" + bankId + '\'' +
                ", flagInfo='" + flagInfo + '\'' +
                ", bankCityId='" + bankCityId + '\'' +
                ", cardIdx='" + cardIdx + '\'' +
                ", branchBankId='" + branchBankId + '\'' +
                ", branchBankName='" + branchBankName + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", customerPid='" + customerPid + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", creditCardTime='" + creditCardTime + '\'' +
                ", addFlag='" + addFlag + '\'' +
                ", status='" + status + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", renewFee='" + renewFee + '\'' +
                ", feeDate='" + feeDate + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", feeDesc='" + feeDesc + '\'' +
                ", cardLastNumber='" + cardLastNumber + '\'' +
                ", branchNumber='" + branchNumber + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", mark='" + mark + '\'' +
                ", quick='" + quick + '\'' +
                ", daikou='" + daikou + '\'' +
                ", cardtype='" + cardtype + '\'' +
                ", daifustatus='" + daifustatus + '\'' +
                ", cardstatus='" + cardstatus + '\'' +
                ", cardnote='" + cardnote + '\'' +
                '}';
    }
}
