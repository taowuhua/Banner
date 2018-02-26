package com.bank.quickpay.activity.mymsg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.psw.ChangePassWord;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.BanksUtils;
import com.bank.quickpay.utils.CNummberUtil;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.bank.quickpay.utils.QuickPayAppData;
import com.bank.quickpay.utils.StringUnit;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的信息
 */
public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.info_state_tv)
    TextView info_state_tv;
    @BindView(R.id.phonenumber_tv)
    TextView phonenumberTv;
    @BindView(R.id.rvamount_tv)
    TextView rvamount_tv;
    @BindView(R.id.jsk_bankimg_iv)
    ImageView jsk_bankimg_iv;

    @BindView(R.id.cardnumber_tv)
    TextView cardnumber_tv;
    @BindView(R.id.tilerighttext)
    TextView tilerighttext;
    @BindView(R.id.tv_currentreerate)
    TextView tvCurrentreerate;
    @BindView(R.id.iv_understandmore)
    ImageView iv_understandmore;
    @BindView(R.id.nodatalayout)
    ImageView nodatalayout;
    @BindView(R.id.all_layout)
    AutoLinearLayout all_layout;


    private Param qtpayAcctType;
    private Param qtpayTransType;

    private String authFlag;//认证状态
    private String cardIdx;//绑定卡编号
    private String amount;//今日收款
    private String bankId;//银行行号
    private String cardNo;//绑定卡号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info_msg;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("", true, false);
        tilerighttext.setText("退出");
        tilerighttext.setVisibility(View.VISIBLE);
        showAuthState();
        initQtPatParams();
        getMyMsg();
    }

    @OnClick(R.id.tilerighttext)
    public void tilerighttextClick() {
        doExit();
        toAgainLogin(MyInfoActivity.this, AppConfig.TOLOGINACT, true);
    }

    public void getMyMsg() {
        qtpayApplication = new Param("application", "GetMyMessage.Req");
        qtpayAttributeList.add(qtpayApplication);
        httpsPost("GetMyMessage.Req", new XmlCallback() {
                    @Override
                    public void onTradeSuccess(QuickPayResult qtpayResult) {
                        if (qtpayResult.getData() != null) {
                            nodatalayout.setVisibility(View.GONE);
                            all_layout.setVisibility(View.VISIBLE);
                            String jsonData = qtpayResult.getData();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(jsonData);
                                authFlag = JsonUtil.getValueFromJSONObject(jsonObject, "customertag");
                                QuickPayAppData.getInstance(MyInfoActivity.this)
                                        .setAuthenFlag(CNummberUtil.parseInt(authFlag,
                                                QuickPayAppData.getInstance(MyInfoActivity.this).getAuthenFlag()));
                                String userName = JsonUtil.getValueFromJSONObject(jsonObject, "username");
                                QuickPayAppData.getInstance(MyInfoActivity.this).setRealName(userName);
                                cardIdx = JsonUtil.getValueFromJSONObject(jsonObject, "cardidx");
                                amount = JsonUtil.getValueFromJSONObject(jsonObject, "amount");
                                rvamount_tv.setText(QuickMoneyEncoder.decodeFormat(amount));
                                bankId = JsonUtil.getValueFromJSONObject(jsonObject, "bankid");
                                cardNo = JsonUtil.getValueFromJSONObject(jsonObject, "cardno");
                                String fee_rate = JsonUtil.getValueFromJSONObject(jsonObject, "fee_rate");
                                if (TextUtils.isEmpty(fee_rate)) {
                                    iv_understandmore.setVisibility(View.GONE);
                                    tvCurrentreerate.setVisibility(View.GONE);
                                } else {
                                    iv_understandmore.setVisibility(View.VISIBLE);
                                    tvCurrentreerate.setVisibility(View.VISIBLE);
                                    tvCurrentreerate.setText(fee_rate);
                                    PreferenceUtil.getInstance(MyInfoActivity.this)
                                            .saveString("fee_rate",fee_rate);
                                }

                                if (!TextUtils.isEmpty(cardIdx)) {
                                    if (!TextUtils.isEmpty(bankId)) {
                                        if (!TextUtils.isEmpty(bankId) && bankId.length() > 6) {
                                            bankId = bankId.substring(1, 4);
                                        }
                                        BanksUtils.selectIcoidToImgView(MyInfoActivity.this, bankId, jsk_bankimg_iv);
                                    }
                                    if (!TextUtils.isEmpty(cardNo)) {
                                        cardnumber_tv.setText(StringUnit.cardJiaMi(cardNo));
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        showAuthState();
                    }

                    @Override
                    public void onOtherState() {
                        super.onOtherState();
                        nodatalayout.setVisibility(View.VISIBLE);
                        all_layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onTradeFailed() {
                        super.onTradeFailed();
                        nodatalayout.setVisibility(View.VISIBLE);
                        all_layout.setVisibility(View.GONE);
                    }
                }


        );
    }

    @OnClick(R.id.iv_understandmore)
    public void feeAskClick() {

        toHtmlMessageAct(AppConfig.RATE, "费率说明");

    }

    private void showAuthState() {
        int tag = QuickPayAppData.getInstance(this).getAuthenFlag();
        if (2 == tag) {
            info_state_tv.setText("认证中");
        } else if (4 == tag) {
            info_state_tv.setText("认证失败>>");
        } else if (3 == tag) {
            info_state_tv.setText("已认证");
        } else if (1 == tag || 5 == tag) {
            info_state_tv.setText("资料提交,照片未提交>>");
        } else {
            info_state_tv.setText("未认证>>");
        }
        phonenumberTv.setText(StringUnit.phoneJiaMi(QuickPayAppData.getInstance(this).getPhone()));
    }

    @OnClick(R.id.toupdatepaswd_layou)
    public void toupdatepaswdLayou() {
        startActivity(new Intent(MyInfoActivity.this, ChangePassWord.class));
    }

    @OnClick(R.id.headimg_iv)
    public void heardImgClick(View view) {
        infoStateToAuth(view);
    }

    //点击我的认证状态
    @OnClick(R.id.info_state_tv)
    public void infoStateToAuth(View view) {
        disabledTimerView(view);
        int tag = QuickPayAppData.getInstance(this).getAuthenFlag();
        if (0 == tag) {
            startActivityForResult(
                    new Intent(MyInfoActivity.this, AuthActivity.class),
                    AppConfig.TO_AUTH);
        } else if (1 == tag || 5 == tag || 4 == tag) {
            startActivityForResult(
                    new Intent(MyInfoActivity.this, AuthPhotoUploadActivity.class),
                    AppConfig.TO_UPLOAD);
        }

    }

    @OnClick(R.id.tobandcard_layou)
    public void tobandcardLayouClick() {
        Intent intent = new Intent(MyInfoActivity.this, CardBindAddCardNumberActivity.class);
        intent.putExtra("cardIdx", cardIdx);
        intent.putExtra("cardNo", cardNo);
        startActivityForResult(intent, AppConfig.TO_BINDCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConfig.TO_AUTH || requestCode == AppConfig.TO_UPLOAD || resultCode == AppConfig.TASKSUCCESS || resultCode == AppConfig.UPLOAD_BACK) {
            getMyMsg();
        }
    }
}
