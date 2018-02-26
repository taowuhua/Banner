package com.bank.quickpay.activity.earningscircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.adapter.EarningCircleAdapter;
import com.bank.quickpay.bean.EarningCircleBean;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.CNummberUtil;
import com.bank.quickpay.utils.FastJSONUtils;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.QuickMoneyEncoder;
import com.bank.quickpay.widget.CMoneyTextView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ryx.quickadapter.inter.NoDoubleClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 收益圈
 * Created by xpp on 2017/11/1 0001.
 */

public class EarningCircleActivity extends BaseActivity {

    private View header;
    @BindView(R.id.rv_income)
    XRecyclerView rv_income;//收益列表

    //今日收益
    CMoneyTextView tv_tdIncome;
    //累计收益
    CMoneyTextView tv_ljIncome;
    //圈友数量
    TextView tv_people_amount;

    private String td_income;//今日收益
    private String pl_amount;//圈友数量
    private String lj_income;//累计收益
    private String bank1="0",bank2="0",valid_level1="0",valid_level2="0";

    private String withdrawl_amount;//可提取的金额
    //可提取收益
    CMoneyTextView tv_withdrawalNo;

    private EarningCircleAdapter earningCircleAdapter;
    List<EarningCircleBean> data = new ArrayList<EarningCircleBean>();
    String localdate;//最后一条信息的日期
    private int UNDRAWL_REQUESTCODE=101;

    private int rv_state = 0;//初始状态，1：下拉刷新；2：上拉加载更多

    @Override
    protected int getLayoutId() {
        return R.layout.activity_earnings_circle;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRecycleView();
        getIncomeDetail();
    }

    private void getIncomeDetail() {
        initQtPatParams();
        qtpayApplication.setValue("GetIncomeDetail.Req");
        qtpayAttributeList.add(qtpayApplication);
        httpsPost("GetIncomeDetail", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                LogUtil.showLog("onTradeSuccess---", payResult + "-----" + payResult.getRespCode());
                if (AppConfig.QTNET_SUCCESS.equals(payResult.getRespCode())) {
                    if (payResult.getData() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(payResult.getData());
                            pl_amount = JsonUtil.getValueFromJSONObject(jsonObject, "bank");
                            if (!TextUtils.isEmpty(pl_amount)) {
                                tv_people_amount.setText(pl_amount);//圈友数量
                            } else {
                                tv_people_amount.setText("0");//圈友数量
                            }
                            bank1=JsonUtil.getValueFromJSONObject(jsonObject, "bank1");
                            bank2=JsonUtil.getValueFromJSONObject(jsonObject, "bank2");
                            valid_level1=JsonUtil.getValueFromJSONObject(jsonObject, "valid_level1");
                            valid_level2=JsonUtil.getValueFromJSONObject(jsonObject, "valid_level2");
                            //今日收益
                            td_income = JsonUtil.getValueFromJSONObject(jsonObject, "amount_today");
                            tv_tdIncome.withNumber(CNummberUtil.parseFloat(QuickMoneyEncoder.decodeToyuan(td_income), 0.00f)).start();
                            //累计收益
                            lj_income = JsonUtil.getValueFromJSONObject(jsonObject, "income_his");
                            tv_ljIncome.withNumber(CNummberUtil.parseFloat(QuickMoneyEncoder.decodeToyuan(lj_income), 0.00f)).start();
                            //可提取金额
                            withdrawl_amount = JsonUtil.getValueFromJSONObject(jsonObject, "balance");
                            tv_withdrawalNo.withNumber(CNummberUtil.parseFloat(QuickMoneyEncoder.decodeToyuan(withdrawl_amount),
                                    0.00f)).start();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                getIncomeList();
            }

            @Override
            public void onTradeFailed() {
                getIncomeList();
            }

            @Override
            public void onOtherState() {
                getIncomeList();
            }
        });

    }

public void circleAmountClick(){
    Intent intent=new Intent(EarningCircleActivity.this,CircleTheDetailActivity.class);
    intent.putExtra("bank1",bank1);
    intent.putExtra("bank2",bank2);
    intent.putExtra("valid_level1",valid_level1);
    intent.putExtra("valid_level2",valid_level2);
    startActivity(intent);

}
    private void initRecycleView() {
        header = LayoutInflater.from(this).inflate(R.layout.activity_earning_circle_header,
                null, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_income.setLayoutManager(linearLayoutManager);
        rv_income.setPullRefreshEnabled(true);
        rv_income.setLoadingMoreEnabled(true);
        rv_income.addHeaderView(header);
        rv_income.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        earningCircleAdapter = new EarningCircleAdapter(data, this, R.layout.adapter_earning_detail_item);
        rv_income.setAdapter(earningCircleAdapter);
        rv_income.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rv_state = 1;
                getIncomeDetail();
            }

            @Override
            public void onLoadMore() {
                rv_state = 2;
                getIncomeList();

            }
        });

        tv_tdIncome = (CMoneyTextView) header.findViewById(R.id.tv_tdIncome);
        tv_ljIncome = (CMoneyTextView) header.findViewById(R.id.tv_ljIncome);
        tv_people_amount = (TextView) header.findViewById(R.id.tv_people_amount);
        tv_withdrawalNo = (CMoneyTextView) header.findViewById(R.id.tv_withdrawalNo);
        Button btn_whithdrawl = (Button) header.findViewById(R.id.btn_whithdrawl);
        header.findViewById(R.id.people_amount_layout).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                circleAmountClick();
            }
        });
        header.findViewById(R.id.tilerightImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHtmlMessageAct(AppConfig.SUBRUN,"收益说明");
            }
        });
        btn_whithdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningCircleActivity.this, UndrawlBalanceActivity.class);
                intent.putExtra("blanceMoney", withdrawl_amount);
                LogUtil.showLog("withdrawl_amount----"+withdrawl_amount);
                startActivityForResult(intent,UNDRAWL_REQUESTCODE);
            }
        });

        ImageView btn_back = (ImageView) header.findViewById(R.id.tileleftImg);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getIncomeList() {
        initQtPatParams();
        qtpayApplication.setValue("GetIncomeDetailByDay.Req");
        qtpayAttributeList.add(qtpayApplication);
        if (rv_state != 2) {
            SimpleDateFormat formatterdate = new SimpleDateFormat(
                    "yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            localdate = formatterdate.format(curDate);
        }
        qtpayParameterList.add(new Param("localdate", localdate));
        httpsPost("GetIncomeDetailByDay", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                if (payResult.getData() != null) {
                    String jsonData = payResult.getData();
                    List<EarningCircleBean> tempList = null;
                    try {
                        tempList = FastJSONUtils.getInstance().parseJSONArray(jsonData, EarningCircleBean.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogUtil.showLog("rv_state-----" + rv_state);
                    if (rv_state == 1) {
                        data.clear();
                        if (tempList != null) {
                            data.addAll(tempList);
                        }
                        rv_income.refreshComplete();
                    } else if (rv_state == 2) {
                        if (tempList != null) {
                            data.addAll(tempList);
                        }
                        rv_income.loadMoreComplete();
                    } else {
                        if (tempList != null) {
                            data.addAll(tempList);
                        }
                    }
                    int size = data.size();
                    if (size > 1) {
                        localdate = data.get(size - 1).getLocaldate();
                    }
                    LogUtil.showLog("data-----" + data.toString());
                    earningCircleAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTradeFailed() {
                if (rv_state == 1) {
                    rv_income.refreshComplete();
                } else if (rv_state == 2) {
                    rv_income.loadMoreComplete();
                }
            }

            @Override
            public void onOtherState() {
                if (rv_state == 1) {
                    rv_income.refreshComplete();
                } else if (rv_state == 2) {
                    rv_income.loadMoreComplete();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNDRAWL_REQUESTCODE) {
            rv_state = 1;
            getIncomeDetail();
        }
    }

}
