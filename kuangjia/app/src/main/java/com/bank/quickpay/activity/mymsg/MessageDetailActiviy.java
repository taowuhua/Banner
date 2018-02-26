package com.bank.quickpay.activity.mymsg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.bean.MsgInfo;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.utils.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDetailActiviy extends BaseActivity {

    @BindView(R.id.tv_msgtitle)
    TextView tv_msgtitle;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.wv_msg)
    WebView wv_msg;

    private MsgInfo msgMap = new MsgInfo();
    Param qtpayNoticeCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail_activiy;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout("我的消息", true, false);
        initWebView();
        if (getIntent() != null) {
            msgMap = (MsgInfo) getIntent().getSerializableExtra("msgMap");
            if (msgMap != null) {
                tv_msgtitle.setText(msgMap.getTitle());
                wv_msg.loadDataWithBaseURL(null, msgMap.getContent(), "text/html", "utf-8", null);
                if (msgMap.getTime() != null) {
                    tv_time.setText(DateUtil.DateToShortStr(DateUtil.StrToDate(msgMap.getTime())));
                }
                initQtPatParams();
                //如果是个人消息，并且如果消息未读，则更新个人消息状态
                if ("1".equals(msgMap.getNoticeType()) && "0".equals(msgMap.getReadFlag())) {
                    updatePersonalMsg();
                }
            }
        }
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        wv_msg.getSettings().setAllowFileAccess(true);
        //开启脚本支持
        wv_msg.getSettings().setJavaScriptEnabled(true);
        String ua = wv_msg.getSettings().getUserAgentString();
        wv_msg.getSettings().setUserAgentString(ua + " " + AppConfig.APPUSER + "/" + AppConfig.CLIENTVERSION);
        wv_msg.addJavascriptInterface(new MessageDetailActiviy.JavaScriptinterface(this), "ryx"
        );
        wv_msg.setVerticalScrollBarEnabled(false); //垂直不显示
        wv_msg.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_msg.getSettings().setDomStorageEnabled(true);//DOM Storage
        wv_msg.getSettings().setGeolocationEnabled(true);
        class MyWebChromeClient extends WebViewClient {


            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                showLoading("努力加载中，请耐心等待...");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cancleLoading();
//                setTitleLayout(view.getTitle(), true, false);
            }
        }
        wv_msg.setWebViewClient(new MyWebChromeClient());

    }

    private void updatePersonalMsg() {
        qtpayApplication = new Param("application", "UpdatePublicNoticePerson.Req");
        qtpayNoticeCode = new Param("noticeCode");
        qtpayNoticeCode.setValue(msgMap.getNoticeCode() + "");
        qtpayAttributeList.add(qtpayApplication);
        qtpayParameterList.add(qtpayNoticeCode);
        httpsPost("UpdatePublicNoticePerson", new XmlCallback() {
            @Override
            public void onTradeSuccess(QuickPayResult payResult) {

            }
        });

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
        public void jumpPage(final String pageClassStr) {
//          例如jumpPage(".authenticate.Authenticate_");
            Intent intent = null;
            try {
                intent = new Intent(context, Class.forName(context.getPackageName() + pageClassStr));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            context.startActivity(intent);

        }


    }

    @OnClick(R.id.tileleftImg)
    public void cancelPage() {
        finish();
    }
}
