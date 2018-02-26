package com.ryx.credit.tnh.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Log工具类
 */
public class LogUtil {
    public static boolean logdebug=true;
    public static String logtag ="ryx";


    public static void setLogdebug(boolean logdebug) {
        LogUtil.logdebug = logdebug;
    }

    /**
     * Logger日志展出
     * @param msg
     */
    public static void showLog(String msg) {
        if (logdebug) {
            Logger.v(msg);
        }
    }

    /**
     * Logger日志dbug输出所有类型
     * @param msg
     */
    public static void dshowLog(Object msg) {
        if (logdebug) {
            Logger.d(msg);
        }
    }
    /**
     * JSON格式展示msg(输入参数必须json格式)
     * @param msg
     */
    public static void showJsonLog(String msg){
        if (logdebug) {
            Logger.json(msg);
        }
    }

    /**
     * xml格式展示msg(输入参数必须为xml格式)
     * @param msg
     */
    public static  void showXmlLog(String msg){
        if (logdebug) {
            Logger.xml(msg);
        }
    }
    public static void showLog(String TAG, String msg) {
        if (logdebug) {
            if(logtag.equals(TAG)){
                Logger.v(msg);
            }else{
                Log.i(TAG,  msg);
            }
        }
    }

    public static void showToast(final Context mContext, final String content) {
        Toast toast = Toast.makeText(mContext, content,
                Toast.LENGTH_SHORT);
        View view=toast.getView();
        try {
            if(view instanceof LinearLayout){
                LinearLayout linearLayout = (LinearLayout)view ;
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setTextSize(12);
            }else if(view instanceof RelativeLayout){
                RelativeLayout relativeLayout = (RelativeLayout)view ;
                TextView messageTextView = (TextView) relativeLayout.getChildAt(0);
                messageTextView.setTextSize(12);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        toast.show();
    }

    public static void showToast(final Context mContext, final String content,String up) {
        Toast toast = Toast.makeText(mContext, content,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void setTextViewContent(final AppCompatActivity activity,
                                          final TextView textView, final String content) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (textView != null) {
                    textView.setText(content);
                }

            }
        });
    }

    /**
     * 仅用于较大日志文件输出打印
     * @param bigContent
     */
    public static void writeTxtToFile(String bigContent){
        if (logdebug) {
//            FileUtil.writeTxtToFile(bigContent,"/sdcard/ryxlog/","ryxlog_"+DateUtil.DateToStr(new Date())+".txt");
        }

    }



}
