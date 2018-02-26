package com.bank.quickpay.activity.mymsg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.IntentUtil;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.rey.material.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定卡卡号校验
 */
public class CardBindAddCardNumberActivity extends BaseActivity {


    private static final int ADDCARDINFO =0x0031 ;
    @BindView(R.id.checkcardnumber_nextbtn)
    Button checkcardnumberNextbtn;
    @BindView(R.id.cardno_et)
    EditText cardnoEt;

    private String cardIdx;//绑定卡编号
    private String cardNo;//绑定卡号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_bind_add_card_number;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("绑定收款卡",true,true);
        onRightImgViewToHtmlMsgAct(AppConfig.BINDSETTLEMENTCARD,"支持卡说明");
        cardIdx = IntentUtil.getStringExtra(getIntent(),"cardIdx");
        cardNo = IntentUtil.getStringExtra(getIntent(),"cardNo");
        if(!TextUtils.isEmpty(cardNo)){
            cardnoEt.setText(cardNo);
            cardnoEt.setSelection(cardNo.length());
        }
    }

    private void queryCardType() {
        qtpayApplication = new Param("application", "CardType.Req");
        Param qtpayCardNo = new Param("cardNo");
        qtpayCardNo.setValue(cardnoEt.getText().toString().trim());
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayCardNo);
        httpsPost("CardType", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                try {
                    JSONObject jsonObject = new JSONObject(payResult.getData());
                   String cardType = JsonUtil.getValueFromJSONObject(jsonObject,"cardtype");
                    String shortBankName = JsonUtil.getValueFromJSONObject(jsonObject,"bankname").replace("\n", "");
                    //三位数（bankId字段取值hissuers字段）
                    String bankId = JsonUtil.getValueFromJSONObject(jsonObject,"hissuers");
                    //新增字段
                    //发卡行总行代码
                    String hissuers = JsonUtil.getValueFromJSONObject(jsonObject,"hissuers");
                    //是否让客户选择支行信息 0不选1是选
                    String needbranch = JsonUtil.getValueFromJSONObject(jsonObject,"needbranch");
                    String branchid2 = JsonUtil.getValueFromJSONObject(jsonObject,"branchid2");

                    Intent intent = null;
                    if ("01".equals(cardType)) {//借记卡
//                        cardTypeTxt = "储蓄卡";
                        intent = new Intent(CardBindAddCardNumberActivity.this, CardBindActivity.class);
                        intent.putExtra("hissuers", hissuers);
                        intent.putExtra("branchid2", branchid2);
                        intent.putExtra("needbranch", needbranch);
                        intent.putExtra("bankid", bankId);
                        intent.putExtra("bankname", shortBankName);
                        intent.putExtra("cardtype", cardType);
                        intent.putExtra("cardIdx",cardIdx);
                        intent.putExtra("cardnumber", cardnoEt.getText().toString().trim());
                        startActivityForResult(intent, ADDCARDINFO);
                    } else {
                        LogUtil.showToast(CardBindAddCardNumberActivity.this, "仅支持绑定储蓄卡!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.showToast(CardBindAddCardNumberActivity.this, "数据解析异常！");
                }
            }
        });
    }
    @OnClick(R.id.checkcardnumber_nextbtn)
    public void onViewClicked() {
        String cardNo = cardnoEt.getText().toString().trim();
        if (!(cardNo.length() >= 14)) {
            LogUtil.showToast(this, getResources().getString(R.string.card_digit_error));
        } else {
            queryCardType();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(ADDCARDINFO==requestCode&&resultCode== AppConfig.TASKSUCCESS){
            setResult(AppConfig.TASKSUCCESS);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
