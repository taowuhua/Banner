package com.bank.quickpay.activity.helping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.widget.ProgressWebView;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HTML展示用户须知之类的界面
 */
public class HtmlMessageActivity extends BaseActivity {


    @BindView(R.id.webview)
    ProgressWebView webview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_html_message;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        try {
            String ccurl = (String) getIntent().getExtras().get("ccurl");
//            ccurl="http://www.baidu.com";
//            LogUtil.showToast(HtmlMessageActivity.this,ccurl);
            final String title = (String) getIntent().getExtras().get("title");
            String urlkey = (String) getIntent().getExtras().get("urlkey");
            String mytitle = "";
            if (TextUtils.isEmpty(title)) {
                mytitle = getResources().getString(R.string.app_name);
            } else {
                mytitle = title;
            }
            setTitleLayout(mytitle, true, false);
            WebSettings wSet = webview.getSettings();
            wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            wSet.setJavaScriptEnabled(true);
            wSet.setDomStorageEnabled(true);
            if (getIntent().getExtras().containsKey("webPage")) {
                boolean isWebPage = (boolean) getIntent().getExtras().get("webPage");
                if (isWebPage)//如果是访问外部页面，则增加网页缩放功能，适配手机屏幕
                {
                    supportZoom(wSet);
                }
            }
            webview.addJavascriptInterface(new JavaScriptinterface(this), "ryx"
            );
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    showLoading("努力加载中，请耐心等待...");
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onReceivedError(final WebView view, int errorCode, String description, final String failUrl) {
                    LogUtil.showToast(HtmlMessageActivity.this, description);
                    htmlNetworkFail(HtmlMessageActivity.this, view, new CompleteResultListen() {
                        @Override
                        public void compleResultok() {
                            view.loadUrl(failUrl);
                        }
                    });
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    cancleLoading();
                    if (TextUtils.isEmpty(title)) {
                        setTitleLayout(view.getTitle(), true, false);
                    }
                }
            });
            String url = PreferenceUtil.getInstance(HtmlMessageActivity.this).getString(urlkey, "");
            LogUtil.showLog("htmlKey===" + urlkey + ",url==" + url + ",ccurl==" + ccurl);
            if (TextUtils.isEmpty(ccurl)) {
                webview.loadUrl(url);
            } else {
                webview.loadUrl(ccurl);
            }
        } catch (Exception e) {
            LogUtil.showToast(HtmlMessageActivity.this, "数据异常！！！");
        }

    }

    /**
     * Android与HTML页面交互接口
     */
    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void alert(String ssss) {
            Toast.makeText(context, ssss, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void shareTo(final String content) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    shareContent(content);
                }
            });
        }
        @JavascriptInterface
        public void jumpPage(final String pageClassStr){
//          例如jumpPage(".authenticate.Authenticate_");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(HtmlMessageActivity.this,
                                Class.forName(getApplicationContext().getPackageName()+pageClassStr));
                        startActivity(intent);
                    }catch (Exception e){
                        LogUtil.showToast(HtmlMessageActivity.this,"跳转失败"+e.getLocalizedMessage());
                    }
                }
            });
        }

        @JavascriptInterface
        public void againLogin() {
            toAgainLogin(context, AppConfig.TOLOGINACT);
        }

    }

    public void shareContent(String content){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Intent.createChooser(intent, getTitle()));
        } catch (Exception e) {
            LogUtil.showToast(HtmlMessageActivity.this, "调取分享失败,请尝试长按链接复制!");
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

}
