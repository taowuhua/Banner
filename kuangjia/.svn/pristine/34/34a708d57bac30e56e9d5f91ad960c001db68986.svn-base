package com.bank.quickpay.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.login.LoginActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.AppDownLoadDialog;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.interfaces.IPermission;
import com.bank.quickpay.utils.HttpUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.bank.quickpay.utils.UriUtils;
import com.ryx.payment.ryxhttp.callback.FileCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.MessageFormat;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 闪屏
 */
public class SplashActivity extends BaseActivity {
    String updateContent = "";
    String version;
    String updateUrl;
    String must;
    String updateInfo;
    AppDownLoadDialog appDownLoadDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void onPermissionCheckSuccess() {
        super.onPermissionCheckSuccess();
        initQtPatParams();
        doUpdate();
    }
    /**
     * 获取用户须知
     */
    private void getUserInstruction() {
        qtpayApplication.setValue("GetUserInstruction.Req");
        qtpayAttributeList.add(qtpayApplication);
        String instr_version= PreferenceUtil.getInstance(SplashActivity.this).getString("instr_version",
                        "0.0.0");
        Param qtpayInstrVersion = new Param("instrVersion", TextUtils.isEmpty(instr_version)?"0.0.0":instr_version);
        qtpayParameterList.add(qtpayInstrVersion);
        httpsPost("getUserInstruction", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                getUserInstructionFromJson(payResult.getData());
                branchToActivity();
            }
        });
    }
    /**
     * 版本更新检测
     */
    public void doUpdate() {
        qtpayApplication.setValue("ClientUpdate2.Req");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(new Param("flag","1"));//只是捡重要的升级
//        qtpayParameterList.add(new Param("flag","0"));//有任何版本变化都进行返回升级信息
        httpsPost("ClientUpdateTag", new XmlCallback() {

            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                getUpdateInfo(payResult.getData());
            }


        });
    }
    private void getUpdateInfo(String jsonstring) {
        try {
            JSONObject jsonObj = new JSONObject(jsonstring);
            if (AppConfig.QTNET_SUCCESS.equals(jsonObj.getJSONObject(
                    "result").getString("resultCode"))) {
                updateInfo = "发现新版本";
                // 解析更新的信息
                JSONArray resultBeans = jsonObj.getJSONArray("resultBean");
                if (resultBeans != null) {
                    for (int i = 0; i < resultBeans.length(); i++) {
                        updateContent = updateContent
                                + resultBeans.getJSONObject(i).getString(
                                "updateContent") + "\n";
                    }
                    // updateContent = "强制更新\n强制更新\n强制更新";
                    version = jsonObj.getJSONObject("summary").getString(
                            "version");
                    updateUrl = jsonObj.getJSONObject("summary").getString(
                            "updateUrl");
                    must = jsonObj.getJSONObject("summary").getString("must");
//                    updateUrl= "http://ruiyinxin-10007319.file.myqcloud.com/ryx/ruiyinxin.apk";
//                    must="";
                    PreferenceUtil.getInstance(SplashActivity.this)
                            .saveString("version", version);
                    PreferenceUtil.getInstance(SplashActivity.this)
                            .saveString("updateContent", updateContent);
                    PreferenceUtil.getInstance(SplashActivity.this)
                            .saveString("updateUrl", updateUrl);
                    PreferenceUtil.getInstance(SplashActivity.this)
                            .saveString("must", must);
                    showUpdataDialog(); // 需要更新就显示升级对话框
                }
            } else if ("0001".equals(jsonObj.getJSONObject("result").getString(
                    "resultCode"))) {
                getUserInstruction();
            } else if ("0002".equals(jsonObj.getJSONObject("result").getString(
                    "resultCode"))) {
                getUserInstruction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PreferenceUtil.getInstance(SplashActivity.this).saveString(
                    "updateInfo", updateInfo);
        }
    }
    /**
     * 展示下载框
     */
    private void showUpdataDialog() {

        QuickSimpleConfirmDialog ryxSimpleConfirmDialog=new QuickSimpleConfirmDialog(SplashActivity.this,new ConFirmDialogListener(){

            @Override
            public void onPositiveActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
                dirpermissionCheck();
            }
            @Override
            public void onNegativeActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
                if ("n".equals(must))
                {
                    getUserInstruction();
                }
            }
        });
        ryxSimpleConfirmDialog.show();
        ryxSimpleConfirmDialog.setContent(TextUtils.isEmpty(updateContent)? ("n".equals(must) ? "发现新版本是否需要升级?" : "发现新版本请升级！"):updateContent);
        if(!"n".equals(must)){
            ryxSimpleConfirmDialog.setOnlyokLinearlayout();
        }
        ryxSimpleConfirmDialog.setCanceledOnTouchOutside(false);
    }


    /**
     *
     * 文件操作权限判断
     */
    public void dirpermissionCheck(){
        String waring = MessageFormat.format(getResources().getString(R.string.dirwaringmsg), getResources().getString(R.string.app_name));
        final String finalWaring = waring;

        PermissionUtil.checkPermission(SplashActivity.this, new IPermission() {
            @Override
            public void permissionSuccess() {
                downApk(updateUrl);
            }

            @Override
            public void permissionFail() {
                LogUtil.showToast(SplashActivity.this, finalWaring);
            }
        },Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    public void downApk(String url) {
        if(TextUtils.isEmpty(url)||!url.contains("http")){
            LogUtil.showToast(SplashActivity.this,"更新路径有误,请联系客服!!!");
            return ;
        }
//        if (ryxLoadDialogBuilder == null) {
//            ryxLoadDialogBuilder =  new RyxLoadDialog().getInstance(SplashActivity.this);
//        }
//        ryxLoadDialogBuilder.setMessage("当前进度为:0.00%");
//        ryxLoadDialogBuilder.show();
        if(appDownLoadDialog==null){
            appDownLoadDialog= AppDownLoadDialog.simpleShowDownLoadDialog(SplashActivity.this);
        }else{
            appDownLoadDialog.show();
        }
        final String downUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/quickpay/";
        int index = url.lastIndexOf("/");
        final String fname = url.substring(index + 1); // 获取文件名
        HttpUtil.getInstance().httpsFilePost(url, "downApkTag", "", new FileCallBack(downUrl, fname) {
            @Override
            public void inProgress(float progress, long total) {
                LogUtil.showLog("progress=" + progress);
                showProgressView(progress, downUrl + fname);
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtil.showLog("onError=" + e.getMessage());
                appDownLoadDialog.dismiss();
                LogUtil.showToast(SplashActivity.this,"访问服务端超时,请检查网络是否正常!!!");
            }

            @Override
            public void onResponse(File response) {
                LogUtil.showLog("onResponse====");
            }
        });
    }
    private void showProgressView(float progress, String fileName) {
//        DecimalFormat df = new DecimalFormat("#0.00");
        appDownLoadDialog.setProgress((int)(progress*100));
        if (progress == 1) {
            appDownLoadDialog.dismiss();
            installApk(fileName);
        }
    }
    private void installApk(String filename) {
        File file = new File(filename);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(UriUtils.fromFile(file,this), type);
        startActivity(intent);
    }
    private void getUserInstructionFromJson(String jsonData) {

        JSONObject jsonObject = null;
        String phone = "";
        String version = "";
        String instrCode = "";
        String instrContent = "";
        try {
            jsonObject = new JSONObject(jsonData);
            phone = jsonObject.getJSONObject("summary").getString("appPhone");
            version = jsonObject.getJSONObject("summary").getString("version");
            PreferenceUtil.getInstance(SplashActivity.this).saveString(
                    "appPhone", phone);
            PreferenceUtil.getInstance(SplashActivity.this).saveString(
                    "instr_version", version);
            JSONArray instructions = jsonObject.getJSONArray("resultBean");
            for (int i = 0; i < instructions.length(); i++) {
                instrCode = instructions.getJSONObject(i).getString(
                        "instrCode");
                instrContent = instructions.getJSONObject(i).getString(
                        "instrContent");
                LogUtil.showLog("UserInstruction==="+instrCode+","+instrContent);
                PreferenceUtil.getInstance(SplashActivity.this)
                        .saveString(instrCode, instrContent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void branchToActivity() {
        boolean isLogined= QuickPayAppData.getInstance(this).isLogin();
        String user_id=  QuickPayAppData.getInstance(this).getCustomerId();
        if((isLogined&&!"0000".equals(user_id))){
            //当前用户登录过
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else{
            toAgainLogin(SplashActivity.this, AppConfig.TOLOGINACT,true);
        }
        finish();
    }
}
