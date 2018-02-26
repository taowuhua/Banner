package com.bank.quickpay.fragment.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bank.quickpay.R;
import com.bank.quickpay.activity.mymsg.CardBindAddCardNumberActivity;
import com.bank.quickpay.activity.mymsg.MyInfoActivity;
import com.bank.quickpay.activity.transactiondetails.BlackCheckMsgActivity;
import com.bank.quickpay.activity.transactiondetails.DetailsTabMainActivity;
import com.bank.quickpay.bean.BankCardInfo;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.fragment.base.BaseFragment;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.interfaces.FragmentListener;
import com.bank.quickpay.utils.BanksUtils;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.StringUnit;
import com.rey.material.widget.Button;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class CashFlowFragment extends BaseFragment {

    //状态Map
    private Map<String, String> consumestepMap = new HashMap<String, String>();
    private Map<String, String> acceptstepMap = new HashMap<String, String>();
    private Map<String, String> outstepMap = new HashMap<String, String>();
    private Map<String, String> offlinestepMap = new HashMap<String, String>();
    @BindView(R.id.cashflowimg)
    ImageView cashflowimg;
    @BindView(R.id.expressionimg)
    ImageView expressionimg;
    @BindView(R.id.impay_bankimg)
    ImageView impay_bankimg;

    @BindView(R.id.btn_changebankcard)
    Button btn_changebankcard;
    @BindView(R.id.btn_submitnewbank)
    Button btn_submitnewbank;
    @BindView(R.id.btn_toBlackCheckMsg)
    Button btn_toBlackCheckMsg;

    @BindView(R.id.ll_changebank)
    LinearLayout ll_changebank;
    @BindView(R.id.toBlackCheckMsgll)
    LinearLayout toBlackCheckMsgll;
    @BindView(R.id.im_pay_selectbankid)
    AutoRelativeLayout im_pay_selectbankid;
    @BindView(R.id.tv_first)
    TextView tv_first;
    @BindView(R.id.tv_second)
    TextView tv_second;
    @BindView(R.id.tv_third)
    TextView tv_third;
    @BindView(R.id.tv_textmessage)
    TextView tv_textmessage;
    @BindView(R.id.impay_bankname)
    TextView impay_bankname;
    @BindView(R.id.impay_accountno)
    TextView impay_accountno;

    private String cardInfo, cardidx, oldaccount, md5code,serveroldaccount;
    private ArrayList<BankCardInfo> bankcardlist = new ArrayList<BankCardInfo>();
    private FragmentListener mListener;
    private TradeDetailInfo tradeDetailInfo;
    private boolean isInit = false;
    private static String actionFlag;
    String cashstatus = "";
    public CashFlowFragment() {
        // Required empty public constructor
    }

    /**
     * 创建资金动态实例
     *
     * @param tradeDetailInfo
     * @return
     */
    public static CashFlowFragment newInstance(TradeDetailInfo tradeDetailInfo) {
        CashFlowFragment fragment = new CashFlowFragment();
        Bundle args = new Bundle();
        args.putSerializable("tradeDetailInfo", tradeDetailInfo);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_cash_flow;
    }

    @Override
    public void afterViews(Bundle savedInstanceState) {
        tradeDetailInfo = (TradeDetailInfo) getArguments().getSerializable("tradeDetailInfo");
        Map paramMap = new HashMap();
        paramMap.put("flag", "GetBalanceStep");
        paramMap.put("data", tradeDetailInfo);
        mListener.doDataRequest(paramMap);
    }

    /**
     * activity回调Fragment接口
     * @param actiontype
     * @param qtpayResult
     */
    public void send(int actiontype, QuickPayResult qtpayResult) {
        if (actiontype == 0x111) {
            //资金动态信息
            String reminder = qtpayResult.getDesc();
            if (!TextUtils.isEmpty(reminder)) {
                tv_textmessage.setText(Html.fromHtml(reminder));
            }
            initFundDynamicData(qtpayResult.getData());
            initFundDynamicView();
        } else if (actiontype == 0x222) {
            //获取银行卡列表值
            newinitBankListData(qtpayResult.getData());
            if (bankcardlist.size() > 0) {
                showSelectBankList();
            } else {
                LogUtil.showToast(getContext(), "您还未绑定有效结算卡,请先绑定有效结算卡!");
                Map paramMap = new HashMap();
                paramMap.put("flag", "tobindBankAct");
                mListener.doDataRequest(paramMap);
            }
        } else if (actiontype == 0x333) {
            //提交更换银行卡后的数据
            LogUtil.showToast(getActivity(), "银行卡信息提交成功");
            getActivity().finish();
        }

    }

    @OnClick(R.id.im_pay_selectbankid)
    public void updatebankViewClick() {
        //银行卡展示布局点击事件
        if(bankcardlist.size()!=0){
            LogUtil.showLog("bankcardlist=="+bankcardlist.toString());
            Intent intent = new Intent(getContext(), CardBindAddCardNumberActivity.class);
            intent.putExtra("cardIdx", bankcardlist.get(0).getCardIdx());
            intent.putExtra("cardNo", bankcardlist.get(0).getAccountNo());
            startActivityForResult(intent, AppConfig.TO_BINDCARD);
        }
    }
    /**
     * 更换银行卡
     */
    @OnClick(R.id.btn_changebankcard)
    public void changebankcard() {
        if (bankcardlist.size() == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("flag", "getBankCardList");
            mListener.doDataRequest(map);
        } else {
            showSelectBankList();
        }
    }

    @OnClick(R.id.btn_submitnewbank)
    public void submitNewBank() {
        String cardInfoShort=StringUnit.cardShortShow(cardInfo);
        if(TextUtils.isEmpty(cardInfoShort)||cardInfoShort.equals(serveroldaccount)){
            LogUtil.showToast(getActivity(), "请更换别的银行卡!");
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("flag", "anewDrawMoney");
            map.put("cardInfo", cardInfo);
            map.put("cardidx", cardidx);
            map.put("oldaccount", TextUtils.isEmpty(serveroldaccount)?"":serveroldaccount);
            map.put("md5code", TextUtils.isEmpty(md5code)?"":md5code);
            map.put("data", tradeDetailInfo);
            mListener.doDataRequest(map);
        }
    }

    @OnClick(R.id.btn_toBlackCheckMsg)
    public void toBlackCheckMsg() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BlackCheckMsgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cardIdjiami", acceptstepMap.get("account"));
        bundle.putString("md5Value", acceptstepMap.get("md5code"));
        bundle.putString("localtime", acceptstepMap.get("localtime"));
        bundle.putString("localDate", acceptstepMap.get("localDate"));
        bundle.putString("locallogo", acceptstepMap.get("locallogo"));
        bundle.putString("msgid", acceptstepMap.get("msgid"));
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.TO_BINDCARD || resultCode == AppConfig.TASKSUCCESS) {
            bankcardlist.clear();
            Map<String, String> map = new HashMap<>();
            map.put("flag", "getBankCardList");
            mListener.doDataRequest(map);
        }
    }
        private void showSelectBankList() {
        try {
            oldaccount = bankcardlist.get(0).getAccountNo();
            //卡号
            cardInfo = bankcardlist.get(0).getAccountNo();
            //银行卡索引值
            cardidx = bankcardlist.get(0).getCardIdx();
            //布局显示
            BanksUtils.selectIcoidToImgView(getActivity(),bankcardlist.get(0).getBankId(), impay_bankimg);
            impay_bankname.setText(bankcardlist.get(0).getBankName());
            impay_accountno.setText(StringUnit.cardJiaMi(bankcardlist.get(0).getAccountNo()));
            //选择银行卡按钮隐藏
            btn_changebankcard.setVisibility(View.GONE);
            //提交按钮显示
            btn_submitnewbank.setVisibility(View.VISIBLE);
            im_pay_selectbankid.setVisibility(View.VISIBLE);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.showLog(e.getLocalizedMessage());
        }

    }

    /**
     * 1、消费
     * 2、银行转出受理
     * 3、银行转出状态
     * 4、线下出款状态
     * {"desc":"交易成功","status":"00","datetime":"20160204142038","step":"consume"},  //00状态才会有后续状态
     * {"desc":"银行已受理","status":"11","datetime":"20160204142037","step":"accept"},  //2、5、19时不会有后续状态
     * {"desc":"交易成功","status":"23","datetime":"20160204215902","step":"out"}]}
     * //status是11自动出款成功,22线下出款中 ,23线下出款失败(可进行重提操作),24线下重提后出款中(此时判断offline状态11的显示线下成功)
     * 初始化资金动态数据
     */
    private void initFundDynamicData(String jsondata) {
        try {
            JSONObject jsonObj = new JSONObject(jsondata);
            JSONArray cashFLos = jsonObj.getJSONArray("resultBean");
            for (int i = 0; i < cashFLos.length(); i++) {
                String step = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "step");
                String status = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "status");
                String datetime = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "datetime");
                String desc = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "desc");
                if ("consume".equals(step)) {
                    consumestepMap.put("step", "consume");
                    consumestepMap.put("status", status);
                    consumestepMap.put("datetime", datetime);
                    consumestepMap.put("desc", desc);
                } else if ("accept".equals(step)) {
                    acceptstepMap.put("step", "accept");
                    acceptstepMap.put("status", status);
                    acceptstepMap.put("datetime", datetime);
                    acceptstepMap.put("desc", desc);
                    //状态19的
                    String md5code = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "md5code");
                    acceptstepMap.put("md5code", md5code);
                    String account = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "account");
                    acceptstepMap.put("account", account);
                    acceptstepMap.put("localtime", DataUtil.getRightValue(tradeDetailInfo.getLocalTime()));
                    acceptstepMap.put("localDate", DataUtil.getRightValue(tradeDetailInfo.getLocalDate()));
                    acceptstepMap.put("locallogo", DataUtil.getRightValue(tradeDetailInfo.getLocalLogNo()));
                    String primarykey = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "primarykey");
                    acceptstepMap.put("msgid", primarykey);
                    //是否可以補充資料標誌
                    String enableblack = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "enableblack");
                    acceptstepMap.put("enableblack", enableblack);
                } else if ("out".equals(step)) {
                    String account = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "account");
                    String md5code = JsonUtil.getValueFromJSONObject(cashFLos.getJSONObject(i), "md5code");
                    outstepMap.put("md5code", md5code);
                    outstepMap.put("step", "out");
                    outstepMap.put("status", status);
                    outstepMap.put("datetime", datetime);
                    outstepMap.put("desc", desc);
                    outstepMap.put("account", account);
                } else if ("offline".equals(step)) {
                    offlinestepMap.put("step", "offline");
                    offlinestepMap.put("status", status);
                    offlinestepMap.put("datetime", datetime);
                    offlinestepMap.put("desc", desc);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void newinitBankListData(String banklistJson) {
        try {
            JSONObject jsonObj = new JSONObject(banklistJson);
            bankcardlist.clear();
            if ("0000".equals(jsonObj.getString("code"))) {
                // 解析银行卡信息
                JSONArray banks = jsonObj.getJSONObject("result").getJSONArray("cardlist");
                for (int i = 0; i < banks.length(); i++) {
                    BankCardInfo   bankCardInfo = new BankCardInfo();
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
                    bankcardlist.add(bankCardInfo);
                }
            }else{
//             String msg=   JsonUtil.getValueFromJSONObject(jsonObj,"msg");
//             LogUtil.showToast(getContext(),msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化流程布局
     */
    private void initFundDynamicView() {

        if (consumestepMap.isEmpty()) {
            cashflowimg.setImageResource(R.drawable.cashflow_process1);
            expressionimg.setImageResource(R.drawable.weep);
            tv_textmessage.setText("订单存在问题!!!");
            btn_changebankcard.setVisibility(View.GONE);
            ll_changebank.setVisibility(View.GONE);
            return;
        }

        //订单消费
        String consumestepStatus = consumestepMap.get("status");
        String consumestepdesc = consumestepMap.get("desc");
        tv_first.setText(consumestepdesc);

        //只有00状态才会有后续,其他状态后续都不显示
        if ("00".equals(consumestepStatus)) {
            cashflowimg.setImageResource(R.drawable.cashflow_process2);
            tv_first.setTextColor(getResources().getColor(R.color.black));
        } else {
            cashflowimg.setImageResource(R.drawable.cashflow_process1);
            tv_first.setTextColor(getResources().getColor(R.color.graytext));
            return;
        }
        //银行受理情况
        String acceptstepStatus = acceptstepMap.get("status");
        String acceptstepdesc = acceptstepMap.get("desc");
        tv_second.setText(acceptstepdesc);
        //走到accept步,将图片修改为亮图
        cashflowimg.setImageResource(R.drawable.cashflow_process3);
        tv_second.setTextColor(getResources().getColor(R.color.black));
        //0,1,2,3,4,5、19时不会有后续状态
        if ("0,1,2,3,4,5,19".contains(acceptstepStatus)) {
            //19是明确失败
            if ("19".equals(acceptstepStatus)) {
                cashflowimg.setImageResource(R.drawable.cashflow_process2);
                tv_second.setTextColor(getResources().getColor(R.color.graytext));

                if ("1".equals(acceptstepMap.get("enableblack"))) {
                    //enableblack為1的時候可以補充資料
                    toBlackCheckMsgll.setVisibility(View.VISIBLE);
                    btn_toBlackCheckMsg.setVisibility(View.VISIBLE);
                } else {
                    toBlackCheckMsgll.setVisibility(View.GONE);
                    btn_toBlackCheckMsg.setVisibility(View.GONE);
                }

            }
            return;
        }
        //银行出款
        String outstepStatus = outstepMap.get("status");
        String outstepdesc = outstepMap.get("desc");
        tv_third.setText(outstepdesc);
        //线下出款
        String offlinestepStatus = offlinestepMap.get("status");
        String offlinestepdesc = offlinestepMap.get("desc");
        //status是11自动出款成功,22线下出款中
        if ("11".equals(outstepStatus) || "22".equals(outstepStatus)) {
            expressionimg.setImageResource(R.drawable.smile);
            //出款成功
            cashflowimg.setImageResource(R.drawable.cashflow_process4);
            tv_third.setTextColor(getResources().getColor(R.color.black));
            return;
        } else if ("23".equals(outstepStatus)) {
//            //获取错误的卡号
//            oldaccount = outstepMap.get("account");
            //服务端返回的错误的旧的卡号
            serveroldaccount=outstepMap.get("account");
            md5code = outstepMap.get("md5code");
            //可进行重提操作
            cashflowimg.setImageResource(R.drawable.cashflow_process3);
            tv_third.setTextColor(getResources().getColor(R.color.graytext));
            //更换银行卡
            btn_changebankcard.setVisibility(View.VISIBLE);
            ll_changebank.setVisibility(View.VISIBLE);
        } else
            //24线下重提后出款中(此时判断offline状态11的显示线下成功 )
            if ("24".equals(outstepStatus) && "11".equals(offlinestepStatus)) {
                //自动失败，但是线下转出成功
                cashflowimg.setImageResource(R.drawable.cashflow_process4);
                tv_third.setTextColor(getResources().getColor(R.color.black));
                tv_third.setText(offlinestepdesc);
            } else if ("24".equals(outstepStatus) && !"11".equals(offlinestepStatus)) {
                //自动失败，但是线下转出情况未知
                cashflowimg.setImageResource(R.drawable.cashflow_process3);
                tv_third.setTextColor(getResources().getColor(R.color.graytext));
                tv_third.setText(offlinestepdesc);
            }
    }
    public String getRightValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            if (values.length == 2) {
                return values[1];
            }
            return "";
        }
        return "";
    }

    public void initFlow() {
        expressionimg.setVisibility(View.GONE);
        tv_first.setText("申请提现");
        tv_third.setText("提现成功");
        if("1".equals(cashstatus)){
            cashflowimg.setImageResource(R.drawable.cashflow_process2);
            tv_first.setTextColor(getResources().getColor(R.color.black));
            tv_second.setTextColor(getResources().getColor(R.color.graytext));
            tv_third.setTextColor(getResources().getColor(R.color.graytext));
        }else if("2".equals(cashstatus)){
            cashflowimg.setImageResource(R.drawable.cashflow_process3);
            tv_first.setTextColor(getResources().getColor(R.color.graytext));
            tv_second.setTextColor(getResources().getColor(R.color.black));
            tv_third.setTextColor(getResources().getColor(R.color.graytext));
        }else if("3".equals(cashstatus)){
            expressionimg.setVisibility(View.VISIBLE);
            expressionimg.setImageResource(R.drawable.smile);
            cashflowimg.setImageResource(R.drawable.cashflow_process4);
            tv_first.setTextColor(getResources().getColor(R.color.graytext));
            tv_second.setTextColor(getResources().getColor(R.color.graytext));
            tv_third.setTextColor(getResources().getColor(R.color.black));
        }else{
            cashflowimg.setImageResource(R.drawable.cashflow_process3);
            tv_first.setTextColor(getResources().getColor(R.color.graytext));
            tv_second.setTextColor(getResources().getColor(R.color.graytext));
            tv_third.setTextColor(getResources().getColor(R.color.red_second));
            tv_third.setText("提现失败");
        }
    }
    @Override
    public void onAttach(Context context) {
        try {
            mListener = (DetailsTabMainActivity) getBaseActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        super.onAttach(context);
    }

}
