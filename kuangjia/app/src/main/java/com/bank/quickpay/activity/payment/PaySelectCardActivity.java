package com.bank.quickpay.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.adapter.PaySelectCardAdapter;
import com.bank.quickpay.bean.CardBeanMap;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.MapUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择填写银行卡信息
 */
public class PaySelectCardActivity extends BaseActivity {


    @BindView(R.id.paymoney_tv)
    TextView paymoneyTv;
    @BindView(R.id.payusername_tv)
    TextView payusernameTv;
    @BindView(R.id.tv_debitcardno_txt)
    TextView tvDebitcardnoTxt;
    @BindView(R.id.cardno_et)
    EditText cardnoEt;
    @BindView(R.id.lay_id)
    AutoRelativeLayout layId;
    @BindView(R.id.prompting_layout)
    AutoLinearLayout promptingLayout;

    @BindView(R.id.recyclerviewlayout)
    AutoLinearLayout recyclerviewlayout;

    @BindView(R.id.carlist_recy)
    RecyclerView carlistRecy;
    @BindView(R.id.pay_nextbtn)
    Button payNextbtn;
    private int TOPAYMSGFILLIN=0x0045;
    List<CardBeanMap> cardBeanMapList=new ArrayList<CardBeanMap>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_select_card;
    }
String  moneyDoubleStr="0.00";
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("收款", true, true);
        moneyDoubleStr=  getIntent().getStringExtra("moneyDoubleStr");
        paymoneyTv.setText(moneyDoubleStr);
       String realName= QuickPayAppData.getInstance(this).getRealName();
        payusernameTv.setText(realName);
        initQtPatParams();
        initBankRecyView();
    }

    @OnClick(R.id.tilerightImg)
    public void showHelp() {
        toHtmlMessageAct(AppConfig.SUPPORTBANKSLIMIT,"帮助说明");
    }
    /**
     * 初始化银行卡列表
     */
    private void initBankRecyView() {
        qtpayApplication.setValue("KJFXBindCardList.Req");
        String customerId=QuickPayAppData.getInstance(this).getCustomerId();
        Param customerIdParam=new Param("customerId",customerId);
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(customerIdParam);
        httpsPost("KJFXBindCardListTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                String data=payResult.getData();
                if(TextUtils.isEmpty(data)){
                    promptingLayout.setVisibility(View.GONE);
                    recyclerviewlayout.setVisibility(View.GONE);
                }else{
                    try {
                        JSONArray jsonArray=new JSONArray(data);
                        if(jsonArray.length()>0){
                            promptingLayout.setVisibility(View.VISIBLE);
                            recyclerviewlayout.setVisibility(View.VISIBLE);
                        }else{
                            promptingLayout.setVisibility(View.GONE);
                            recyclerviewlayout.setVisibility(View.GONE);
                        }

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            String bankName= JsonUtil.getValueFromJSONObject(jsonObject,"bankname");
                            String account= JsonUtil.getValueFromJSONObject(jsonObject,"account");
                            String id= JsonUtil.getValueFromJSONObject(jsonObject,"id");
                            String bankid= JsonUtil.getValueFromJSONObject(jsonObject,"bankid");
                            CardBeanMap cardBeanMap=new CardBeanMap();
                            Map map1=new HashMap();
                            map1.put("bankName",bankName+"(尾号"+account.substring(account.length()-4)+")");
                            map1.put("account",account);
                            map1.put("id",id);
                            map1.put("bankLogo", AppConfig.BANKIMG_URL.replace("placeholder",bankid));
                            cardBeanMap.setMap(map1);
                            cardBeanMapList.add(cardBeanMap);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(PaySelectCardActivity.this);
                        layoutManager.setOrientation(1);
                        carlistRecy.setLayoutManager(layoutManager);
                        final PaySelectCardAdapter paySelectCardAdapter=new PaySelectCardAdapter(cardBeanMapList,PaySelectCardActivity.this,R.layout.pay_selectcard_item);
                        carlistRecy.setAdapter(paySelectCardAdapter);
                        paySelectCardAdapter.setOnItemClickListener(new OnListItemClickListener() {
                            @Override
                            public void onItemClick(View view, int i, Object item) {
                                Map<String,String> aditemMap=((CardBeanMap) item).getMap();
                                String account=    MapUtil.get(aditemMap,"account","");
                                cardnoEt.setText(account);
                                cardnoEt.setSelection(account.length());
                                paySelectCardAdapter.setSelectedposition(i);
                                paySelectCardAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @OnClick(R.id.pay_nextbtn)
    public void onViewClicked(View view) {
        disabledTimerView(view);
       final String cardNumber= cardnoEt.getText().toString().trim();
        if(TextUtils.isEmpty(cardNumber)){
            LogUtil.showToast(this,"请正确输入卡号!");
            return;
        }
        String id="";
        if(cardBeanMapList.size()>0){
            for (int i=0;i<cardBeanMapList.size();i++){
               Map<String,String> beanMap= cardBeanMapList.get(i).getMap();
              String account= MapUtil.getString(beanMap,"account","");
              if(cardNumber.equals(account)){
                  id= MapUtil.getString(beanMap,"id","");
                  break;
              }
            }
        }
        qtpayApplication.setValue("KJFXQuickPaymentBindCardPrepare.Req");
        qtpayAttributeList.add(qtpayApplication);
        if(TextUtils.isEmpty(id)){
            qtpayParameterList.add(new Param("account",cardNumber));
        }else{
            qtpayParameterList.add(new Param("id",id));
        }
        qtpayParameterList.add(new Param("amount",String.valueOf(((int) (Double.parseDouble(moneyDoubleStr)*100)))));
        httpsPost("QuickPaymentBindCardPrepareTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                String data=payResult.getData();
                if(!TextUtils.isEmpty(data)){
                    Intent intent=   new Intent(PaySelectCardActivity.this,PayMsgFillinActivity.class);
                    intent.putExtra("data",data);
                    intent.putExtra("money",moneyDoubleStr);
                    intent.putExtra("account",cardNumber);
                    startActivityForResult(intent,TOPAYMSGFILLIN);
                }
            }
        });
    }
}
