package com.bank.quickpay.activity.invitecode;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.IPermission;
import com.bank.quickpay.utils.BitmapUntils;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 我的邀请码
 * Created by xpp on 2017/11/2 0002.
 */

public class MyInviteCodeActivity extends BaseActivity {

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.iv_qrcodeimg)
    ImageView iv_qrcodeimg;
    @BindView(R.id.nodatalayout)
    AutoLinearLayout nodatalayout;
    @BindView(R.id.tv_url)
    TextView tv_url;
    @BindView(R.id.tv_invitationCode)
    TextView tv_invitationCode;

    @BindView(R.id.textmsg_tv)
    TextView textmsg_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_invitation_code;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("我的邀请码", true, true);
        nestedScrollView.setVisibility(View.VISIBLE);
        onRightImgViewToHtmlMsgAct(AppConfig.MYINVITATIONCODE,"邀请码使用说明");
        initQtPatParams();
        invitationCode();
    }

    /**
     * 获取我的邀请码信息
     */
    private void invitationCode() {
        qtpayApplication.setValue("GetInvitationCode.Req");
        qtpayAttributeList.add(qtpayApplication);
        httpsPost("GetInvitationCode", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {
                try {
                    String data = payResult.getData();
                    JSONObject dataJsonObj = new JSONObject(data);
                        final String code_val = JsonUtil.getValueFromJSONObject(dataJsonObj, "code_val");
                        final String code_url = JsonUtil.getValueFromJSONObject(dataJsonObj, "code_url");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nestedScrollView.setVisibility(View.VISIBLE);
                                nodatalayout.setVisibility(View.GONE);
                                createQrcodeImg(code_url, iv_qrcodeimg);
                                tv_url.setText(code_url);
                                tv_invitationCode.setText(code_val);
                            }
                        });

                } catch (Exception e) {

                }
            }

            @Override
            public void onOtherState(String rescode, String resDesc) {
                nodatalayout.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
                textmsg_tv.setText(resDesc);
                if ("9127".equals(rescode)) {
                }
            }
        });
    }

    /**
     * 根据内容生成二维码
     *
     * @param content   二维码内容
     * @param qrcodeimg 展现二维码的Img
     */
    public void createQrcodeImg(final String content, final ImageView qrcodeimg) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                //解码资源ID引用的图像。
                Bitmap logoBitMap = BitmapFactory.decodeResource(getResources(), R.mipmap.kjfx_logo);
                Bitmap qrcodeBitMap = QRCodeEncoder.syncEncodeQRCode(content, 600, Color.BLACK, Color.WHITE, logoBitMap);
                return qrcodeBitMap;
            }

            @Override
            protected void onPostExecute(Bitmap qrcodeBitMap) {
                try {
                    qrcodeimg.setImageBitmap(qrcodeBitMap);
                } catch (Exception e) {
                    qrcodeimg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.qrcodefail));
                }
            }
        }.execute();
    }

    @OnClick(R.id.bt_shareUrl)
    public void shareClick() {
        String tv_urlStr = tv_url.getText().toString();
        if (TextUtils.isEmpty(tv_urlStr)) {
            LogUtil.showToast(MyInviteCodeActivity.this, "分享链接不能为空");
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
            intent.putExtra(
                    Intent.EXTRA_TEXT, tv_urlStr
            );
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Intent.createChooser(intent, getTitle()));
        } catch (Exception e) {
            LogUtil.showToast(MyInviteCodeActivity.this, "调取分享失败,请尝试长按链接复制!");
        }
    }

    /**
     * 将布局文件保存于磁盘中
     */
    @OnClick(R.id.bt_save_local)
    public void dirSaveView() {
        String waring = MessageFormat.format(getResources().getString(R.string.dirwaringmsg), getResources().getString(R.string.app_name));
        final String finalWaring = waring;
        PermissionUtil.checkPermission(this,new IPermission() {
                    @Override
                    public void permissionSuccess() {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(getResources().getString(R.string.app_name));
                        stringBuffer.append("/PayQrCode");
                        //保存二维码图片到手机qrcodeallaout
                        Map<String, String> ssMap = BitmapUntils.saveQrcodeAsFile(iv_qrcodeimg,
                                stringBuffer.toString());
                        if (TextUtils.isEmpty(ssMap.get("path")) || TextUtils.isEmpty(ssMap.get("fileName"))) {
                            LogUtil.showToast(MyInviteCodeActivity.this, ssMap.get("result"));
                            return;
                        }
                        // 其次把文件插入到系统图库
                        try {
                            MediaStore.Images.Media.insertImage(MyInviteCodeActivity.this.getContentResolver(),
                                    ssMap.get("path") + File.separator + ssMap.get("fileName")
                                            + ".jpg", ssMap.get("fileName"), null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // 最后通知图库更新
                        MyInviteCodeActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + ssMap.get("path") + File.separator + ssMap.get("fileName")
                                + ".jpg")));
                        LogUtil.showToast(MyInviteCodeActivity.this, ssMap.get("result"));
                    }

                    @Override
                    public void permissionFail() {
                        LogUtil.showToast(MyInviteCodeActivity.this, finalWaring);
                    }
                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @OnClick(R.id.bt_copy)
    public void copy(View view) {
        String invitationCode = tv_invitationCode.getText().toString();
        if (TextUtils.isEmpty(invitationCode)) {
            LogUtil.showToast(MyInviteCodeActivity.this, "邀请码不允许为空");
            return;
        }
        ClipboardManager clip = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        clip.setText(invitationCode); // 复制
        LogUtil.showToast(getApplicationContext(), "复制成功");
        clip.getText(); // 粘贴

    }

    @OnLongClick(R.id.tv_url)
    public boolean copyUrl(View view) {
        String tv_urlStr = tv_url.getText().toString();
        if (TextUtils.isEmpty(tv_urlStr)) {
            LogUtil.showToast(MyInviteCodeActivity.this, "分享链接不能为空");
            return false;
        }
        ClipboardManager clip = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        clip.setText(tv_urlStr); // 复制
        LogUtil.showToast(getApplicationContext(), "复制成功");
        clip.getText(); // 粘贴
        return true;
    }
}
