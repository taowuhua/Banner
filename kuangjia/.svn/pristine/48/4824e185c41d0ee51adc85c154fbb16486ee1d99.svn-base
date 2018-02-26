package com.bank.quickpay.activity.payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.widget.ProgressWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * HTML展示用户须知之类的界面
 */
public class BankHtmlActivity extends BaseActivity {
private int TOPAYSUCCESSPAGE=0x0051;

    @BindView(R.id.webview)
    ProgressWebView webview;
    private String amount,orderId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_html_message;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        try {
            String htmlContent = (String) getIntent().getExtras().get("htmlContent");
            amount = (String) getIntent().getExtras().get("amount");
            orderId = (String) getIntent().getExtras().get("orderId");
            WebSettings wSet = webview.getSettings();
            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            wSet.setJavaScriptEnabled(true);
            wSet.setDomStorageEnabled(true);
            supportZoom(wSet);

            wSet.setAllowFileAccess(true);
            wSet.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //开启脚本支持
            webview.addJavascriptInterface(new JavaScriptinterface(this),"quickbank"
            );
            webview.setVerticalScrollBarEnabled(false); //垂直不显示

            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    showLoading("努力加载中，请耐心等待...");
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public void onReceivedError(final WebView view, int errorCode, String description, final String failUrl) {
                    LogUtil.showToast(BankHtmlActivity.this, description);
                    htmlNetworkFail(BankHtmlActivity.this, view, new CompleteResultListen() {
                        @Override
                        public void compleResultok() {
                            view.loadUrl(failUrl);
                        }
                    });
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    cancleLoading();
                }
            });
                webview.loadDataWithBaseURL(null,htmlContent,"text/html","utf-8",null);
//            webview.loadUrl("https://mpostest.ruiyinxin.com/kjfx/index.html");
        } catch (Exception e) {
            LogUtil.showToast(BankHtmlActivity.this, "数据异常！！！");
        }

    }/**
     * Android与HTML页面交互接口
     */
    public class JavaScriptinterface {
        Context context;
        public JavaScriptinterface(Context c) {
            context= c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void alert(String ssss) {
            Toast.makeText(context, ssss, Toast.LENGTH_LONG).show();
        }
        @JavascriptInterface
        public void showToast(String ssss) {

            Toast.makeText(context, ssss, Toast.LENGTH_LONG).show();
        }
        @JavascriptInterface
        public void toResultPage() {
            Intent intent=new Intent(BankHtmlActivity.this,PaySuccessActivity.class);
            intent.putExtra("orderId",orderId);
            intent.putExtra("amount",amount);
            startActivityForResult(intent,TOPAYSUCCESSPAGE);
        }

    }
    private void supportZoom(WebSettings webSettings) {
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        webSettings.setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        webSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            setResult(AppConfig.CLOSE_ALL);
            finish();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
