package com.bank.quickpay.activity.transactiondetails;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.mymsg.CardBindActivity;
import com.bank.quickpay.activity.mymsg.CardBindAddCardNumberActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.bean.TradeDetailInfo;
import com.bank.quickpay.fragment.details.CashFlowFragment;
import com.bank.quickpay.fragment.details.DetailsContentFragment;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.FragmentListener;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.IntentUtil;
import com.bank.quickpay.utils.LogUtil;

import java.util.Map;

import butterknife.BindView;

/**
 *交易明细详情Tab
 */
public class DetailsTabMainActivity extends BaseActivity implements FragmentListener {
    @BindView(R.id.commonTabLayout)
    TabLayout tabLayout;
    private TradeDetailInfo tradeDetailInfo;
    CashFlowFragment cashFlowFragment;
    DetailsContentFragment detailsContentFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_details_tab_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("交易明细", true, false);
        tradeDetailInfo =(TradeDetailInfo) IntentUtil.getSerializableExtra(getIntent(),
                "tradeDetailInfo");
        cashFlowFragment=CashFlowFragment.newInstance(tradeDetailInfo);
        detailsContentFragment=  DetailsContentFragment.newInstance(tradeDetailInfo);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    todetailsFrg();
                }else {
                    tocashFrg();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        todetailsFrg();
    }


    /**
     * 收支明细
     */
    private void todetailsFrg(){
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if(!detailsContentFragment.isAdded()){
                LogUtil.showLog("detailsContentFragment===初次加载");
                fragmentTransaction.add(R.id.tabfragemetcomtent, detailsContentFragment,"details").commitAllowingStateLoss();
            }else{
                LogUtil.showLog("detailsContentFragment===isAdded");
                fragmentTransaction.hide(cashFlowFragment).show(detailsContentFragment).commitAllowingStateLoss();
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    /**
     * 资金动态
     */
    private void tocashFrg(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(!cashFlowFragment.isAdded()){
            LogUtil.showLog("cashFlowFragment===初次加载");
            fragmentTransaction.hide(detailsContentFragment).add(R.id.tabfragemetcomtent, cashFlowFragment,"cash").commitAllowingStateLoss();
        }else{
            LogUtil.showLog("cashFlowFragment===isAdded");
            fragmentTransaction.hide(detailsContentFragment).show(cashFlowFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void doDataRequest(Object data) {
        Map paramMap=(Map)data;
        if("GetBalanceStep".equals(paramMap.get("flag"))){
            //获取资金动态数据
            TradeDetailInfo detailInfo =(TradeDetailInfo)paramMap.get("data");
            initQtPatParams();
            qtpayApplication.setValue("GetBalanceStep.Req");
            qtpayAttributeList.add(qtpayApplication);
            qtpayParameterList.add(new Param("localdate", DataUtil.getRightValue(detailInfo.getLocalDate())));
            qtpayParameterList.add(new Param("locallogno", DataUtil.getRightValue(detailInfo.getLocalLogNo())));
            qtpayParameterList.add(new Param("orderId",DataUtil.getRightValue(detailInfo.getOrderId())));
            httpsPost("GetBalanceStepTag", new XmlCallback() {
                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
                    cashFlowFragment.send(0x111,payResult);
                }
            });
        }else if("getBankCardList".equals(paramMap.get("flag"))){
            //获取银行卡列表
            initQtPatParams();
            qtpayApplication.setValue("BindCardList.Req");
            qtpayAttributeList.add(qtpayApplication);
            qtpayParameterList.add(new Param("cardType","10"));
            httpsPost("BindCardListTag", new XmlCallback() {
                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
                    cashFlowFragment.send(0x222,payResult);
                }
            });
        }else if("tobindBankAct".equals(paramMap.get("flag"))){
            //去绑定银行卡界面
            Intent intent = new Intent(this,
                    CardBindAddCardNumberActivity.class);
            startActivity(intent);
            finish();
        }else if("anewDrawMoney".equals(paramMap.get("flag"))){
            String  cardInfo=(String)paramMap.get("cardInfo");
            String  cardidx=(String)paramMap.get("cardidx");
            String  oldaccount=(String)paramMap.get("oldaccount");
            String  md5code=(String)paramMap.get("md5code");
            TradeDetailInfo detailInfo =(TradeDetailInfo)paramMap.get("data");
            anewDrawMoney(detailInfo,cardInfo,cardidx,oldaccount,md5code);
        }
    }

    /**
     * 银行卡信息重提
     */
    private void anewDrawMoney( TradeDetailInfo detailInfo,String cardInfo,String cardidx,String oldaccount,String md5code) {
        initQtPatParams();
        qtpayApplication.setValue("AnewDrawMoney.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("localdate",DataUtil.getRightValue(detailInfo.getLocalDate())));
        qtpayParameterList.add(new Param("locallogno",DataUtil.getRightValue(detailInfo.getLocalLogNo())));
        qtpayParameterList.add(new Param("orderId",DataUtil.getRightValue(detailInfo.getOrderId())));
        qtpayParameterList.add(new Param("cardInfo",cardInfo));
        qtpayParameterList.add(new Param("cardIdx",cardidx));
        qtpayParameterList.add(new Param("account",oldaccount));
        qtpayParameterList.add(new Param("md5code",md5code));
        httpsPost("AnewDrawMoneyTag", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                cashFlowFragment.send(0x333,payResult);
            }
        });
    }
    @Override
    public void doDataRequest(String type, Object data) {

    }
}
