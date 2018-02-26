package com.bank.quickpay.activity.payment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.dialog.VerificationCodeDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.interfaces.VerificationCodeDialogListener;
import com.bank.quickpay.utils.BanksUtils;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.ExpiryFilter;
import com.bank.quickpay.utils.GlideUtils;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.StringUnit;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付信息填写补充页面
 */
public class PayMsgFillinActivity extends BaseActivity {


    @BindView(R.id.pay_msg_ordermoney_id)
    TextView payMsgOrdermoneyId;
    @BindView(R.id.pay_msg_goodsname_id)
    TextView payMsgGoodsnameId;
    @BindView(R.id.pay_msg_ordertime_id)
    TextView payMsgOrdertimeId;
    @BindView(R.id.pay_msg_ordertime_tv)
    TextView pay_msg_ordertimeTv;
    @BindView(R.id.pay_msg_ordernumber_id)
    TextView payMsgOrdernumberId;
    @BindView(R.id.pay_msg_fkcard_id)
    TextView payMsgFkcardId;
    @BindView(R.id.bankImg_iv)
    ImageView bankImgIv;
    @BindView(R.id.banknumber_tv)
    TextView banknumberTv;
    @BindView(R.id.cardTypeName_tv)
    TextView cardTypeNameTv;
    @BindView(R.id.tv_cvn_txt)
    TextView tvCvnTxt;
    @BindView(R.id.edt_cvn)
    EditText edtCvn;
    @BindView(R.id.lay_cvn)
    AutoRelativeLayout layCvn;
    @BindView(R.id.tv_validate_txt)
    TextView tvValidateTxt;
    @BindView(R.id.edt_validate)
    EditText edtValidate;
    @BindView(R.id.lay_date)
    AutoRelativeLayout layDate;
    @BindView(R.id.tv_mobile_txt)
    TextView tvMobileTxt;
    @BindView(R.id.edt_mobile)
    EditText edtMobile;
    @BindView(R.id.lay_mobile)
    AutoRelativeLayout layMobile;
    @BindView(R.id.code_tv)
    TextView codeTv;
    @BindView(R.id.orderId_tv)
    TextView orderIdTv;
    @BindView(R.id.ordermoney_tv)
    TextView ordermoneyTv;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.smscode_layou)
    AutoRelativeLayout smscodeLayou;
    @BindView(R.id.pay_nextbtn)
    Button payNextbtn;
    InputFilter[] filters = {new ExpiryFilter()};
    private int month;
    private int year;
    private boolean fullLength = false;
    private  int TOBANKHTML=0x0051;
    private  int TOPAYRESULTPAG=0x0052;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_msg_fillin;
    }
private String nextStr,need_cvnStr,need_phonenumStr,amountStr,need_smsStr,need_dateStr,order_idStr,account,money;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("收款", true, false);
        intView();
        initData();
    }
    private void intView() {
      String data=  getIntent().getStringExtra("data");
       money=  getIntent().getStringExtra("money");
      account=  getIntent().getStringExtra("account");
        try {
//  <![CDATA[{"amount":"100","need_cvn":"","need_phonenum":"1","need_sms":"0","next":"open","need_date":"1","order_id":"1711075907230643"}]]>
//            {"cardtype":"03","amount":"1","need_cvn":"0","need_phonenum":"0","bankid":"304","need_sms":"0","next":"pay","need_date":"0","order_id":"1711084181615193"}
            JSONObject jsonObject=new JSONObject(data);
             nextStr=   JsonUtil.getValueFromJSONObject(jsonObject,"next");
             need_cvnStr=   JsonUtil.getValueFromJSONObject(jsonObject,"need_cvn");
             need_phonenumStr=   JsonUtil.getValueFromJSONObject(jsonObject,"need_phonenum");
             amountStr=   JsonUtil.getValueFromJSONObject(jsonObject,"amount");
             need_smsStr=   JsonUtil.getValueFromJSONObject(jsonObject,"need_sms");
             need_dateStr=   JsonUtil.getValueFromJSONObject(jsonObject,"need_date");
             order_idStr=   JsonUtil.getValueFromJSONObject(jsonObject,"order_id");
           String orderdate=   JsonUtil.getValueFromJSONObject(jsonObject,"orderdate");
          String  cardtype=   JsonUtil.getValueFromJSONObject(jsonObject,"cardtype");
          String  bankid=   JsonUtil.getValueFromJSONObject(jsonObject,"bankid");
            String imgurl=   AppConfig.BANKIMG_URL.replace("placeholder",bankid);
            pay_msg_ordertimeTv.setText(DateUtil.StrToDateStr(orderdate,"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            GlideUtils.getInstance().load(PayMsgFillinActivity.this,imgurl,bankImgIv);
            banknumberTv.setText(StringUnit.cardJiaMi(account));
            if("01".equals(cardtype)){
                cardTypeNameTv.setText("(储蓄卡)");
            }else if("03".equals(cardtype)){
                cardTypeNameTv.setText("(信用卡)");
            }else{
                cardTypeNameTv.setText("(未知)");
            }
            orderIdTv.setText(order_idStr);
            ordermoneyTv.setText(money);
            if("open".equals(nextStr)){
                //开通快捷流程
                if("1".equals(need_cvnStr)){
                    layCvn.setVisibility(View.VISIBLE);
                }else{
                    layCvn.setVisibility(View.GONE);
                }

                if("1".equals(need_dateStr)){
                    layDate.setVisibility(View.VISIBLE);
                }else{
                    layDate.setVisibility(View.GONE);
                }

                if("1".equals(need_phonenumStr)){
                    layMobile.setVisibility(View.VISIBLE);
                }else {
                    layMobile.setVisibility(View.GONE);
                }
                if("1".equals(need_smsStr)){
                    smscodeLayou.setVisibility(View.VISIBLE);
                }else {
                    smscodeLayou.setVisibility(View.GONE);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @OnClick(R.id.pay_nextbtn)
    public void onViewClicked(View view) {
        disabledTimerView(view);
        if("pay".equals(nextStr)){
            //直接调用支付接口
            KJFXPayOrder(order_idStr);
        }else{
            String cvn2Str= edtCvn.getText().toString().trim();
            String edtValidateStr= edtValidate.getText().toString().trim();
            final String edtMobileStr= edtMobile.getText().toString().trim();
            String etSmscodeStr= etSmscode.getText().toString().trim();
            qtpayApplication.setValue("KJFXOpenKJZF.Req");
            qtpayAttributeList.add(qtpayApplication);

            if("1".equals(need_cvnStr)){
                if(TextUtils.isEmpty(cvn2Str)){
                    LogUtil.showToast(PayMsgFillinActivity.this,"请输入CVN2码!");
                    return;
                }else{
                    qtpayParameterList.add(new Param("cvn2",cvn2Str));
                }
            }
            if("1".equals(need_dateStr)){
                if(TextUtils.isEmpty(edtValidateStr)){
                    LogUtil.showToast(PayMsgFillinActivity.this,"请输入有效期!");
                    return;
                }else{
                    qtpayParameterList.add(new Param("validDate",edtValidateStr));
                }
            }

            if("1".equals(need_phonenumStr)){
                if(TextUtils.isEmpty(edtMobileStr)){
                    LogUtil.showToast(PayMsgFillinActivity.this,"请输入银行卡预留手机号!");
                    return;
                }else{
                    qtpayParameterList.add(new Param("phoneNum",edtMobileStr));
                }
            }
            if("1".equals(need_smsStr)){
                if(TextUtils.isEmpty(etSmscodeStr)){
                    LogUtil.showToast(PayMsgFillinActivity.this,"请输入验证码!");
                    return;
                }else{
                    qtpayParameterList.add(new Param("smsCode",etSmscodeStr));
                }
            }

            qtpayParameterList.add(new Param("account",account));
            httpsPost("KJFXOpenKJZFTag", new XmlCallback() {
                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
//                {"id":"151005623114500125","neet_sms":"1"}]
                    String data=  payResult.getData();
                    try {
                        JSONObject jsonObject=new JSONObject(data);
                        String neet_sms=   JsonUtil.getValueFromJSONObject(jsonObject,"neet_sms");
                        final String id=   JsonUtil.getValueFromJSONObject(jsonObject,"id");
                        if("1".equals(neet_sms)){
                            showVerificationCodeDialog(6,"短信已经发送至"+edtMobileStr+",请注意查收!",new VerificationCodeDialogListener() {
                                @Override
                                public void onCodeInputOked(final VerificationCodeDialog verificationCodeDialog, final String smsCode) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            qtpayApplication.setValue("KJFXOpenKJZFSMS.Req");
                                            qtpayAttributeList.add(qtpayApplication);
                                            qtpayParameterList.add(new Param("id",id));
                                            qtpayParameterList.add(new Param("smsCode",smsCode));
                                            httpsPost("KJFXOpenKJZFSMSTag", new XmlCallback() {
                                                @Override
                                                public void onTradeSuccess(QuickPayResult payResult) {
                                                    verificationCodeDialog.dismiss();
                                                    KJFXPayOrder(order_idStr);
                                                }
                                            });

                                        }
                                    });

                                }
                                @Override
                                public void onaginBtnClicked(VerificationCodeDialog verificationCodeDialog) {
                                    LogUtil.showToast(PayMsgFillinActivity.this,"重发");

                                }

                                @Override
                                public void onCloseClicked(VerificationCodeDialog verificationCodeDialog) {
                                    verificationCodeDialog.dismiss();
                                    setResult(AppConfig.CLOSE_ALL);
                                    finish();

                                }
                            });
                        }else{
                            //无需短信直接调用支付接口
                            KJFXPayOrder(order_idStr);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
    /**
     * 支付请求
     * @param orderId
     */
    private void KJFXPayOrder(final String orderId) {
        qtpayApplication.setValue("KJFXPayOrder.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("orderId",orderId));
        httpsPost("KJFXPayOrderTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
               String data= payResult.getData();
               if(!TextUtils.isEmpty(data)){
                   try {
                       JSONObject jsonObject=new JSONObject(data);
                    String htmlContent=   JsonUtil.getValueFromJSONObject(jsonObject,"html");
                    String neet_sms=   JsonUtil.getValueFromJSONObject(jsonObject,"neet_sms");
                    String phonenum=   JsonUtil.getValueFromJSONObject(jsonObject,"phonenum");
                    if("1".equals(neet_sms)){
                        //需要短信
                        showVerificationCodeDialog(4,"短信已经发送至"+phonenum+",请注意查收!",new VerificationCodeDialogListener() {
                            @Override
                            public void onCodeInputOked(final VerificationCodeDialog verificationCodeDialog, final String smsCode) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        qtpayApplication.setValue("KJFXPayOrderSMS.Req");
                                        qtpayAttributeList.add(qtpayApplication);
                                        qtpayParameterList.add(new Param("orderId",orderId));
                                        qtpayParameterList.add(new Param("smsCode",smsCode));
                                        httpsPost("KJFXPayOrderSMSTag", new XmlCallback() {
                                            @Override
                                            public void onTradeSuccess(QuickPayResult payResult) {
                                                verificationCodeDialog.dismiss();
                                                Intent intent=new Intent(PayMsgFillinActivity.this,PaySuccessActivity.class);
                                                intent.putExtra("orderId",orderId);
                                                intent.putExtra("amount",money);
                                                startActivityForResult(intent,TOPAYRESULTPAG);

                                            }

                                            @Override
                                            public void onOtherState(String rescode, String resDesc) {
//                                                super.onOtherState(rescode, resDesc);
                                                verificationCodeDialog.dismiss();
                                                if("9101".equals(rescode)){
                                                    //支付完成等待轮询结果
                                                    Intent intent=new Intent(PayMsgFillinActivity.this,PaySuccessActivity.class);
                                                    intent.putExtra("orderId",orderId);
                                                    intent.putExtra("amount",money);
                                                    startActivityForResult(intent,TOPAYRESULTPAG);
                                                }else{
                                                    //验证码错误及重复验证情况
                                                    QuickSimpleConfirmDialog quickSimpleConfirmDialog=new QuickSimpleConfirmDialog(PayMsgFillinActivity.this);
                                                    quickSimpleConfirmDialog.setOnClickListen(new ConFirmDialogListener() {
                                                        @Override
                                                        public void onPositiveActionClicked(QuickSimpleConfirmDialog quickSimpleConfirmDialog) {
                                                            quickSimpleConfirmDialog.dismiss();
                                                            setResult(AppConfig.CLOSE_ALL);
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onNegativeActionClicked(QuickSimpleConfirmDialog quickSimpleConfirmDialog) {

                                                        }
                                                    });
                                                    quickSimpleConfirmDialog.show();
                                                    quickSimpleConfirmDialog.setContent(resDesc);
                                                    quickSimpleConfirmDialog.setOkbtnText("确认");
                                                    quickSimpleConfirmDialog.setOnlyokLinearlayout();
                                                }

                                            }
                                        });
                                    }
                                });

                            }
                            @Override
                            public void onaginBtnClicked(VerificationCodeDialog verificationCodeDialog) {
                                LogUtil.showToast(PayMsgFillinActivity.this,"重发");

                            }

                            @Override
                            public void onCloseClicked(VerificationCodeDialog verificationCodeDialog) {
                                verificationCodeDialog.dismiss();
                                setResult(AppConfig.CLOSE_ALL);
                                finish();
                            }
                        });
                    }else{
                        //加载html片段
                        Intent intent=new Intent(PayMsgFillinActivity.this,BankHtmlActivity.class);
                        intent.putExtra("htmlContent",htmlContent);
                        intent.putExtra("orderId",orderId);
                        intent.putExtra("amount",money);
                        startActivityForResult(intent,TOBANKHTML);
                    }

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
            }
            @Override
            public void onOtherState(String rescode, String resDesc) {
                super.onOtherState(rescode, resDesc);
            }
        });

    }

    private void initData() {
        edtValidate.setInputType(InputType.TYPE_CLASS_PHONE);
        edtValidate.setFilters(filters);
        String  expirydate=edtValidate.getText().toString();
        if (!TextUtils.isEmpty(expirydate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMM");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMyy");
            simpleDateFormat.setLenient(false);
            try {
                Date expiry = simpleDateFormat.parse(expirydate);
                month = 0;
                year = 0;
                edtValidate.setText(simpleDateFormat2.format(expiry));
                fullLength = (expirydate.length() >= 4);
                edtValidate.setEnabled(false);
                edtValidate.setFocusable(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        edtValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                month = 0;
                year = 0;
                fullLength = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fullLength = (s.length() >= 5);
//                changeButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String datestr = s.toString();
                if (datestr == null) {
                    return;
                }
                Date expiry = getDateForString(datestr);
                if (expiry == null) {
                    return;
                }
                month = expiry.getMonth() + 1;
                year = expiry.getYear();
                if (year < 1900) {
                    year += 1900;
                }

            }
        });
//        changeButtonState();
    }
    public static Date getDateForString(String dateString) {
        String digitsOnly = getDigitsOnlyString(dateString);
        SimpleDateFormat validDate = getDateFormatForLength(digitsOnly.length());
        if (validDate != null) {
            try {
                validDate.setLenient(false);
                Date date = validDate.parse(digitsOnly);
                return date;
            } catch (ParseException pe) {
                return null;
            }
        }
        return null;
    }
    public static String getDigitsOnlyString(String numString) {
        StringBuilder sb = new StringBuilder();
        for (char c : numString.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static SimpleDateFormat getDateFormatForLength(int len) {
        if (len == 4) {
            return new SimpleDateFormat("MMyy");
        } else if (len == 6) {
            return new SimpleDateFormat("MMyyyy");
        } else {
            return null;
        }
    }

    public VerificationCodeDialog showVerificationCodeDialog(int msgcount,String phoneNumberMsg,VerificationCodeDialogListener verificationCodeDialogListener) {
        VerificationCodeDialog verificationCodeDialog=new VerificationCodeDialog(msgcount,PayMsgFillinActivity.this);
        verificationCodeDialog.show();
        verificationCodeDialog.setCanceledOnTouchOutside(false);
        verificationCodeDialog.setClickListen(verificationCodeDialogListener);
        verificationCodeDialog.setphoneNumberMsg(phoneNumberMsg);
        verificationCodeDialog.setCancelable(false);
        return verificationCodeDialog;
    }


    @OnClick(R.id.pay_msg_ordertime_id)
    public void  testDialog(){
//        showVerificationCodeDialog("短信已经发送至18789898909,请注意查收!", new VerificationCodeDialogListener() {
//            @Override
//            public void onCodeInputOked(VerificationCodeDialog verificationCodeDialog, String code) {
//                Intent intent=new Intent(PayMsgFillinActivity.this,PaySuccessActivity.class);
//                intent.putExtra("orderId","1711082301064905");
//                intent.putExtra("amount",money);
//                startActivityForResult(intent,TOPAYRESULTPAG);
//            }
//
//            @Override
//            public void onaginBtnClicked(VerificationCodeDialog verificationCodeDialog) {
//
//            }
//            @Override
//            public void onCloseClicked(VerificationCodeDialog verificationCodeDialog) {
//                setResult(AppConfig.CLOSE_ALL);
//                finish();
//            }
//                }
//
//        );
//        //加载html片段
//        Intent intent=new Intent(PayMsgFillinActivity.this,BankHtmlActivity.class);
//        intent.putExtra("htmlContent","");
//        intent.putExtra("orderId","1711151784784833");
//        intent.putExtra("amount",money);
//        startActivityForResult(intent,TOBANKHTML);
    }
}
