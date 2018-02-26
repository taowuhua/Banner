package com.bank.quickpay.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bank.quickpay.QuickPayApplication;
import com.bank.quickpay.R;
import com.bank.quickpay.activity.base.BaseActivity;
import com.bank.quickpay.activity.helping.HtmlMessageActivity;
import com.bank.quickpay.activity.mymsg.AuthActivity;
import com.bank.quickpay.activity.mymsg.MessageScreenActivity;
import com.bank.quickpay.activity.mymsg.MyInfoActivity;
import com.bank.quickpay.adapter.MainGridAdapter;
import com.bank.quickpay.bean.AdBeanMap;
import com.bank.quickpay.bean.CardBeanMap;
import com.bank.quickpay.bean.IconBean;
import com.bank.quickpay.bean.Param;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.dialog.AppDownLoadDialog;
import com.bank.quickpay.dialog.QuickSimpleConfirmDialog;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.http.XmlCallback;
import com.bank.quickpay.interfaces.ConFirmDialogListener;
import com.bank.quickpay.interfaces.IPermission;
import com.bank.quickpay.utils.GlideUtils;
import com.bank.quickpay.utils.HttpUtil;
import com.bank.quickpay.utils.IntentHelper;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.MapUtil;
import com.bank.quickpay.utils.PermissionUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.bank.quickpay.utils.QuickPayAppData;
import com.bank.quickpay.utils.UriUtils;
import com.ryx.payment.ryxhttp.callback.FileCallBack;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.inter.OnListItemClickListener;
import com.ryx.quickadapter.inter.RecyclerViewHelper;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.Information;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import okhttp3.Call;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.tileleftImg)
    ImageView tileleftImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.tilerightImg)
    ImageView tilerightImg;
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.gv_bottom)
    RecyclerView gvBottom;
    private ArrayList<IconBean.IconMsgBean> mTopIconMsgBean = new ArrayList<>();
    private Intent intent;
    String updateContent = "";
    String version;
    String updateUrl;
    String must;
    String updateInfo;
    AppDownLoadDialog appDownLoadDialog;
    final List<AdBeanMap> adBeanMapList=new ArrayList<>();
    List<View> adviews = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitleLayout(getResources().getString(R.string.app_name),true,true);
       ImageView imageView= getRightImgView();
        imageView.setImageResource(R.drawable.msgimg);
        initQtPatParams();
        initVersionView();
        initRecycler();
        initGuideView();
    }
    public void backUpImgOnclickListen(){
        if (!QuickPayAppData.getInstance(
                getApplicationContext()).isLogin()) {
            toAgainLogin(getApplicationContext(), AppConfig.TOLOGINACT);
        }else{
            initKeFu();
        }
    }
    private void initKeFu() {
        Information info = new Information();
        info.setAppkey("b67c6a7fe24d44ddada0315aafed7841");
        info.setUname(QuickPayAppData.getInstance(MainActivity.this).getRealName());
        //用户姓名，选填
        info.setRealname(QuickPayAppData.getInstance(MainActivity.this).getRealName());
        //用户电话，选填
        info.setTel(QuickPayAppData.getInstance(MainActivity.this).getMobileNo());

        //设置标题栏的背景图片，选填
//        info.setTitleImgId(R.drawable.sobot_delete_hismsg_normal);
//设置标题栏的背景颜色，如果背景颜色和背景图片都设置，则以背景图片为准，选填
//     int styleTag=RyxAppdata.getInstance(MainFragmentActivity.this).getCurrentBranchMainStyleTag();
//        if(styleTag==1){
//        //蓝色调
//            info.setColor("#1db7f0");
//        }else if(styleTag==3){
//            //灰色调
//            info.setColor("#404352");
//        }
        info.setTitleImgId(R.color.bg_black);
        String infoUid = QuickPayAppData.getInstance(MainActivity.this).getMobileNo() + QuickPayAppData.getInstance(MainActivity.this).getCustomerId();
        info.setUid(infoUid);
        /**
         * 设置聊天界面标题显示模式
         * @param context 上下文对象
         * @param mode titile的显示模式
         *              SobotChatTitleDisplayMode.Default:显示客服昵称(默认)
         *              SobotChatTitleDisplayMode.ShowFixedText:显示固定文本
         *              SobotChatTitleDisplayMode.ShowCompanyName:显示console设置的企业名称
         * @param content 如果需要显示固定文本，需要传入此参数，其他模式可以不传
         */
        SobotApi.setChatTitleDisplayMode(MainActivity.this, SobotChatTitleDisplayMode.Default, "");

        //默认false：显示转人工按钮。true：智能转人工
        info.setArtificialIntelligence(false);
        //当未知问题或者向导问题显示超过(X)次时，显示转人工按钮。
        //注意：只有ArtificialIntelligence参数为true时起作用
        info.setArtificialIntelligenceNum(5);
        //是否使用语音功能 true使用 false不使用
//                info.setUseVoice(true);
        //客服模式控制 -1不控制 按照服务器后台设置的模式运行
        //1仅机器人 2仅人工 3机器人优先 4人工优先
        info.setInitModeType(3);
        //返回时是否弹出满意度评价
        info.setShowSatisfaction(true);
        /**
         * @param context 上下文对象
         * @param information 初始化参数
         */
        SobotApi.startSobotChat(MainActivity.this, info);
    }

    @OnClick(R.id.tilerightImg)
    public void openMsgPage(){
        Intent intent = new Intent(this, MessageScreenActivity.class);
        startActivity(intent);
    }

    private void initRecycler() {

        String topResult = getFromRaw(R.raw.top_grid);
        IconBean topIconBean = handleInputStream(topResult);
        mTopIconMsgBean = (ArrayList<IconBean.IconMsgBean>) topIconBean.getGetIconList();

        Comparator topComparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                IconBean.IconMsgBean imb1 = (IconBean.IconMsgBean) o1;
                IconBean.IconMsgBean imb2 = (IconBean.IconMsgBean) o2;
                return (new Integer(imb1.getIdx())).compareTo(new Integer(imb2.getIdx()));
            }
        };
        Collections.sort(mTopIconMsgBean, topComparator);
        Iterator<IconBean.IconMsgBean> topIterator = mTopIconMsgBean.iterator();
        while (topIterator.hasNext()) {
            IconBean.IconMsgBean topBean = topIterator.next();
            if ("1".equals(topBean.getShow())) {
                //不显示的进行移除
                topIterator.remove();
            }
        }
        RecyclerViewHelper.init().setRVGridLayout(MainActivity.this, gvBottom, 3);//4列
        MainGridAdapter mainTopGridAdapter = new MainGridAdapter(mTopIconMsgBean, MainActivity.this, R.layout.gridview_main_item);
        gvBottom.setAdapter(mainTopGridAdapter);
        mainTopGridAdapter.setOnItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int i, Object o) {
                Bundle bundle=new Bundle();
                if(checkPermission((IconBean.IconMsgBean) o)){
                    if("HelpExplain".equals(((IconBean.IconMsgBean) o).getFlag())){
                        bundle.putString("urlkey",AppConfig.HELPEXPLAIN);
                        bundle.putString("title","帮助说明");
                    }

                    try {
                        intentToActivity(((IconBean.IconMsgBean) o).getId(),bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.showToast(MainActivity.this,e.getLocalizedMessage());
                        LogUtil.showLog(e.getLocalizedMessage());
                    }
                }
            }
        });
    }


    /**
     * 统一跳转前检测
     *
     * @param msgBean 对象
     * @return 是否通过
     */
    private boolean checkPermission(IconBean.IconMsgBean msgBean) {
        String permissionStr = msgBean.getPermission();
        /**
         * 0   需要登录
         * 1   需要实名通过
         * 2   需要绑定默认结算卡
         */
        if (permissionStr.contains("0")) {//验证登录
            if (!QuickPayAppData.getInstance(getApplicationContext()).isLogin()) {
                toAgainLogin(getApplicationContext(), AppConfig.TOLOGINACT);
                return false;
            }
        }
        if (permissionStr.contains("1")) {//验证实名认证是否通过
            int flag = QuickPayAppData.getInstance(MainActivity.this).getAuthenFlag();
            if (flag != 3) {
                showAuthDialog();
                return false;
            }
        }
        if (permissionStr.contains("2")) {//验证是否绑定银行卡
//            getBankCardList(msgBean);
        }
        return true;
    }

    /**
     * 展示实名认证框
     */
    private void showAuthDialog() {
        QuickSimpleConfirmDialog ryxSimpleConfirmDialog = new QuickSimpleConfirmDialog(MainActivity.this, new ConFirmDialogListener() {

            @Override
            public void onPositiveActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
                gotoRealName();
            }

            @Override
            public void onNegativeActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
            }
        });
        ryxSimpleConfirmDialog.show();
        ryxSimpleConfirmDialog.setContent("亲爱的用户，为了确保您的资金安全，进行此业务前需通过实名认证。");
    }


    /**
     * 跳转对应实名认证步骤
     */
    protected void gotoRealName() {
        // case 0: "未实名");
        // case 1:
        // case 5: "未认证");
        // case 2: "认证中");
        // case 4: "认证失败");
        // case 3: "已认证");
        int tag = QuickPayAppData.getInstance(this).getAuthenFlag();
        switch (tag) {
            case 0:
                 intent = new Intent(MainActivity.this, AuthActivity.class);
                break;
            case 1:
            case 5:
                intent = new Intent(MainActivity.this, MyInfoActivity.class);
                break;
            case 2:
            case 3:
            case 4:
                intent = new Intent(MainActivity.this, MyInfoActivity.class);
                break;
        }
        try {
            startActivity(intent);
        }catch (Exception e){
            LogUtil.showToast(MainActivity.this, "当前用户状态有误!");
        }
    }

        /**
         * 统一跳转对应模块
         *
         * @param ActivityName
         * @param bundle
         * @throws Exception
         */
    private void intentToActivity(String ActivityName, Bundle bundle) throws Exception {

        if (null == ActivityName || "".equals(ActivityName)) {
            throw new Exception("参数为空");
        }
        if (IntentHelper.getInstance().contains(ActivityName)) {
            Intent intent = new Intent(MainActivity.this
                    .getApplicationContext(), IntentHelper.getInstance()
                    .getActivityClass(ActivityName));
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
        throw new Exception("尚未注册此ActivityName=" + ActivityName);

    }

    private void initGuideView() {
        try {
            qtpayApplication = new Param("application", "RequestPhp.Req");
            qtpayAttributeList.add(qtpayApplication);
            qtpayParameterList.add(new Param("posiId", "10"));
            httpsPost("RequestPhpTag", new XmlCallback() {
                @Override
                public void onTradeSuccess(QuickPayResult payResult) {
        try {


                    String adJsonContent= payResult.getData();
                    JSONObject adJsonObj=new JSONObject(adJsonContent);
                    JSONObject posiRowObj= JsonUtil.getJSONObjectFromJsonObject(adJsonObj,"posiRow");
                    String posiRowalert=JsonUtil.getValueFromJSONObject(posiRowObj,"alert");
                    if("y040102".equals(posiRowalert)){
                        adBeanMapList.clear();
                        adviews.clear();
                        JSONArray advertRowsArray=adJsonObj.getJSONArray("advertRows");
                        for (int i=0;i<advertRowsArray.length();i++){
                            JSONObject advertRowObj= advertRowsArray.getJSONObject(i);
                            String advertRowalert=JsonUtil.getValueFromJSONObject(advertRowObj,"alert");
                            if("y080102".equals(advertRowalert)){
                                //广告链接
                                String advert_href=JsonUtil.getValueFromJSONObject(advertRowObj,"advert_href");
                                String advert_name=JsonUtil.getValueFromJSONObject(advertRowObj,"advert_name");
                                String advert_note=JsonUtil.getValueFromJSONObject(advertRowObj,"advert_note");
                                JSONObject mediaRowObj=advertRowObj.getJSONObject("mediaRow");
                                String mediaRow_alert=JsonUtil.getValueFromJSONObject(mediaRowObj,"alert");
                                if("y070102".equals(mediaRow_alert)){
                                    String media_url=JsonUtil.getValueFromJSONObject(mediaRowObj,"media_url");
                                    Map<String,String> map1=new HashMap<>();
                                    map1.put("title",advert_name);
                                    map1.put("imgurl",media_url);
                                    map1.put("advert_href",advert_href);
                                    map1.put("advert_note",advert_note);
                                    AdBeanMap adBeanMap1=new AdBeanMap();
                                    adBeanMap1.setMap(map1);
                                    adBeanMapList.add(adBeanMap1);
                                    adviews.add(GlideUtils.getInstance().getItemImageView(MainActivity.this,media_url));
                                }
                            }
                        }
                        LogUtil.showLog(adBeanMapList.size()+"==================");
                        bannerGuideContent.setData(adviews);
                        bannerGuideContent.setDelegate(new BGABanner.Delegate() {
                            @Override
                            public void onBannerItemClick(BGABanner bgaBanner, View view, Object o, final int position) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Map<String,String> adBeanMap=  adBeanMapList.get(position).getMap();
                                        String advert_href= MapUtil.getString(adBeanMap,"advert_href");
                                        String advert_name= MapUtil.getString(adBeanMap,"title");
                                        if(TextUtils.isEmpty(advert_href)){
                                            return;
                                        }
                                        jumpHtmlPage(advert_name,advert_href);
                                    }
                                });
                            }
                        });
                    }else{
                        String code=   JsonUtil.getValueFromJSONObject(adJsonObj,"code");
                        if(!AppConfig.QTNET_SUCCESS.equals(code)){
                            String message=   JsonUtil.getValueFromJSONObject(adJsonObj,"message");
                            LogUtil.showToast(MainActivity.this,"活动信息:"+message);
                        }

                    }
                    }catch (Exception e){

                    }
                }

            });








//            List<View> views = new ArrayList<>();
////        views.add(BGABannerUtil.getItemImageView(this, R.mipmap.ic_launcher));
//            views.add(GlideUtils.getInstance().getItemImageView(this,"http://www.ruiyinxin.com/app/adms/media/2017/10/34.jpg"));
//            views.add(GlideUtils.getInstance().getItemImageView(this,"http://www.ruiyinxin.com/app/adms/media/2017/10/34.jpg"));
//            views.add(GlideUtils.getInstance().getItemImageView(this,"http://www.ruiyinxin.com/app/adms/media/2017/10/34.jpg"));
//            views.add(GlideUtils.getInstance().getItemImageView(this,"http://www.ruiyinxin.com/app/adms/media/2017/10/34.jpg"));
//            views.add(GlideUtils.getInstance().getItemImageView(this,"http://www.ruiyinxin.com/app/adms/media/2017/10/34.jpg"));
////        views.add(BGABannerUtil.getItemImageView(this, R.mipmap.ic_launcher));
//            bannerGuideContent.setData(views);
        }catch (Exception e){

        }

    }

    /**
     * 跳转广告页面
     * @param title
     * @param url
     */
    private void jumpHtmlPage(String title,String url){
        String customerId=QuickPayAppData.getInstance(MainActivity.this).getCustomerId();
        Bundle bundle =new Bundle();
        bundle.putString("ccurl", url+"&customerId="+customerId+"&appuser="+AppConfig.APPUSER+"&version="+AppConfig.CLIENTVERSION);
        bundle.putString("title",title);
        Intent intent = new Intent(MainActivity.this, HtmlMessageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 从resources中的raw 文件夹中获取文件并读取数据
    public String getFromRaw(int id) {
        String result = "";
        try {
            InputStream in = getResources().openRawResource(id);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, 0, buffer.length, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public IconBean handleInputStream(String inputString) {
        IconBean iconBean = new IconBean();
        try {
            JSONObject iconBeanjsonObject= new JSONObject(inputString);
            JSONArray jsonArray = iconBeanjsonObject.getJSONArray("getIconList");
            ArrayList<IconBean.IconMsgBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                IconBean.IconMsgBean msgBean = new IconBean.IconMsgBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                msgBean.setFlag(jsonObject.getString("flag"));
                msgBean.setId(jsonObject.getString("id"));
                msgBean.setIdx(jsonObject.getString("idx"));
                msgBean.setName(jsonObject.getString("name"));
                msgBean.setRes(jsonObject.getString("res"));
                msgBean.setShow(jsonObject.getString("show"));
                msgBean.setPermission(jsonObject.getString("permission"));
                list.add(msgBean);
            }
            String mainbg=  JsonUtil.getValueFromJSONObject(iconBeanjsonObject,"mainbg");
            iconBean.setGetIconList(list);
            iconBean.setMainbg(mainbg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iconBean;
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            LogUtil.showToast(MainActivity.this, getResources()
                    .getString(R.string.press_again));
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            if (HttpUtil.checkNet(getApplicationContext())) {
                qtpayApplication = new Param("application");
                qtpayApplication.setValue("UserLoginExit.Req");
                qtpayAttributeList.add(qtpayApplication);
                httpsPost(false, true, "UserLoginExit", new XmlCallback() {
                    @Override
                    public void onTradeSuccess(QuickPayResult payResult) {
                        doExit();
                        QuickPayApplication.getInstance().exit();
                    }
                });
            } else {
                doExit();
                QuickPayApplication.getInstance().exit();
            }
            finish();
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initVersionView() {
        String updateStr=  PreferenceUtil.getInstance(MainActivity.this).getString("updateInfo","");
        versionTv.setText("当前版本: V"+AppConfig.getVersionCode(MainActivity.this)+(TextUtils.isEmpty(updateStr)?"":"("+updateStr+")"));
        if(!TextUtils.isEmpty(updateStr)){
            versionTv.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    doUpdate();
                }
            });
        }
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
                    PreferenceUtil.getInstance(MainActivity.this)
                            .saveString("version", version);
                    PreferenceUtil.getInstance(MainActivity.this)
                            .saveString("updateContent", updateContent);
                    PreferenceUtil.getInstance(MainActivity.this)
                            .saveString("updateUrl", updateUrl);
                    PreferenceUtil.getInstance(MainActivity.this)
                            .saveString("must", must);
                    showUpdataDialog(); // 需要更新就显示升级对话框
                }
            } else if ("0001".equals(jsonObj.getJSONObject("result").getString(
                    "resultCode"))) {
            } else if ("0002".equals(jsonObj.getJSONObject("result").getString(
                    "resultCode"))) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PreferenceUtil.getInstance(MainActivity.this).saveString(
                    "updateInfo", updateInfo);
        }
    }

    /**
     * 展示下载框
     */
    private void showUpdataDialog() {

        QuickSimpleConfirmDialog ryxSimpleConfirmDialog=new QuickSimpleConfirmDialog(MainActivity.this,new ConFirmDialogListener(){

            @Override
            public void onPositiveActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
                dirpermissionCheck();
            }
            @Override
            public void onNegativeActionClicked(QuickSimpleConfirmDialog ryxSimpleConfirmDialog) {
                ryxSimpleConfirmDialog.dismiss();
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

        PermissionUtil.checkPermission(MainActivity.this, new IPermission() {
            @Override
            public void permissionSuccess() {
                downApk(updateUrl);
            }

            @Override
            public void permissionFail() {
                LogUtil.showToast(MainActivity.this, finalWaring);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }
    public void downApk(String url) {
        if(TextUtils.isEmpty(url)||!url.contains("http")){
            LogUtil.showToast(MainActivity.this,"更新路径有误,请联系客服!!!");
            return ;
        }
//        if (ryxLoadDialogBuilder == null) {
//            ryxLoadDialogBuilder =  new RyxLoadDialog().getInstance(SplashActivity.this);
//        }
//        ryxLoadDialogBuilder.setMessage("当前进度为:0.00%");
//        ryxLoadDialogBuilder.show();
        if(appDownLoadDialog==null){
            appDownLoadDialog= AppDownLoadDialog.simpleShowDownLoadDialog(MainActivity.this);
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
                LogUtil.showToast(MainActivity.this,"访问服务端超时,请检查网络是否正常!!!");
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
}
