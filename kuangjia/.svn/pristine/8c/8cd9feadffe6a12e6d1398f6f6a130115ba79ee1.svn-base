package com.bank.quickpay.activity.earningscircle;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.BankCardInfo;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.bank.quickpay.utils.QuickPayAppData;
import com.qtpay.qtjni.QtPayEncode;
import com.rey.material.widget.Button;
import com.ryx.ryxkeylib.listener.EditPwdListener;
import com.ryx.ryxkeylib.service.CustomKeyBoardService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 余额提取
 * Created by xpp on 2017/11/2 0002.
 */

public class UndrawlBalanceActivity extends BaseActivity {

    @BindView(R.id.tv_withdrawal_amount)
    TextView tvWithdrawalAmount;
    @BindView(R.id.tv_withdrawal)
    TextView tvWithdrawal;
    @BindView(R.id.display_tv)
    TextView displayTv;
    @BindView(R.id.impay_paymoney)
    EditText impayPaymoney;
    @BindView(R.id.tv_psw)
    EditText tvPsw;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private String pswdStr, textStr, blanceMoney;
    private double moneyDouble;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_undraw_balance;
    }

    private ArrayList<BankCardInfo> bankcardlist = new ArrayList<BankCardInfo>();// 已经绑定银行卡列表

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitleLayout("余额提取", true, false);
        initData();
        iniRyxKeyWord();


    }

    @OnClick(R.id.tv_withdrawal)
    public void tvwithdrawalClick() {
        impayPaymoney.setText(QuickMoneyEncoder.decodeToyuan(blanceMoney));
    }


    private void initData() {
        blanceMoney = getIntent().getStringExtra("blanceMoney");
        tvWithdrawalAmount.setText(QuickMoneyEncoder.decodeToyuan(blanceMoney));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("温馨提示:提款成功后第二个工作日到结算银行卡中,请及时留意资金动态。");
        String shareFreeMsg = PreferenceUtil.getInstance(UndrawlBalanceActivity.this).getString(AppConfig.FEE, "");
        if (!TextUtils.isEmpty(shareFreeMsg)) {
            String feeMsg = "<font color=\"#ed9f2b\">" + shareFreeMsg + "</font>";
            stringBuffer.append(feeMsg);
            displayTv.setText(Html.fromHtml(stringBuffer.toString()));
        } else {
            displayTv.setText(stringBuffer.toString());
        }

        initQtPatParams();

        qtpayApplication.setValue("BindCardList.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("cardType", "10"));
        httpsPost("BindCardListTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                newinitBankListData(payResult.getData());
            }
        });


    }

    /**
     * 初始化银行卡列表(new)
     *
     * @param banklistJson
     */
    private void newinitBankListData(String banklistJson) {
        try {
            JSONObject jsonObj = new JSONObject(banklistJson);
            if ("0000".equals(jsonObj.getString("code"))) {
                // 解析银行卡信息
                JSONArray banks = jsonObj.getJSONObject("result").getJSONArray("cardlist");
                for (int i = 0; i < banks.length(); i++) {
                    BankCardInfo bankCardInfo = new BankCardInfo();
                    bankCardInfo.setCardIdx(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "cardidx"));
                    bankCardInfo.setBankId(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "bankid"));
                    bankCardInfo.setAccountNo(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "cardno"));
                    bankCardInfo.setBankName(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "bankname"));
                    bankCardInfo.setQuick(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "quick"));
                    bankCardInfo.setDaikou(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "msdk"));
                    bankCardInfo.setDaifustatus(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "daifustatus"));
                    bankCardInfo.setFlagInfo(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "flaginfo"));//1为默认结算卡
                    bankCardInfo.setCardtype(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "cardtype"));
                    bankCardInfo.setBranchBankName(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "branchBankName"));
                    bankCardInfo.setName(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "customername"));
                    bankCardInfo.setCardstatus(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "cardstatus"));
                    bankCardInfo.setCardnote(JsonUtil.getValueFromJSONObject(banks.getJSONObject(i), "cardnote"));
                    bankcardlist.add(bankCardInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_sure)
    public void BtnSureClick(View view) {
        disabledTimerView(view);
        pswdStr = tvPsw.getText().toString().trim();
        textStr = impayPaymoney.getText().toString().trim();
        if (TextUtils.isEmpty(pswdStr)) {
            LogUtil.showToast(UndrawlBalanceActivity.this, "密码不能为空!");
            return;
        }
        if (TextUtils.isEmpty(textStr)) {
            LogUtil.showToast(UndrawlBalanceActivity.this, "金额不能为空!");
            return;
        }
        try {

            moneyDouble = Double.parseDouble(textStr);
            if (moneyDouble <= 0) {
                LogUtil.showToast(this, "付款金额必须大于零");
                return;
            }
            if (textStr.indexOf(".") > 0 && textStr.length() - textStr.indexOf(".") > 3) {
                LogUtil.showToast(this, "付款金额单位过小");
                LogUtil.showLog(textStr + "===" + textStr.indexOf("."));
                return;
            }

            if (bankcardlist.size() == 0) {
                LogUtil.showToast(this, "未获取到结算卡,请绑定结算卡后再进行提取!");
                return;
            }
            qtpayApplication.setValue("JFPalCash.Req");
            Param qtpayCardIdx = new Param("cardIdx", bankcardlist.get(0).getCardIdx());
            Param qtpayCardTag = new Param("cardTag", bankcardlist.get(0).getAccountNo()
                    .substring(bankcardlist.get(0).getAccountNo().length() - 4,
                            bankcardlist.get(0).getAccountNo().length()));

            Param qtpayCashAmt = new Param("cashAmt", Double.valueOf(moneyDouble * 100).intValue() + "");
            Param qtpayPassword = new Param("password",
                    QtPayEncode.encryptUserPwd(pswdStr, QuickPayAppData.getInstance(UndrawlBalanceActivity.this)
                            .getPhone(), false));
            Param qtpayMobileMac = new Param("mobileMac", "0000");
            Param qtpayCashType = new Param("cashType", "2");//1 及时取 0普通
            qtpayAttributeList.add(qtpayApplication);
            qtpayParameterList.add(qtpayCardIdx);
            qtpayParameterList.add(qtpayCardTag);
            qtpayParameterList.add(qtpayCashAmt);
            qtpayParameterList.add(qtpayPassword);
            qtpayParameterList.add(qtpayMobileMac);
            qtpayParameterList.add(qtpayCashType);
            httpsPost("JFPalCashTag", new XmlCallback() {
                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
                    LogUtil.showToast(UndrawlBalanceActivity.this, payResult.getRespDesc());
                    qtpayApplication = new Param("application", "JFPalAcctEnquiry.Req");
                    Param qtpayAcctType = new Param("acctType", "00");
                    qtpayAttributeList.add(qtpayApplication);
                    qtpayParameterList.add(qtpayAcctType);
                    httpsPost("doQueryBalance", new XmlCallback() {
                        @Override
                        public void onTradeSuccess(QuickPayResult payResult) {
                            finish();
//                            blanceMoney = payResult.getAvailableAmt();
//                            tvWithdrawalAmount.setText(QuickMoneyEncoder.decodeToyuan(blanceMoney) + "元");
                        }
                    });
                }
            });
        } catch (Exception e) {
            LogUtil.showToast(this, "请正确输入金额!");
            e.printStackTrace();
            return;
        }


    }

    private void iniRyxKeyWord() {
        CustomKeyBoardService customKeyBoardService = CustomKeyBoardService.registerKeyBoardForEdit(UndrawlBalanceActivity.this, true, tvPsw, new EditPwdListener() {
            @Override
            public void getPwdVal(String realVal, String disVal) {
//            textView1.setText("密码:"+realVal);
                tvPsw.setText(realVal);
            }

            @Override
            public void getPwdDisVal(String disVal, int count) {
                tvPsw.setText(disVal);
            }

            @Override
            public void pwdViewOkbtnLisener() {
                BtnSureClick(null);

            }

        });
        customKeyBoardService.setEditMaxLenth(20);
    }


}
