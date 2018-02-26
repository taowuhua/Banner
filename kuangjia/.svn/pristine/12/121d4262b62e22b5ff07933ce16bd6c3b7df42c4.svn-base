package com.bank.quickpay.activity.mymsg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.helping.HtmlMessageActivity;
import com.bank.quickpay.adapter.BranchAdapter;
import com.bank.quickpay.adapter.CityAdapter;
import com.bank.quickpay.adapter.ProvinceAdapter;
import com.bank.quickpay.bean.BankCardInfo;
import com.bank.quickpay.bean.CityInfo;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.bean.ProvinceInfo;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.LogUtil;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定结算卡
 */
public class CardBindActivity extends BaseActivity {


    @BindView(R.id.cardnumber_tv)
    TextView cardnumberTv;
    @BindView(R.id.cardbankname_tv)
    TextView cardbanknameTv;
    @BindView(R.id.select_provinces_tv)
    TextView selectProvincesTv;
    @BindView(R.id.select_city_tv)
    TextView selectCityTv;
    @BindView(R.id.select_subbranch_tv)
    TextView selectSubbranchTv;
//    @BindView(R.id.cb_agree)
//    CheckBox mAgreeCb;

    @BindView(R.id.bandcardok_nextbtn)
    Button bandcardok_nextbtn;

    @BindView(R.id.kaihuprovinces_layout)
    RelativeLayout kaihuprovincesLayout;
    @BindView(R.id.kaihucity_layout)
    RelativeLayout kaihucityLayout;
    @BindView(R.id.kaihusubbranch_layout)
    RelativeLayout kaihusubbranchLayout;
    private ArrayList<ProvinceInfo> allcitys = new ArrayList<ProvinceInfo>();
    private String cardnumber, usertype, cardType, bankId, bankName, branchId = "", needbranch = "1", branchid2 = "", hissuers = "";
    public static final int SHOW_PROVINCE = 1;
    public static final int SHOW_CITY = 2;
    private int pPosition;
    ProvinceAdapter provinceAdapter;
    CityAdapter cityAdapter;
    private String bankProvinceId = "";
    private String bankCityId = "";
    private boolean isProvinceSelected = false,
            isCitySelected = false, isBranchSelected = false;
    private String islast = "0";
    private String condition = "";
    private int offset = 0;
    private boolean isRefresh;
    private boolean isBranchDialogshow;
    private LinkedList<BankCardInfo> bankCardInfoList = new LinkedList<BankCardInfo>();
    private MaterialRefreshLayout materialRefreshLayout;
    private com.rey.material.app.Dialog branchDialog;
    BranchAdapter branchAdapter;
    private String cardIdx;
    Param qtpayCardIdx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_bind;
    }

//    @OnClick(R.id.tv_payProtocols)
//    public void tvPayProtocols(){
//        toHtmlMessageAct(AppConfig.LICENSEINFORMATIONAGREEMENT,"快捷支付开通协议");
//    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        cardIdx = getIntent().getExtras().getString("cardIdx");
        if (TextUtils.isEmpty(cardIdx)) {
            setTitleLayout("绑定收款卡", true, false);
            bandcardok_nextbtn.setText("确定");
        } else {
            setTitleLayout("修改收款卡", true, false);
            bandcardok_nextbtn.setText("修改");
        }
        initData();
    }

    private void initData() {
        usertype = getIntent().getExtras().getString("usertype");
        cardnumber = getIntent().getExtras().getString("cardnumber");
        bankName = getIntent().getExtras().getString("bankname");
        cardType = getIntent().getExtras().getString("cardtype");
        bankId = getIntent().getExtras().getString("bankid");
        branchid2 = getIntent().getExtras().getString("branchid2");
        //0不选1是选
        needbranch = getIntent().getExtras().getString("needbranch");
        hissuers = getIntent().getExtras().getString("hissuers");
        cardbanknameTv.setText(bankName);
        cardnumberTv.setText(cardnumber);
        initQtPatParams();
        if ("0".equals(needbranch)) {
            viewGone();
        } else {
            viewVisible();
        }
        initListData();
//        mNextBtn.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            protected void onNoDoubleClick(View view) {
//                btnClick();
//            }
//        });
//        mSendMacTv.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            protected void onNoDoubleClick(View view) {
//                setmSendMacTv();
//            }
//        });

    }

    /**
     * 检查省份城市
     */
    public boolean isParamsOK() {
        if (!isProvinceSelected && bankProvinceId.length() == 0) {
            LogUtil.showToast(CardBindActivity.this,
                    getResources().getString(R.string.please_select_province_bank));
            return false;
        }
        if (!isCitySelected && bankCityId.length() == 0) {
            LogUtil.showToast(CardBindActivity.this,
                    getResources().getString(R.string.please_select_bank_city));
            return false;
        }
        return true;
    }

    private void btnClick() {
        //需要选择支行
        if ("1".equals(needbranch) && !isParamsOK()) {
            return;
        }
//        if (!mAgreeCb.isChecked()) {
//            LogUtil.showToast(this, "请同意快捷支付协议");
//            return;
//        }
        if (isBranchSelected || "0".equals(needbranch)) {
            //新增结算卡
            if (TextUtils.isEmpty(cardIdx)) {
                Bindcard();
            }
            //修改结算卡
            else {
                unBindCard();
            }

        } else {
            LogUtil.showToast(CardBindActivity.this,
                    getResources().getString(R.string.select_the_account_where_the_branch));
        }
    }

    //解除绑定的银行卡接口
    private void unBindCard() {
        initQtPatParams();
        qtpayApplication = new Param("application", "BankCardUnBind.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayCardIdx = new Param("cardIdx", cardIdx);
        qtpayParameterList.add(qtpayCardIdx);
        httpsPost("BankCardUnBind", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                Bindcard();
            }

            @Override
            public void onTradeFailed() {
                LogUtil.showToast(CardBindActivity.this, "修改结算卡失败！");
            }
        });

    }

    private void Bindcard() {
        initQtPatParams();
        qtpayApplication = new Param("application", "BankCardBind.Req");
        Param qtpayBindType = new Param("bindType", "01");
        Param qtpayhissuers = new Param("hissuers", hissuers);
        Param qtpayAccountNo = new Param("accountNo");
        Param qtpayUsertype = new Param("userType", usertype);
//        Param qtPayMobileNumber = new Param("phoneNum", numberString);
        Param qtpayBankId = new Param("bankId");
        if ("0".equals(needbranch)) {
            qtpayBankId.setValue(branchid2);
        } else {
            qtpayBankId.setValue(branchId);
        }
        qtpayAccountNo.setValue(cardnumber);
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayBindType);
//        qtpayParameterList.add(qtPayMobileNumber);
        qtpayParameterList.add(qtpayBankId);
        qtpayParameterList.add(qtpayhissuers);
        qtpayParameterList.add(qtpayAccountNo);
        qtpayParameterList.add(qtpayUsertype);
        qtpayParameterList.add(new Param("cardType", "01"));
        httpsPost("BankCardBind", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                if (TextUtils.isEmpty(cardIdx)) {
                    LogUtil.showToast(CardBindActivity.this, "绑定结算卡成功！");
                } else {
                    LogUtil.showToast(CardBindActivity.this, "修改结算卡成功！");
                }
                setResult(AppConfig.TASKSUCCESS);
                finish();
            }
        });
    }

    //显示分行
    private void showBranchDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        AutoLinearLayout layout = (AutoLinearLayout) inflater.inflate(R.layout.bind_card_branch_bank_list, null);
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder =
                new com.rey.material.app.Dialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog);
        branchDialog = builder.build(CardBindActivity.this);
        materialRefreshLayout = (MaterialRefreshLayout) layout.findViewById(R.id.materialRefreshLayout);
        final EditText edt_condition = (EditText) layout.findViewById(R.id.edt_condition);
        ImageView btn_search = (ImageView) layout.findViewById(R.id.btn_search);
        ListView lv_bank = (ListView) layout.findViewById(R.id.lv_bank);
        ImageView imgview_close = (ImageView) layout.findViewById(R.id.imgview_close);
        imgview_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branchDialog.dismiss();
            }
        });
        if (branchAdapter == null) {
            branchAdapter = new BranchAdapter(CardBindActivity.this);
        }
        branchAdapter.setList(bankCardInfoList);
        lv_bank.setAdapter(branchAdapter);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankCardInfoList != null) {
                    bankCardInfoList.clear();
                    branchAdapter.notifyDataSetChanged();
                    offset = 0;
                    condition = edt_condition.getText().toString();
                    islast = "0";
                    initBranchList();
                }
            }
        });
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = true;
                initBranchList();
            }

        });
        lv_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                branchDialog.dismiss();
                isBranchDialogshow = false;
                isBranchSelected = true;
                branchId = bankCardInfoList.get(position).getBranchBankId();
                selectSubbranchTv.setText(bankCardInfoList.get(position).getBranchBankName());
            }
        });
        branchDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isBranchDialogshow = false;
            }
        });
        branchDialog.setContentView(layout);
        branchDialog.show();
        isBranchDialogshow = true;
    }


    /**
     * 获取城市列表数据
     */
    public String getRawCitys() {
        InputStream in = getResources().openRawResource(R.raw.cities);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            br.close();
            isr.close();
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    public void initListData() {
        // 获取中国省市区信息
        String jsonString = getRawCitys().toString();
        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            // 获得一个省的数组
            JSONArray citysArray = jsonObj.getJSONArray("resultBean");
            for (int i = 0; i < citysArray.length(); i++) {
                // 创建一个新的省份
                ProvinceInfo provinceinfo = new ProvinceInfo();
                JSONObject province = citysArray.getJSONObject(i);

                JSONArray citys = province.getJSONArray("citys");
                // 创建该省的城市列表list
                ArrayList<CityInfo> cityslist = new ArrayList<CityInfo>();
                for (int j = 0; j < citys.length(); j++) {
                    JSONObject cityjson = citys.getJSONObject(j);
                    CityInfo city = new CityInfo(
                            cityjson.getString("cityCode"),
                            cityjson.getString("cityName"));
                    cityslist.add(city);
                }
                provinceinfo
                        .setProvinceCode(province.getString("provinceCode"));
                provinceinfo
                        .setProvinceName(province.getString("provinceName"));
                provinceinfo.setCityslist(cityslist);
                allcitys.add(provinceinfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.kaihuprovinces_layout)
    public void searchProvince() {
        creatDialog(SHOW_PROVINCE);
    }

    @OnClick(R.id.select_city_tv)
    public void searchCity() {
        if (isProvinceSelected) {
            creatDialog(SHOW_CITY);
        } else {
            LogUtil.showToast(CardBindActivity.this, getResources().getString(R.string.please_select_province_bank));
        }
    }

    @OnClick(R.id.select_subbranch_tv)
    public void searchBranch() {
        islast = "0";
        condition = "";
        offset = 0;
        isRefresh = false;
        isBranchDialogshow = false;
        bankCardInfoList.clear();
        if (isParamsOK()) {
            initBranchList();
        }
    }

    private void initBranchList() {
        if (!"0".equals(islast)) {
            LogUtil.showToast(CardBindActivity.this, "无记录！");
            if (isRefresh) {
                materialRefreshLayout.finishRefreshLoadMore();
            }
            return;
        }
        initQtPatParams();
        if (isRefresh) {
            isNeedThread = false;
        }
        qtpayApplication = new Param("application", "GetBankBranch.Req");
        Param qtpayBankId = new Param("bankId");
        Param qtpayBankCityId = new Param("bankCityId");
        Param qtpayBankProvinceId = new Param("bankProvinceId");
        Param qtpayCondition = new Param("condition");
        Param qtpayOffset = new Param("offset");
        qtpayCondition.setValue(condition);
        qtpayBankId.setValue(bankId.length() > 3 ? bankId.substring(1, 4) : bankId);
        qtpayBankCityId.setValue(bankCityId);
        qtpayBankProvinceId.setValue(bankProvinceId);
        qtpayOffset.setValue(offset + "");
        offset = offset + 20;
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayBankId);
        qtpayParameterList.add(qtpayBankCityId);
        qtpayParameterList.add(qtpayBankProvinceId);
        qtpayParameterList.add(qtpayCondition);
        qtpayParameterList.add(qtpayOffset);
        httpsPost("GetBankBranch", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                if (isRefresh) {
                    materialRefreshLayout.finishRefreshLoadMore();
                    isRefresh = false;
                }
                if (payResult.getData() != null) {
                    LinkedList<BankCardInfo> list = getSubBranchList(payResult.getData());
                    if (list != null && list.size() > 0) {
                        bankCardInfoList.addAll(list);
                        list = null;
                        if (branchAdapter == null) {
                            branchAdapter = new BranchAdapter(CardBindActivity.this);
                        }
                        branchAdapter.notifyDataSetChanged();
                        if (!isBranchDialogshow) {
                            showBranchDialog();
                        }
                    }
                }
            }

            @Override
            public void onOtherState() {
                super.onOtherState();
                if (isRefresh) {
                    materialRefreshLayout.finishRefreshLoadMore();
                    isRefresh = false;
                }
            }

            @Override
            public void onTradeFailed() {
                super.onTradeFailed();
                if (isRefresh) {
                    materialRefreshLayout.finishRefreshLoadMore();
                    isRefresh = false;
                }
            }
        });
    }

    public LinkedList<BankCardInfo> getSubBranchList(String jsonstring) {
        LinkedList<BankCardInfo> list = null;
        String toastmsg = "";
        try {
            if (!"1".equals(islast)) {    // 如果不是最后一页，才可以进一步加载更多
                JSONObject jsonObj = new JSONObject(jsonstring);
                islast = (String) jsonObj.getJSONObject("summary").getString("isLast");

                toastmsg = (String) jsonObj.getJSONObject("result").getString("message");
                if (AppConfig.QTNET_SUCCESS.equals(jsonObj.getJSONObject("result").getString("resultCode"))) {
                    // 解析银行信息
                    JSONArray banks = jsonObj.getJSONArray("resultBean");
                    list = new LinkedList<BankCardInfo>();
                    for (int i = 0; i < banks.length(); i++) {
                        BankCardInfo cardinfo = new BankCardInfo();
                        cardinfo.setBranchBankName(banks.getJSONObject(i).getString("bankName"));
                        cardinfo.setBranchBankId(banks.getJSONObject(i).getString("bankId"));
                        list.add(cardinfo);
                        cardinfo = null;
                    }
                    return list;
                } else {
                    LogUtil.showToast(CardBindActivity.this, toastmsg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void creatDialog(final int dialogType) {
        if (provinceAdapter == null) {
            provinceAdapter = new ProvinceAdapter(CardBindActivity.this, allcitys);
        }
        if (cityAdapter == null) {
            cityAdapter = new CityAdapter(CardBindActivity.this);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        AutoLinearLayout layout = (AutoLinearLayout) inflater.inflate(R.layout.bind_debit_card_select_povince, null);
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder =
                new com.rey.material.app.Dialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog);
        final com.rey.material.app.Dialog dialog = builder.build(CardBindActivity.this);
        ImageView imgview_close = (ImageView) layout.findViewById(R.id.imgview_close);
        imgview_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ListView lv_bank = (ListView) layout.findViewById(R.id.lv_location);
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
        if (dialogType == SHOW_PROVINCE) {
            tv_title.setText(getResources().getString(R.string.please_select_a_provinces));
            lv_bank.setAdapter(provinceAdapter);
        } else {
            tv_title.setText(getResources().getString(R.string.please_select_a_city));
            cityAdapter.setList(allcitys.get(pPosition).getCityslist());
            lv_bank.setAdapter(cityAdapter);
        }

        lv_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (dialogType == SHOW_PROVINCE) {
                    pPosition = position;
                    bankProvinceId = allcitys.get(pPosition).getProvinceCode();
                    selectProvincesTv.setText(allcitys.get(pPosition).getProvinceName());
                    // 判断该省下是否有市级
                    if (allcitys.get(pPosition).getCityslist().size() < 1) {
                        selectCityTv.setText("");

                    } else {
                        selectCityTv.setText(allcitys.get(pPosition).getCityslist()
                                .get(0).getCityName());
                        bankCityId = allcitys.get(pPosition).getCityslist()
                                .get(0).getCityCode();
                    }
                    isProvinceSelected = true;
                    isCitySelected = false;
                    isBranchSelected = false;
                    bankCityId = "";
                    selectSubbranchTv.setText("");
                    creatDialog(SHOW_CITY);
                } else {
                    bankCityId = allcitys.get(pPosition).getCityslist()
                            .get(position).getCityCode();
                    selectCityTv.setText(allcitys.get(pPosition).getCityslist()
                            .get(position).getCityName());
                    isCitySelected = true;
                    isBranchSelected = false;
                    selectSubbranchTv.setText("");
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(layout);
        dialog.show();
    }

    /**
     * 支行选择隐藏
     */
    private void viewGone() {
        kaihuprovincesLayout.setVisibility(View.GONE);
        kaihucityLayout.setVisibility(View.GONE);
        kaihusubbranchLayout.setVisibility(View.GONE);
    }

    /**
     * 支行选择展示
     */
    private void viewVisible() {
        kaihuprovincesLayout.setVisibility(View.VISIBLE);
        kaihucityLayout.setVisibility(View.VISIBLE);
        kaihusubbranchLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bandcardok_nextbtn)
    public void onViewClicked() {
        btnClick();
    }

}
