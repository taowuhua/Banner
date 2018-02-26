package com.bank.quickpay.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.login.LoginActivity;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收款
 */
public class CollectionActivity extends BaseActivity {


    @BindView(R.id.impay_paymoney)
    EditText impayPaymoney;
    @BindView(R.id.tv_freerate)
    TextView tv_freerate;
    private int TOORDERPAY=0x0041;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("收款", true, true);
        String free_rate = PreferenceUtil.getInstance(this).getString("fee_rate","");
        if(!TextUtils.isEmpty(free_rate)){
            tv_freerate.setText("费率："+free_rate);
        }
    }
    @OnClick(R.id.tilerightImg)
    public void showHelp() {
        toHtmlMessageAct(AppConfig.SUPPORTBANKSLIMIT,"帮助说明");
    }
    @OnClick(R.id.collection_nextbtn)
    public void collectionNextbtnClick(View view) {
        disabledTimerView(view);
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat("##0.00");
            String textStr = impayPaymoney.getText().toString().trim();
            if (TextUtils.isEmpty(textStr)) {
                LogUtil.showToast(this, "交易金额不能为空");
                return;
            }
            double moneyDouble=   Double.parseDouble(textStr);
            if(moneyDouble<=0){
                LogUtil.showToast(this, "交易金额必须大于零");
                return;
            }
//           String moneyDoubleStr= String.valueOf(moneyDouble);
            if(textStr.indexOf(".")>0&&textStr.length()-textStr.indexOf(".")>3){
                LogUtil.showToast(this, "交易金额单位过小");
                LogUtil.showLog(textStr+"==="+textStr.indexOf("."));
                return;
            }
            String amountLow=PreferenceUtil.getInstance(CollectionActivity.this).getString("amountLow","0");
            try {
              int amountLowInt=  Integer.parseInt(amountLow);
              if(amountLowInt>moneyDouble*100){
                  LogUtil.showToast(this, "最低交易金额"+amountLowInt/100+"元");
                  return;
              }
            }catch (Exception e){

            }

            String  moneyDecimalStr= df.format(moneyDouble);
            Intent intent = new Intent(CollectionActivity.this, PaySelectCardActivity.class);
            intent.putExtra("moneyDoubleStr",moneyDecimalStr);
            startActivityForResult(intent,TOORDERPAY);
        } catch (Exception e) {
            LogUtil.showToast(this, "请正确输入金额!");
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==TOORDERPAY&&resultCode== AppConfig.TASKSUCCESS){
            finish();
        }
    }
}
