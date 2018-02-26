package com.bank.quickpay.activity.mymsg;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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
import com.bank.quickpay.utils.CryptoUtils;
import com.bank.quickpay.utils.DateUtil;
import com.bank.quickpay.utils.ImageLoaderUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.bank.quickpay.utils.PhoneinfoUtils;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.UriUtils;
import com.bank.quickpay.widget.ProgressLoadDialog;
import com.bank.quickpay.widget.ProgressLoadDialogBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 上传身份证照片
 * xiepingping
 */
public class AuthPhotoUploadActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tileleftImg)
    ImageView tileleftImg;
    @BindView(R.id.tilerightImg)
    ImageView tilerightImg;

    @BindView(R.id.iv_idcard_front)
    ImageView iv_idcard_front;
    @BindView(R.id.iv_idcard_back)
    ImageView iv_idcard_back;
    @BindView(R.id.iv_idcard_hold)
    ImageView iv_idcard_hold;

    @BindView(R.id.iv_check_front)
    ImageView iv_check_front;
    @BindView(R.id.iv_check_back)
    ImageView iv_check_back;
    @BindView(R.id.iv_check_hold)
    ImageView iv_check_hold;

    @BindView(R.id.btn_next)
    Button btn_next;

    private String imgTempName = "";
    private Bitmap myBitmap1, myBitmap2, myBitmap3;
    boolean hasFisrtPic = false, hasSecondPic = false, hasThirdPic = false;
    //    private String imgProfile = "", imgCardName = "", imgCardReverseName = "";
    String[] upindexs = {"01", "02", "03"};
    boolean[] isokflag = {false, false, false};
    final int START_UPLOADED = 0; // 准备上传照片，展示进度条
    final int FIRST_UPLOADED_SUCCESS = 1; // 第 1 张照片上传成功
    final int SECOND_UPLOADED_SUCCESS = 2; // 第 2 张照片上传成功
    final int THRID_UPLOADED_SUCCESS = 3; // 第 3 张照片上传成功
    final int UPLOADED_FINISH = 4; // 上传完毕，关掉进度条
    int currentindex = 0; // 当前索引
    String[] infos = {"", "", ""};
    Param qtpayImg;
    Param qtpayImgApplyType;
    Param qtpayImgSign;
    int selectiv;
    final int TAKE_PHOTO = 11;
    ProgressLoadDialogBuilder ryxLoadDialogBuilder;
    private String powermsg, rmsmsg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_auth_photo_upload;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initQtPatParams();
        setTitleLayout("实名认证", true, true);
    }

    @Override
    protected void backUpImgOnclickListen() {
        setResult(AppConfig.UPLOAD_BACK);
        finish();
    }

    @Override
    public void initQtPatParams() {
        super.initQtPatParams();
        isNeedThread = false;
        qtpayApplication = new Param("application",
                "UserIdentityPicUpload2.Req");
        qtpayImg = new Param("img");
        qtpayImgApplyType = new Param("imgApplyType");
        qtpayImgSign = new Param("imgSign");

    }

    @OnClick(R.id.tilerightImg)
    public void showHelp() {
        toHtmlMessageAct(AppConfig.UPLOADIMAGE,"拍照指南");
    }

    @OnClick(R.id.iv_idcard_front)
    public void showFrontPhoto() {
        selectiv = 1;
        takePhoto();
    }

    @OnClick(R.id.iv_idcard_back)
    public void showbackPhoto() {
        selectiv = 2;
        takePhoto();
    }

    @OnClick(R.id.iv_idcard_hold)
    public void getHoldPhoto() {
        selectiv = 3;
        takePhoto();
    }

    private void takePhoto() {
        PreferenceUtil.getInstance(AuthPhotoUploadActivity.this).saveInt("selectiv", selectiv);
        //弹出对话框
        requestCamera();
    }

    @OnClick(R.id.tileleftImg)
    public void closeWindow() {
        restShareData();
        setResult(AppConfig.UPLOAD_BACK);
        finish();
    }

    @OnClick(R.id.btn_next)
    public void nextPage() {
        if (AppConfig.getInstance(AuthPhotoUploadActivity.this).isLogin()) {
            isNeedThread = false;
            qtpayApplication.setValue("UserIdentityPicUpload2.Req");

            if (hasFisrtPic == true && hasSecondPic == true
                    && hasThirdPic == true) {
                if (isokflag[0] && isokflag[1] && isokflag[2]) {
                    btn_next.setEnabled(false);
                    //当前照片上传完毕,直接请求新增条件判断
                    LogUtil.showToast(AuthPhotoUploadActivity.this, "图片全部上传完毕");
                    //进行是否符合新增条件状态判断
                    setResult(AppConfig.UPLOAD_FINISH);
                    finish();
                } else {
                    myhandler.sendEmptyMessage(START_UPLOADED); // 初始化提示框
                    //设置不可点击下一步按钮
                    btn_next.setEnabled(false);
                    uploadPics();
                }

            } else {
                LogUtil.showToast(AuthPhotoUploadActivity.this, getResources()
                        .getString(R.string.please_upload_all_pic));
            }
        } else {
            LogUtil.showToast(getApplicationContext(),
                    getResources().getString(R.string.please_login_first));
        }
    }

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START_UPLOADED: // 显示
                    if (ryxLoadDialogBuilder == null) {
                        ryxLoadDialogBuilder = new ProgressLoadDialog().getInstance(AuthPhotoUploadActivity.this);
                        ryxLoadDialogBuilder.setCancelable(false);
                    }
                    ryxLoadDialogBuilder.setMessage("当前进度" + currentindex
                            + "/3,上传过程中请不要关闭此页面");
                    ryxLoadDialogBuilder.show();
                    break;
                case FIRST_UPLOADED_SUCCESS: // 更新
                    ryxLoadDialogBuilder.setMessage("当前进度" + 1 + "/3,上传过程中请不要关闭此页面");
                    uploadPics();
                    break;
                case SECOND_UPLOADED_SUCCESS: // 更新
                    ryxLoadDialogBuilder.setMessage("当前进度" + 2 + "/3,上传过程中请不要关闭此页面");
                    uploadPics();
                    break;
                case THRID_UPLOADED_SUCCESS: // 关闭
                    ryxLoadDialogBuilder.setMessage("当前进度" + 3 + "/3,上传过程中请不要关闭此页面");
                    ryxLoadDialogBuilder.dismiss();
                    if (isUploadSuccess()) {
                        AppConfig.getInstance(AuthPhotoUploadActivity.this).setAuthenFlag(2);
                        LogUtil.showToast(AuthPhotoUploadActivity.this,"图片全部上传完毕！");
                        //进行是否符合新增条件状态判断
                        setResult(AppConfig.UPLOAD_FINISH);
                        finish();
                    } else {
                        btn_next.setEnabled(true);
                    }
                    break;
            }
        }
    };

    /**
     * 判断是否全部上传成功
     *
     * @author tianyingzhong <br/>
     */
    public boolean isUploadSuccess() {
        String msginfo = "";
        for (int i = 0; i < upindexs.length; i++) {
            if (isokflag[i] == false) {
                msginfo = msginfo + "第" + (i + 1) + "张照片上传失败，失败原因：" + infos[i]
                        + "\r\n";
            }
        }
        if (isokflag[0] && isokflag[1] && isokflag[2]) {
            return true;
        } else {
            LogUtil.showToast(AuthPhotoUploadActivity.this, msginfo);
            return false;
        }

    }

    /**
     * 上传照片
     */
    public void uploadPics() {
        if (!isokflag[currentindex]) {
            qtpayAttributeList.add(qtpayApplication);
            byte[] imgBytes = null;
            if (currentindex == 0) {
                imgBytes = BitmapUntils.getContent(myBitmap1);
            } else if (currentindex == 1) {
                imgBytes = BitmapUntils.getContent(myBitmap2);
            } else if (currentindex == 2) {
                imgBytes = BitmapUntils.getContent(myBitmap3);
            }
            qtpayImg.setValue(BitmapUntils.bytesToHexString(imgBytes)); // 图片传输：采用Base64编码
            qtpayImgApplyType.setValue(upindexs[currentindex]);// 01：身份证正面02：身份证反面03：脸部头像（按顺序上传）
            if (imgBytes == null) {
                qtpayImgSign.setValue(""); // 原图片文件内容MD5（转码前计算）
            } else {
                String result = CryptoUtils.getInstance().EncodeDigest(imgBytes);
                if (TextUtils.isEmpty(result)) {
                    result = "";
                }
                qtpayImgSign.setValue(result);
            }
            qtpayParameterList.add(qtpayImg);
            qtpayParameterList.add(qtpayImgApplyType);
            qtpayParameterList.add(qtpayImgSign);
            String addresss = PreferenceUtil.getInstance(AuthPhotoUploadActivity.this).getString("addresss", "");
            String validDateStart = PreferenceUtil.getInstance(AuthPhotoUploadActivity.this).getString("validDateStart", "");
            String validDateEnd = PreferenceUtil.getInstance(AuthPhotoUploadActivity.this).getString("validDateEnd", "");
            qtpayParameterList.add(new Param("addresss", addresss));
            qtpayParameterList.add(new Param("validDateStart", validDateStart));
            qtpayParameterList.add(new Param("validDateEnd", validDateEnd));
            LogUtil.showLog("addresss---validDateEnd", addresss + "---" + validDateStart + "---" + validDateEnd);
            imgBytes = null;
            httpsPost("UserIdentityPicUpload2", new XmlCallback() {
                @Override
                public void onLoginAnomaly() {
                    ryxLoadDialogBuilder.dismiss();
                }

                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
                    isokflag[currentindex] = true;
                    if (null != payResult) {
                        infos[currentindex] = payResult.getRespDesc();
                    }
                    currentindex = currentindex + 1;
                    myhandler.sendEmptyMessage(currentindex); // 提示在第几张更新成功

                }

                @Override
                public void onOtherState() {
                    btn_next.setEnabled(true);
                    ryxLoadDialogBuilder.dismiss();
                }

                @Override
                public void onTradeFailed() {
                    btn_next.setEnabled(true);
                    ryxLoadDialogBuilder.dismiss();
                }
            });

        }
    }

    public void requestCamera() {
        final String waring = MessageFormat.format(getResources().getString(R.string.camerawaringmsg), getResources().getString(R.string.app_name));
        PermissionUtil.checkPermission(this, new IPermission() {
                    @Override
                    public void permissionSuccess() {
                        requestStrorage();
                    }

                    @Override
                    public void permissionFail() {
                        LogUtil.showToast(AuthPhotoUploadActivity.this, waring);
                    }
                },
                Manifest.permission.CAMERA);

    }

    private void requestStrorage() {
        final String waring = MessageFormat.format(getResources().getString(R.string.dirwaringmsg), getResources().getString(R.string.app_name));
        PermissionUtil.checkPermission(this, new IPermission() {
                    @Override
                    public void permissionSuccess() {
                        if (ImageLoaderUtil.avaiableSdcard()) {
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
                            imgTempName = "/temp_" + format2.format(new Date()) + ".jpg";
                            PreferenceUtil.getInstance(getApplicationContext())
                                    .saveString(AppConfig.TEMP_IMAGENAME, imgTempName);
                            //身份证正面照和背面照，手持身份证拍照
                            File f = new File(Environment.getExternalStorageDirectory(), imgTempName);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 针对7.0可能存在调用方面问题处理
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtils.fromFile(f,
                                    AuthPhotoUploadActivity.this));
                            intent.putExtra("scale", true);
                            startActivityForResult(intent, TAKE_PHOTO);
                        } else {
                            LogUtil.showToast(AuthPhotoUploadActivity.this, "请确保SD卡是否存在!");
                        }
                    }

                    @Override
                    public void permissionFail() {
                        LogUtil.showToast(AuthPhotoUploadActivity.this, waring);
                    }
                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void newshowPicFromCamera() {
        imgTempName = Environment.getExternalStorageDirectory() +
                    PreferenceUtil.getInstance(getApplicationContext())
                            .getString(AppConfig.TEMP_IMAGENAME, "");
        selectiv = PreferenceUtil.getInstance(getApplicationContext())
                .getInt("selectiv", 1);
        switch (selectiv) {
            case 1: {
                if (!TextUtils.isEmpty(imgTempName)) {
                    PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_IDENTITY1, imgTempName);
                }
                break;
            }
            case 2: {
                if (!TextUtils.isEmpty(imgTempName)) {
                    PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_IDENTITY2, imgTempName);
                }
                break;
            }
            case 3: {
                if (!TextUtils.isEmpty(imgTempName)) {
                    PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_PROFILE, imgTempName);
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String identity_img1 = PreferenceUtil.getInstance(getApplicationContext()).getString(AppConfig.IMAG_IDENTITY1, "");
        LogUtil.showLog("onResume1---", identity_img1 + "---");
        if (!TextUtils.isEmpty(identity_img1)) {
            Glide.with(AuthPhotoUploadActivity.this)
                    .load(identity_img1)
                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(new SimpleTarget(PhoneinfoUtils.getWindowsWidth(AuthPhotoUploadActivity.this), PhoneinfoUtils.getWindowsHight(AuthPhotoUploadActivity.this)) {
                        @Override
                        public void onResourceReady(final Object resource, GlideAnimation glideAnimation) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myBitmap1 = (Bitmap) resource;
                                    LogUtil.showLog("onResume---myBitmap1", myBitmap1 + "---");
                                    if (myBitmap1 != null) {
                                        hasFisrtPic = true;
                                        iv_check_front.setVisibility(View.VISIBLE);
                                        int imgwith = PreferenceUtil.getInstance(getApplicationContext()).getInt("IDfront_width", 0);
                                        int imgHeight = PreferenceUtil.getInstance(getApplicationContext()).getInt("IDfront_height", 0);
                                        if (imgwith == 0 || imgHeight == 0) {
                                        //控件加载完之后，再获取控件宽高
                                        iv_idcard_front.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                            @Override
                                            public void onGlobalLayout() {
                                                // Removing layout listener to avoid multiple calls
                                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                                    iv_idcard_front.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                                } else {
                                                    iv_idcard_front.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                                }
                                                Bitmap myBitmap1thumbnail = ThumbnailUtils.extractThumbnail(myBitmap1, iv_idcard_front.getWidth(), iv_idcard_front.getHeight());
                                                PreferenceUtil.getInstance(getApplicationContext()).saveInt("IDfront_width", iv_idcard_front.getWidth());
                                                PreferenceUtil.getInstance(getApplicationContext()).saveInt("IDfront_height", iv_idcard_front.getHeight());
                                                iv_idcard_front.setImageBitmap(myBitmap1thumbnail == null ? myBitmap1 : myBitmap1thumbnail);
                                            }
                                        });
                                    } else {
                                        Bitmap myBitmap1thumbnail = ThumbnailUtils.extractThumbnail(myBitmap1, imgwith, imgHeight);
                                        iv_idcard_front.setImageBitmap(myBitmap1thumbnail == null ? myBitmap1 : myBitmap1thumbnail);
                                    }
                                    }else {
                                        LogUtil.showToast(AuthPhotoUploadActivity.this, "身份证正面照片加载失败!");
                                    }
                                }
                            });
                        }
                    });
        }
        String identity_img2 = PreferenceUtil.getInstance(getApplicationContext()).getString(AppConfig.IMAG_IDENTITY2, "");
        LogUtil.showLog("onResume2---", identity_img2 + "---");
        if (!TextUtils.isEmpty(identity_img2)) {
            Glide.with(AuthPhotoUploadActivity.this)
                    .load(identity_img2)
                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(new SimpleTarget(PhoneinfoUtils.getWindowsWidth(AuthPhotoUploadActivity.this), PhoneinfoUtils.getWindowsHight(AuthPhotoUploadActivity.this)) {
                        @Override
                        public void onResourceReady(final Object resource, GlideAnimation glideAnimation) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myBitmap2 = (Bitmap) resource;
                                    LogUtil.showLog("onResume---myBitmap2", myBitmap2 + "---");
                                    if (myBitmap2 != null) {
                                        hasSecondPic = true;
                                        iv_check_back.setVisibility(View.VISIBLE);
                                        int imgwith = PreferenceUtil.getInstance(getApplicationContext()).getInt("IDback_width", 0);
                                        int imgHeight = PreferenceUtil.getInstance(getApplicationContext()).getInt("IDback_height", 0);
                                        if (imgwith == 0 || imgHeight == 0) {
                                        iv_idcard_back.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                            @Override
                                            public void onGlobalLayout() {
                                                // Removing layout listener to avoid multiple calls
                                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                                    iv_idcard_back.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                                } else {
                                                    iv_idcard_back.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                                }
                                                PreferenceUtil.getInstance(getApplicationContext()).saveInt("IDback_width", iv_idcard_back.getWidth());
                                                PreferenceUtil.getInstance(getApplicationContext()).saveInt("IDback_height", iv_idcard_back.getHeight());
                                                Bitmap myBitmap2thumbnail = ThumbnailUtils.extractThumbnail(myBitmap2, iv_idcard_back.getWidth(), iv_idcard_back.getHeight());
                                                iv_idcard_back.setImageBitmap(myBitmap2thumbnail == null ? myBitmap2 : myBitmap2thumbnail);
                                            }
                                        });
                                        } else {
                                            Bitmap myBitmap2thumbnail = ThumbnailUtils.extractThumbnail(myBitmap2, imgwith, imgHeight);
                                            iv_idcard_back.setImageBitmap(myBitmap2thumbnail == null ? myBitmap2 : myBitmap2thumbnail);
                                        }
                                    } else {
                                        LogUtil.showToast(AuthPhotoUploadActivity.this, "身份证正面照片加载失败!");
                                    }
                                }
                            });
                        }
                    });
        }

        //手持身份证照片，显示
        String profile_img = PreferenceUtil.getInstance(getApplicationContext()).getString(AppConfig.IMAG_PROFILE, "");
        if (!TextUtils.isEmpty(profile_img)) {
            LogUtil.showLog("onResume=======profile_img=" + profile_img + "," + DateUtil.getDateTime(new Date()));
            Glide.with(AuthPhotoUploadActivity.this)
                    .load(profile_img)
                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(new SimpleTarget(PhoneinfoUtils.getWindowsWidth(AuthPhotoUploadActivity.this), PhoneinfoUtils.getWindowsHight(AuthPhotoUploadActivity.this)) {
                        @Override
                        public void onResourceReady(final Object resource, GlideAnimation glideAnimation) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myBitmap3 = (Bitmap) resource;
                                    if (myBitmap3 != null) {
                                        hasThirdPic = true;
                                        iv_check_hold.setVisibility(View.VISIBLE);
                                        int imgwith = PreferenceUtil.getInstance(getApplicationContext()).getInt("profile_width", 0);
                                        int imgHeight = PreferenceUtil.getInstance(getApplicationContext()).getInt("profile_height", 0);
                                        if (imgwith == 0 || imgHeight == 0) {
                                            iv_idcard_hold.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                                @Override
                                                public void onGlobalLayout() {
                                                    // Removing layout listener to avoid multiple calls
                                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                                        iv_idcard_hold.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                                    } else {
                                                        iv_idcard_hold.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                                    }
                                                    LogUtil.showLog("onGlobalLayout---", "onGlobalLayout");
                                                    PreferenceUtil.getInstance(getApplicationContext()).saveInt("profile_width", iv_idcard_hold.getWidth());
                                                    PreferenceUtil.getInstance(getApplicationContext()).saveInt("profile_height", iv_idcard_hold.getHeight());
                                                    Bitmap myBitmap3thumbnail = ThumbnailUtils.extractThumbnail(myBitmap3, iv_idcard_hold.getWidth(), iv_idcard_hold.getHeight());
                                                    iv_idcard_hold.setImageBitmap(myBitmap3thumbnail == null ? myBitmap3 : myBitmap3thumbnail);
                                                }
                                            });
                                        } else {
                                            Bitmap myBitmap3thumbnail = ThumbnailUtils.extractThumbnail(myBitmap3, imgwith, imgHeight);
                                            iv_idcard_hold.setImageBitmap(myBitmap3thumbnail == null ? myBitmap3 : myBitmap3thumbnail);
                                        }
                                    } else {
                                        LogUtil.showToast(AuthPhotoUploadActivity.this, "手持身份证照片加载失败!");
                                    }
                                }
                            });

                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    newshowPicFromCamera();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            restShareData();
            setResult(AppConfig.UPLOAD_BACK);
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 初始化数据
     */
    private void restShareData() {
        LogUtil.showLog("restShareData====" + DateUtil.getDateTime(new Date()));
        PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.TEMP_IMAGENAME, "");
        PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_IDENTITY1, "");
        PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_IDENTITY2, "");
        PreferenceUtil.getInstance(getApplicationContext()).saveString(AppConfig.IMAG_PROFILE, "");
        PreferenceUtil.getInstance(getApplicationContext()).saveInt("profile_width", 0);
        PreferenceUtil.getInstance(getApplicationContext()).saveInt("profile_height", 0);

    }
}
