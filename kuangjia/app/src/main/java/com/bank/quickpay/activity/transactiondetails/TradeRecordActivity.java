package com.bank.quickpay.activity.transactiondetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.adapter.TradeRecordAdapter;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 交易明细
 * Created by xpp on 2017/11/2 0002.
 */

public class TradeRecordActivity extends BaseActivity {

    @BindView(R.id.rv_income)
    XRecyclerView rv_income;

    @BindView(R.id.nodatalayout)
    AutoLinearLayout nodatalayout;

    private String localdate;//列表最后一条数据的日期
    private String LocalTime;//列表最后一条数据的时间

    private TradeRecordAdapter recordAdapter;
    public List<TradeDetailInfo> tradeList = new ArrayList<TradeDetailInfo>();
    Param qtpayflag, qtpayRecordDate, qtpayRecordTime;
    private String date, time;
    String jsondata = "";

    //是否是下拉刷新
    public boolean isRefresh = true;

    String sumAmount;//交易列表的数量
    String isIncomeLast = "0"; // 收入数据是否加载完


    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_record;
    }

    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        qtpayApplication = new Param("application");
        qtpayflag = new Param("flag");
        qtpayRecordDate = new Param("tradeRecordDate");
        qtpayRecordTime = new Param("tradeRecordTime");

        isNeedThread = false;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("交易明细", true, false);
        initQtPatParams();
        getTradeRecord();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_income.setLayoutManager(linearLayoutManager);
        rv_income.setPullRefreshEnabled(true);
        rv_income.setLoadingMoreEnabled(true);
        rv_income.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        recordAdapter = new TradeRecordAdapter(tradeList, this, R.layout.adapter_trade_record_item);
        rv_income.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener(new OnListItemClickListener<TradeDetailInfo>() {
            @Override
            public void onItemClick(View var1, int var2, TradeDetailInfo item) {
//                Intent intent = new Intent(TradeRecordActivity.this,
//                        TradeRecordDetailActivity.class);
                Intent intent = new Intent(TradeRecordActivity.this,
                        DetailsTabMainActivity.class);
                intent.putExtra("tradeDetailInfo", (Serializable) item);
                startActivity(intent);
            }
        });
        rv_income.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                isRefresh = true;
                getTradeRecord();
            }

            @Override
            public void onLoadMore() {
                // load more data here
                isRefresh = false;
                getTradeRecord();
            }
        });
    }

    public void getTradeRecord() {
        qtpayApplication.setValue("GetTradeRecordIn2.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayflag.setValue("SF");
        if (!isRefresh) {
            date = localdate;
            time = LocalTime;
        } else {
            SimpleDateFormat formatterdate = new SimpleDateFormat(
                    "yyyyMMdd");
            SimpleDateFormat formattertime = new SimpleDateFormat("HHmmss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            date = formatterdate.format(curDate);
            time = formattertime.format(curDate);
        }
        qtpayRecordDate.setValue(date);
        qtpayRecordTime.setValue(time);

        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayflag);
        qtpayParameterList.add(qtpayRecordDate);
        qtpayParameterList.add(qtpayRecordTime);
        showLoading();
        httpsPost("GetTradeRecordIn2.Req", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult qtpayResult) {
                jsondata = qtpayResult.getData();
                LogUtil.showLog("getdata----" + qtpayResult.getData());
                LogUtil.showLog("jsondata----" + jsondata);
                ArrayList<TradeDetailInfo> tempList = getDetailList();
                if (!isRefresh && tempList == null) {
                    LogUtil.showToast(TradeRecordActivity.this, "无更多记录！");
                } else if (isRefresh && tradeList == null) {
                    LogUtil.showToast(TradeRecordActivity.this, "无记录！");
                }
                notifyDataSetChanged(tempList);
            }

            @Override
            public void onOtherState() {
                if (isRefresh) {
                    rv_income.refreshComplete();
                } else {
                    rv_income.loadMoreComplete();
                }
            }

            @Override
            public void onTradeFailed() {
                if (isRefresh) {
                    rv_income.refreshComplete();
                } else {
                    rv_income.loadMoreComplete();
                }
            }
        });
    }

    public ArrayList<TradeDetailInfo> getDetailList() {
        ArrayList<TradeDetailInfo> list = null;
        if (jsondata != null && jsondata.length() > 0) {
            try {
                JSONObject jsonObj = new JSONObject(jsondata);
                isIncomeLast = (String) jsonObj.getJSONObject("summary")
                        .getString("isLast");
                sumAmount = jsonObj.getString("sumAmount");

                LogUtil.showLog("sumAmount===" + sumAmount);

                // 解析交易详情
                JSONArray detailsArray = jsonObj.getJSONArray("resultBean");
                list = new ArrayList<TradeDetailInfo>();
                TradeDetailInfo detailinfo;
                for (int i = 0; i < detailsArray.length(); i++) {
                    detailinfo = new TradeDetailInfo();
                    detailinfo.setStatus(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "status"));
                    detailinfo.setSignPic(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "signPic"));
                    detailinfo.setLocalDate(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "localDate"));
                    detailinfo.setTermId(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "termId"));
                    detailinfo.setLocalLogNo(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "localLogNo"));
                    detailinfo.setFee(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "fee"));
                    detailinfo.setAmount(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "amount"));
                    detailinfo.setBizAmount(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "bizAmount"));
                    detailinfo.setBranchId(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "branchId"));
                    detailinfo.setPayType(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "payType"));
                    detailinfo.setAccount(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "account"));
                    detailinfo.setAccount2(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "account2"));
                    detailinfo.setMerchantId(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "merchantId"));
                    detailinfo.setLocalTime(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "localTime"));
                    detailinfo.setOrderId(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "orderId"));
                    detailinfo.setTransName(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "transName"));
                    detailinfo.setBranchName(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "branchName"));
                    detailinfo.setMobileno(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "mobileno"));
                    detailinfo.setDeawstatus(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "deawstatus"));
                    detailinfo.setPaytag(JsonUtil.getValueFromJSONObject(
                            detailsArray.getJSONObject(i), "payTag"));
                    list.add(detailinfo);
                    detailinfo = null;
                }
                if (list != null && list.size() != 0) {
                    return list;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                jsondata = "";
            }

        }
        return null;
    }

    public void notifyDataSetChanged(List list) {
        if (isRefresh) {
            tradeList.clear();
            if (list != null) {
                tradeList.addAll(list);
            }
            rv_income.refreshComplete();
        } else {
            if (list != null) {
                tradeList.addAll(list);
            }
            rv_income.loadMoreComplete();
        }
        if (tradeList.size() != 0) {
            int size = tradeList.size();
            localdate = tradeList.get(size-1).getLocalDate();
            LocalTime = tradeList.get(size-1).getLocalTime();
            rv_income.setVisibility(View.VISIBLE);
            nodatalayout.setVisibility(View.GONE);
            LogUtil.showLog("tradeList---", tradeList.size() + "----");
        } else {
            nodatalayout.setVisibility(View.VISIBLE);
            rv_income.setVisibility(View.GONE);
        }
        recordAdapter.notifyDataSetChanged();

    }
}
