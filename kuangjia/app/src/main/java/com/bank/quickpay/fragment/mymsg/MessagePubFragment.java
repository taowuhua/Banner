package com.bank.quickpay.fragment.mymsg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.bank.quickpay.R;
import com.bank.quickpay.activity.mymsg.MessageDetailActiviy;
import com.bank.quickpay.activity.mymsg.MessageScreenActivity;
import com.bank.quickpay.adapter.MsgListAdapter;
import com.bank.quickpay.bean.MsgInfo;
import com.bank.quickpay.config.AppConfig;
import com.bank.quickpay.fragment.base.BaseFragment;
import com.bank.quickpay.http.QuickPayResult;
import com.bank.quickpay.interfaces.FragmentListener;
import com.bank.quickpay.utils.DataUtil;
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by xiepingping on 2016/7/14.
 */
public class MessagePubFragment extends BaseFragment {

    private FragmentListener mListener;
    @BindView(R.id.materialRefreshLayout)
    MaterialRefreshLayout materialRefreshLayout;

    @BindView(R.id.lv_pubmsg)
    ListView lv_pubmsg;
    MsgListAdapter adapter;

    @BindView(R.id.lay_top)
    AutoLinearLayout lay_top;
    ArrayList<MsgInfo> noticeList = new ArrayList<MsgInfo>();
    ArrayList<MsgInfo> tempNoticeList = new ArrayList<MsgInfo>();
    private boolean isUpRefresh = false;
    private boolean isDownRefresh = false;
    private Context context;
    private int offset = 1;
    private int unreadNoticeNumber = 0;//未读公共消息
    private final int WILL_HAS_UPDATE = 21;
    private boolean isFirstIn = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_public_content;
    }

    /**
     * 创建资金动态实例
     *
     * @return
     */
    public static MessagePubFragment newInstance() {
        MessagePubFragment fragment = new MessagePubFragment();
        return fragment;
    }

    @Override
    public void afterViews(Bundle savedInstanceState) {
        adapter = new MsgListAdapter(getActivity());
        adapter.setList(tempNoticeList);
        lv_pubmsg.setAdapter(adapter);
        unreadNoticeNumber = 0;
        initRefreshViews();
        getNoticeList();
        mListener.doDataRequest(null);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        try {
            mListener = (MessageScreenActivity) getBaseActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        super.onAttach(context);
    }

    private void initRefreshViews() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            //上拉刷新
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                isDownRefresh = true;
                offset = 1;
                tempNoticeList.clear();
                mListener.doDataRequest(null);
            }

            //下拉刷新
            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                isUpRefresh = true;
                mListener.doDataRequest(null);
            }
        });
    }

    //判断消息是否存在
    private boolean hasNotice(String noticeCode) {
        int len = noticeList.size();
        for (int i = 0; i < len; i++) {
            if (noticeCode.equals(noticeList.get(i).getNoticeCode())) {
                return true;
            }
        }
        return false;
    }

    //判断公共消息的读取状态
    private boolean getReadState(String noticeCode) {
        int len = noticeList.size();
        for (int i = 0; i < len; i++) {
            if (noticeCode.equals(noticeList.get(i).getNoticeCode())) {
                return noticeList.get(i).isReaded();
            }
        }
        return false;
    }

    //获取本地存储的公共消息
    @SuppressWarnings("unchecked")
    private void getNoticeList() {
        ObjectInputStream in = null;
        try {
            InputStream is = context.openFileInput("notice_" + AppConfig.getInstance((MessageScreenActivity) context).getCustomerId() + ".obj");
            in = new ObjectInputStream(is);
            if (in != null) {
                noticeList = (ArrayList<MsgInfo>) in.readObject();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        noticeList = DataUtil.removeDuplicate(noticeList);
        LogUtil.dshowLog("noticeList===" + noticeList);
        int len = noticeList.size();
        for (int i = 0; i < len; i++) {
            String noticeType = noticeList.get(i).getNoticeType();
            boolean isRead = getReadState(noticeList.get(i).getNoticeCode());
            if (("0".equals(noticeType) && !isRead)
                    || (TextUtils.isEmpty(noticeType) && !isRead)) {
                unreadNoticeNumber = unreadNoticeNumber + 1;
            }
        }

    }

    public void saveUnreadNoticeNumber() {
        PreferenceUtil.getInstance((MessageScreenActivity) context).saveInt("unreadNoticeNumber_"
                + AppConfig.getInstance((MessageScreenActivity) context).getMobileNo(), unreadNoticeNumber);
    }

    /**
     * activity回调Fragment接口
     *
     * @param actiontype
     * @param qtpayResult
     */
    public void send(int actiontype, QuickPayResult qtpayResult) {
        if (actiontype == 0x111) {
            if (isUpRefresh) {
                materialRefreshLayout.finishRefreshLoadMore();
            } else if (isDownRefresh) {
                materialRefreshLayout.finishRefresh();
            }
            //首次进来可以添加历史消息
            if (isFirstIn) {
                tempNoticeList.addAll(noticeList);
                isFirstIn = false;
            }
            ArrayList<MsgInfo> list = analyzeNotices(qtpayResult.getData());
            if (list.size() > 0) {
                tempNoticeList.addAll(list);
                noticeList.addAll(list);
            } else if (isUpRefresh) {
                LogUtil.showToast((MessageScreenActivity) context, "无更多消息！");
            }
            //下拉刷新时
            else if (isDownRefresh) {
                tempNoticeList.addAll(noticeList);
            }

            if (isUpRefresh) {
                isUpRefresh = false;
            }
            if (isDownRefresh) {
                isDownRefresh = false;
            }
            initList();
        } else if (actiontype == 0x222) {
            if (isUpRefresh) {
                materialRefreshLayout.finishRefreshLoadMore();
                isUpRefresh = false;
            } else if (isDownRefresh) {
                materialRefreshLayout.finishRefresh();
                isDownRefresh = false;
            }
        }
    }

    private void initList() {
        resetPubList();
        adapter.notifyDataSetChanged();
        if (tempNoticeList.size() == 0) {
            lay_top.setVisibility(View.VISIBLE);
        } else {
            lay_top.setVisibility(View.GONE);
        }
    }

    public void resetPubList() {
        int len = tempNoticeList.size();
        ArrayList<MsgInfo> temppublist1 = new ArrayList<MsgInfo>();
        ArrayList<MsgInfo> temppublist2 = new ArrayList<MsgInfo>();
        for (int i = 0; i < len; i++) {
            MsgInfo msgInfo = tempNoticeList.get(i);
            if (!msgInfo.isReaded()) {
                temppublist1.add(msgInfo);
            } else {
                temppublist2.add(msgInfo);
            }
        }
        tempNoticeList.clear();
        tempNoticeList.addAll(temppublist1);
        tempNoticeList.addAll(temppublist2);
    }

    //解析通知内容
    private ArrayList<MsgInfo> analyzeNotices(String noticeData) {
        ArrayList<MsgInfo> list = new ArrayList<MsgInfo>();
        if (noticeData != null) {
            try {
                JSONObject noticeObj = new JSONObject(noticeData);
                if (AppConfig.QTNET_SUCCESS.equals(noticeObj.getJSONObject(
                        "result").getString("resultCode"))) {
                    JSONArray noticeArray = noticeObj.getJSONArray("resultBean");
                    int length = noticeArray.length();
                    MsgInfo msgInfo = null;
                    for (int i = 0; i < length; i++) {
                        msgInfo = new MsgInfo();
                        String noticeCode = JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeCode");
                        msgInfo.setNoticeCode(noticeCode);
                        msgInfo.setTitle(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "title"));
                        msgInfo.setContent(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeContent"));
                        msgInfo.setTime(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "effectTime"));
                        msgInfo.setReadFlag(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "readFlag"));
                        String noticeType = JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "noticeType");

                        msgInfo.setNoticeType(noticeType);
                        boolean isRead = false;
                        boolean isNewPubNotice = false;
                        if ("0".equals(noticeType)) {
                            if (!hasNotice(noticeCode)) {
                                msgInfo.setReaded(false);
                                list.add(msgInfo);
                                isNewPubNotice = true;
                            } else {
                                isNewPubNotice = false;
                                isRead = getReadState(noticeCode);
                            }
                        }
                        // 1、公共消息，则根据本地存储的消息状态判断消息是否已读；
                        // 2、历史公共消息，根据isRead判断是否已读
                        if ("0".equals(noticeType) && !isRead && isNewPubNotice) {
                            unreadNoticeNumber = unreadNoticeNumber + 1;
                        }
                    }
                    PreferenceUtil.getInstance(context).saveInt(
                            "unreadNoticeNumber_"
                                    + AppConfig
                                    .getInstance(context)
                                    .getMobileNo(), unreadNoticeNumber);
                    offset = offset + noticeArray.length();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
        return list;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        offset = 1;
        mListener.doDataRequest(null);
    }

    @OnItemClick(R.id.lv_pubmsg)
    public void getClickItem(int position) {
        if (!tempNoticeList.get(position).isReaded()) {
            if (unreadNoticeNumber > 0) {
                unreadNoticeNumber = unreadNoticeNumber - 1;
            }
            tempNoticeList.get(position).setReaded(true);
        }
        Intent intent = new Intent((MessageScreenActivity) context, MessageDetailActiviy.class);
        intent.putExtra("msgMap", (Serializable) tempNoticeList.get(position));
        startActivityForResult(intent, WILL_HAS_UPDATE);
    }

    /**
     * 保存消息到本地
     */
    public void saveNoticeList() {
        ObjectOutputStream out = null;
        try {
            noticeList = DataUtil.removeDuplicate(noticeList);
            FileOutputStream os = context.openFileOutput("notice_" + AppConfig.getInstance((MessageScreenActivity) context).getCustomerId() + ".obj", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(os);
            out.writeObject(noticeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveNoticeList();
    }
}
