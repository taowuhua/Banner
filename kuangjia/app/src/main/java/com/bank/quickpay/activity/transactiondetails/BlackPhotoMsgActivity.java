package com.bank.quickpay.activity.transactiondetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.helping.HtmlMessageActivity;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.IPermission;
import com.bank.quickpay.utils.BitmapUntils;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.bank.quickpay.utils.PhoneinfoUtils;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.UriUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rey.material.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上传黑名单照片
 */

public class BlackPhotoMsgActivity extends BaseActivity {

    private String pname,pid,phoneno,smscode,md5Value,localtime,localDate,locallogo,primarykey;
    final int TAKE_PHOTO = 1,TAKE_SIGN=12;
    private String imgTempName = "";
    private Bitmap myBitmap;
    private String signPicAscii;
    @BindView(R.id.agreeCb)
    CheckBox agreeCb;
    @BindView(R.id.iv_sign)
    ImageView iv_sign;
    @BindView(R.id.agreement_tv)
    TextView agreement_tv;
    @BindView(R.id.iv_touxiang)
    ImageView iv_touxiang;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_photo_msg;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("补充资料",true,false);
        initdata();
    }

    @OnClick(R.id.tohandsignll)
    public void tohandsignll(){
        Intent intent=new Intent(BlackPhotoMsgActivity.this,BlackCheckSignActivity.class);
        startActivityForResult(intent, TAKE_SIGN);

    }
    @OnClick(R.id.iv_touxiang)
    public void iv_touxiangClick(){
        takePic();
    }

    @OnClick(R.id.agreement_tv)
    public void agreementtvClick(){
        Intent intent=new Intent(BlackPhotoMsgActivity.this, HtmlMessageActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("title","支付协议");
        bundle.putString("urlkey",AppConfig.Notes_BlackMSG);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 拍照
     */
    public void takePic() {
       final  String   waring = MessageFormat.format(getResources().getString(R.string.camerawritefilewaringmsg),getResources().getString(R.string.app_name));
        PermissionUtil.checkPermission(this,  new IPermission() {

            @Override
            public void permissionSuccess() {
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
                    imgTempName = "/temp_" + format2.format(new Date()) + ".jpg";
                    PreferenceUtil.getInstance(getApplicationContext()).saveString(
                            "imgTempName", imgTempName);
                    File f = new File(Environment.getExternalStorageDirectory(),
                            imgTempName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtils.fromFile(f,BlackPhotoMsgActivity.this));
                    intent.putExtra("scale", true);
                    startActivityForResult(intent, TAKE_PHOTO);
                } else {
                    LogUtil.showToast(BlackPhotoMsgActivity.this, "请检查SD卡是否正常!");
                }
            }

            @Override
            public void permissionFail() {
                LogUtil.showToast(BlackPhotoMsgActivity.this, waring);

            }
        }, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtil.showLog("requestCode=="+requestCode+",resultCode="+resultCode);
        // 检查数据来源；
        switch (requestCode) {
            case TAKE_PHOTO: // 相机返回
            {
                if (resultCode == RESULT_OK) {
                    LogUtil.showLog("相机拍照完毕===========================");
                    showPicFromCamera(null);
                }
                break;
            }
            case TAKE_SIGN:
                if (resultCode == RESULT_OK) {
                    Bundle bundle=data.getExtras();
                    signPicAscii=bundle.getString("signPicAscii");
                    LogUtil.showLog("接收signPicAscii==="+signPicAscii);
                    byte[] signPicAsciiByte=  BitmapUntils.hexStringToBytes(signPicAscii);
                    if(signPicAsciiByte!=null){
                        Bitmap bitmap=   BitmapFactory.decodeByteArray(signPicAsciiByte, 0, signPicAsciiByte.length);
                        iv_sign.setImageBitmap(bitmap);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showPicFromCamera(String filePath) {

        if(TextUtils.isEmpty(filePath)) {
            imgTempName = Environment.getExternalStorageDirectory() +
                    PreferenceUtil.getInstance(getApplicationContext()).getString("imgTempName", "");
        } else {
            imgTempName = filePath;
        }
//=============================图片BitmapFactory加载=========================================
        Glide.with(BlackPhotoMsgActivity.this)
                .load(imgTempName)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget(PhoneinfoUtils.getWindowsWidth(BlackPhotoMsgActivity.this), PhoneinfoUtils.getWindowsHight(BlackPhotoMsgActivity.this)) {

                    @Override
                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                        myBitmap=(Bitmap) resource;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(myBitmap!=null){
                                    iv_touxiang.setImageBitmap(myBitmap);
                                }else{
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, "拍照失败请重新拍照!");
                                }
                            }
                        });

                    }
                });
    }
    /**
     * 参数检查
     * @return
     */
    public boolean checkInput(){
        if(!agreeCb.isChecked()){
            LogUtil.showToast(BlackPhotoMsgActivity.this, "请先同意支付协议!");
            return false;
        }
        return true;
    }
    /**
     * 初始化数据
     */
    private void initdata() {
        super.initQtPatParams();
        qtpayApplication = new Param("application");
        Bundle bundle=	getIntent().getExtras();
        pname=bundle.getString("pname");
        pid=bundle.getString("pid");
        phoneno=bundle.getString("phoneno");
        smscode=bundle.getString("smscode");
        md5Value=bundle.getString("md5Value");

        localtime=bundle.getString("localtime");
        localDate=bundle.getString("localDate");
        locallogo=bundle.getString("locallogo");
        primarykey=bundle.getString("msgid","");
    }

    @OnClick(R.id.bt_next)
    public void btnNextClick(){
        if(checkInput()){
            if(myBitmap==null){
                LogUtil.showToast(BlackPhotoMsgActivity.this, "请上传手持银行卡照片!");
                return;
            }
            if(TextUtils.isEmpty(signPicAscii)){
                LogUtil.showToast(BlackPhotoMsgActivity.this, "请先签名后再上传!");
                return;
            }
            showLoading();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    byte[] bitmapBytes = BitmapUntils.getContentbyCameraPix(myBitmap);
                    Message msg = new Message();
                    msg.what=0x001;
                    Bundle bundle=new Bundle();
                    bundle.putString("handpic",  BitmapUntils
                            .changeBytesToHexString(bitmapBytes));
                    msg.setData(bundle);
                    mhandler.sendMessage(msg);
                }
            }).start();
        }
    }
    private Handler mhandler = new Handler(){

        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0x001:

                    Bundle dataBundle=	msg.getData();
                    String handpicData= dataBundle.getString("handpic","");
                    cancleLoading();

                    qtpayApplication.setValue("UpdateRhdfInfo.Req");
                    qtpayAttributeList.add(qtpayApplication);
                    qtpayParameterList.add(new Param("pid", pid));
                    qtpayParameterList.add(new Param("pname", pname));
                    qtpayParameterList.add(new Param("pphoneno", phoneno));
                    qtpayParameterList.add(new Param("smsCode", smscode));
                    qtpayParameterList.add(new Param("md5Value", md5Value));
                    qtpayParameterList.add(new Param("lcoaldate", localDate));
                    qtpayParameterList.add(new Param("localtime", localtime));
                    qtpayParameterList.add(new Param("locallogno", locallogo));
                    qtpayParameterList.add(new Param("rhdfReviewId", primarykey));
                    qtpayParameterList.add(new Param("handpic", handpicData));
                    qtpayParameterList.add(new Param("signpic", signPicAscii));

                    httpsPost("UpdateRhdfInfoTag", new XmlCallback() {
                        @Override
                        public void onTradeSuccess(QuickPayResult payResult) {
                            String result=payResult.getData();
                            try {
                                JSONObject jsonObj = new JSONObject(result);
                                if ("0000".equals(jsonObj.getString("code"))) {
                                    //资料上传成功
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, "资料上传成功,请耐心等待审核.");
                                    setResult(AppConfig.CLOSE_ALL);
                                }else{
                                    LogUtil.showToast(BlackPhotoMsgActivity.this, jsonObj.getString("msg"));
                                    if("5001".equals(jsonObj.getString("code"))||"5002".equals(jsonObj.getString("code"))){
                                        setResult(AppConfig.CLOSE_ALL);
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            finish();
                        }
                    });


                    break;
            }
        }

    };
}
