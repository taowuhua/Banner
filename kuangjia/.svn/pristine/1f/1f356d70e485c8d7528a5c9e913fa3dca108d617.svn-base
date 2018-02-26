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
import com.bank.quickpay.utils.IDCardUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 填写实名认证信息
 */
public class AuthActivity extends BaseActivity {


    @BindView(R.id.auth_nextbtn)
    Button authNextbtn;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_idcardnumber)
    EditText edtIdcardnumber;
    private String userRealName, userIdNo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("实名认证", true, false);
    }


    @OnClick(R.id.auth_nextbtn)
    public void onViewClicked() {
        if (checkInput()) {
            initQtPatParams();
            UploadUserInfoRequest(userRealName, userIdNo);
        }
    }

    private void UploadUserInfoRequest(String realName, String certPid) {
        qtpayApplication = new Param("application", "UserUpdateInfo.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("certType", "01"));
        qtpayParameterList.add(new Param("userType", "00"));
        qtpayParameterList.add(new Param("email", "test@163.com"));
        qtpayParameterList.add(new Param("merchantName", ""));
        qtpayParameterList.add(new Param("merchantAddres", ""));
        qtpayParameterList.add(new Param("realName", realName));
        qtpayParameterList.add(new Param("certPid", certPid));
        httpsPost("UserUpdateInfo", new XmlCallback() {

            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                QuickPayAppData.getInstance(AuthActivity.this).setAuthenFlag(
                        payResult.getAuthenFlag());
                LogUtil.showToast(AuthActivity.this, "实名认证信息提交成功!");
                Intent intent = new Intent(AuthActivity.this, AuthPhotoUploadActivity.class);
                startActivityForResult(intent, AppConfig.TO_UPLOAD);

            }
        });
    }

    private boolean checkInput() {
        userRealName = edtUsername.getText().toString() + "";
        userIdNo = edtIdcardnumber.getText().toString() + "";
        //判断用户是否填写了姓名
        if (TextUtils.isEmpty(userRealName)) {
            LogUtil.showToast(AuthActivity.this, getResources().getString(R.string.please_enter_correct_name));
            return false;
        }
        //判断用户是否填写了身份证号码
        if (!IDCardUtil.isIDCard(userIdNo)) {
            LogUtil.showToast(AuthActivity.this, getResources().getString(R.string.please_enter_the_correct_id_number));
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConfig.TO_UPLOAD&&resultCode==AppConfig.UPLOAD_FINISH) {
            setResult(AppConfig.TASKSUCCESS);
            finish();
        }else if(requestCode == AppConfig.TO_UPLOAD&&resultCode==AppConfig.UPLOAD_BACK){
            setResult(AppConfig.UPLOAD_BACK);
            finish();
        }
    }
}
