package com.ryx.credit.tnh.ui.demo;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;

import com.google.gson.GsonBuilder;
import com.ryx.baselib.interfaces.IPermission;
import com.ryx.baselib.mvpframe.exception.MvpServerException;
import com.ryx.baselib.utils.PermissionUtil;
import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseAct;
import com.ryx.credit.tnh.model.base.RequestBase;
import com.ryx.credit.tnh.ui.demo.bean.TestModel;
import com.ryx.credit.tnh.utils.CryptoUtils;
import com.ryx.credit.tnh.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoMainAct extends BaseAct<DemoPresenter, DemoModel>
        implements DemoContract.View {
    @BindView(R.id.netbtn)
    Button netbtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo_main;
    }


    @OnClick(R.id.netbtn)
    public void onViewClicked() {

        LogUtil.showLog("111111111111111111111111");
        String transDate = CryptoUtils.getInstance().getTransDate();
        String transTime = CryptoUtils.getInstance().getTransTime();
        RequestBase requestBase = new RequestBase();
        requestBase.setAppUser("kjfx");
        requestBase.setMobileSerialNum("web0000000000000000000000000000000000000");
        requestBase.setClientType("11");
        requestBase.setLongitude("117.14834");
        requestBase.setLatitude("36.6643");
        requestBase.setToken("simple");
        requestBase.setVersion("4.2.0");
        requestBase.setOsType("unknown");
        requestBase.setApplication("GetMobileMac.Req");
        requestBase.setCustomerId("");
        requestBase.setUserIP("");
        requestBase.setPhone("");
        requestBase.setTransDate(transDate);
        requestBase.setTransTime(transTime);
        requestBase.setTransLogNo("3");
        requestBase.setSign("<sign>s0mvr01ga0mavhiiqh36lvl9ah800321</sign>");

        TestModel testModel = new TestModel();
        testModel.setMobileNo("18769798020");
        testModel.setAppType("UserRegister");
        requestBase.setBody(testModel);
        String all = new GsonBuilder().disableHtmlEscaping().create().toJson(requestBase);
        String newSign = CryptoUtils.getInstance().EncodeDigest(all);
        all = all.replaceAll("<sign>s0mvr01ga0mavhiiqh36lvl9ah800321</sign>", newSign);
        LogUtil.showLog(new GsonBuilder().disableHtmlEscaping().create().toJson(requestBase));

        final String finalAll = all;
        PermissionUtil.checkPermission(DemoMainAct.this, new IPermission() {
            @Override
            public void permissionSuccess() {
                mPresenter.getMobileMac(finalAll);
            }

            @Override
            public void permissionFail() {

            }
        }, Manifest.permission.INTERNET);




    }

    @Override
    public void getMobileMacSuccess(String result) {

        LogUtil.showLog("getMobileMacSuccess=="+result);
    }

    @Override
    public void getMobileMacBusinessFailed(MvpServerException ex) {
        LogUtil.showLog("getMobileMacFailed=="+ex.toString());
    }
}
