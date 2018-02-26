package com.bank.quickpay.fragment.mymsg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.bank.quickpay.utils.JsonUtil;
import com.bank.quickpay.utils.LogUtil;
import com.bank.quickpay.utils.PreferenceUtil;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by xiepingping on 2016/7/14.
 */
public class MessagePersonalFragment extends BaseFragment {

    private FragmentListener mListener;
    @BindView(R.id.materialRefreshLayout)
    MaterialRefreshLayout materialRefreshLayout;
    @BindView(R.id.lv_personalmsg)
    ListView lv_personalmsg;
    MsgListAdapter adapter;
    @BindView(R.id.lay_top)
    AutoLinearLayout lay_top;

    private boolean isUpRefresh = false;
    private boolean isDownRefresh = false;
    ArrayList<MsgInfo> tempNoticeList = new ArrayList<MsgInfo>();
    private int offset = 1;

    private int unreadPersonNoticeNumber = 0;//未读个人消息
    private Context context;
    private String jsonData;
    private final int WILL_HAS_UPDATE = 21;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_personal_content;
    }

    /**
     * 创建资金动态实例
     *
     * @return
     */
    public static MessagePersonalFragment newInstance() {
        MessagePersonalFragment fragment = new MessagePersonalFragment();
        return fragment;
    }

    @Override
    public void afterViews(Bundle savedInstanceState) {
        adapter = new MsgListAdapter(getActivity());
        initRefreshViews();
        tempNoticeList.clear();
        adapter.setList(tempNoticeList);
        lv_personalmsg.setAdapter(adapter);
        mListener.doDataRequest(null);
    }

    private void initRefreshViews() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                isDownRefresh = true;
                tempNoticeList.clear();
                offset = 1;
                unreadPersonNoticeNumber = 0;
                mListener.doDataRequest(null);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                isUpRefresh = true;
                tempNoticeList.clear();
                mListener.doDataRequest(null);
            }
        });
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

    /**
     * activity回调Fragment接口
     *
     * @param actiontype
     * @param qtpayResult
     */
    public void send(int actiontype, QuickPayResult qtpayResult) {

        if (actiontype == 0x111) {
            Log.i("send----","send-----");
            if (isUpRefresh) {
                materialRefreshLayout.finishRefreshLoadMore();
            } else if (isDownRefresh) {
                materialRefreshLayout.finishRefresh();
            }
            ArrayList<MsgInfo> list = analyzeNotices(qtpayResult.getData());
            if (list.size() > 0) {
                tempNoticeList.addAll(list);
            } else if (isUpRefresh) {
                LogUtil.showToast((MessageScreenActivity) context, "无更多消息！");
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
        Log.i("send----","initList-----");
        resetPubList();
        adapter.notifyDataSetChanged();
        lv_personalmsg.setAdapter(adapter);
        if (tempNoticeList.size() == 0) {
            lay_top.setVisibility(View.VISIBLE);
        } else {
            lay_top.setVisibility(View.GONE);
        }
    }

    public void resetPubList() {
        int len = tempNoticeList.size();
        ArrayList<MsgInfo> templist1 = new ArrayList<MsgInfo>();
        ArrayList<MsgInfo> templist2 = new ArrayList<MsgInfo>();
        for (int i = 0; i < len; i++) {
            MsgInfo msgInfo = tempNoticeList.get(i);
            if ("0".equals(msgInfo.getReadFlag())) {
                templist1.add(msgInfo);
            } else {
                templist2.add(msgInfo);
            }
        }
        tempNoticeList.clear();
        tempNoticeList.addAll(templist1);
        tempNoticeList.addAll(templist2);
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
                        msgInfo.setPopup(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "popup"));
                        msgInfo.setXdtype(JsonUtil.getValueFromJSONObject(
                                noticeArray.getJSONObject(i), "xdtype"));
                        msgInfo.setNoticeType(noticeType);
                        String readFlag = "";
                        if ("1".equals(noticeType)) {
                            readFlag = JsonUtil.getValueFromJSONObject(
                                    noticeArray.getJSONObject(i), "readFlag");
                            msgInfo.setReadFlag(readFlag);
                            list.add(msgInfo);
                        }
                        // 如果是个人消息，则根据readFlag判断消息状态
                        if (("1".equals(noticeType) && "0".equals(readFlag))) {
                            unreadPersonNoticeNumber = unreadPersonNoticeNumber + 1;
                        }
                    }
                    PreferenceUtil.getInstance((MessageScreenActivity) context).saveInt(
                            "unreadNoticePersonNumber_"
                                    + AppConfig.getInstance((MessageScreenActivity) context)
                                    .getMobileNo(), unreadPersonNoticeNumber);
                    offset = offset + noticeArray.length();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                noticeData = null;
            }
        }
        return list;
    }

    @OnItemClick(R.id.lv_personalmsg)
    public void getClickItem(int position) {
        Intent intent = new Intent((MessageScreenActivity) context, MessageDetailActiviy.class);
        intent.putExtra("msgMap", (Serializable) tempNoticeList.get(position));
        startActivityForResult(intent, WILL_HAS_UPDATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        tempNoticeList.clear();
        offset = 1;
        unreadPersonNoticeNumber = 0;
        mListener.doDataRequest(null);
    }

}
