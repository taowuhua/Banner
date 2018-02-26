package com.ryx.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.ryx.baselib.R;

/**
 * Author：xucc
 * date: 2017/11/30 13:24
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：RYX等待框
 */
public class RyxLoadDialogBuilder extends Dialog implements DialogInterface {
    private static RyxLoadDialogBuilder instance;
    private View mLoadDialogView;
    private RyxSpinKitView spinKitView;
    private Sprite drawable;
    private TextView tvMsg;
    private String showMsg;


    public static RyxLoadDialogBuilder getInstance(Context context) {

        if (instance == null) {
            synchronized (RyxLoadDialogBuilder.class) {
                if (instance == null &&context!=null) {
                    instance = new RyxLoadDialogBuilder(
                            context.getApplicationContext(), R.style.CustomProgressDialog);
                }
            }
        }
        return instance;
    }

    public void setMessage(String showMsg) {
        this.showMsg = showMsg;
        if (!TextUtils.isEmpty(this.showMsg)) {
            tvMsg.setText(this.showMsg);
        }
    }

    public void isCancel(boolean able){
        instance.setCancelable(able);
    }

    public void setTvMsgSize(float size){

        tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private RyxLoadDialogBuilder(Context context) {

        super(context);
        initView(context);
    }

    public void show() {
        //xucc将位置替换到setMessage方法中,动态设置提示信息
//        if (!TextUtils.isEmpty(this.showMsg)) {
//            tvMsg.setText(this.showMsg);
//        }
        super.show();
    }


    public RyxLoadDialogBuilder(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private RyxLoadDialogBuilder(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        mLoadDialogView = View.inflate(context, R.layout.base_widget_loading, null);
        spinKitView = (RyxSpinKitView) mLoadDialogView.findViewById(R.id.spin_kit);
        tvMsg = (TextView) mLoadDialogView.findViewById(R.id.tv_loading);
//        drawable = new CubeGrid();
        drawable = new Circle();
        spinKitView.setIndeterminateDrawable(drawable);
        setContentView(mLoadDialogView);
    }

}
