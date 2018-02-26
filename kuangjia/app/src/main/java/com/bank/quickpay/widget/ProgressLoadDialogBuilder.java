package com.bank.quickpay.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;

/**
 * 加载中对话框
 * Created by laomao on 16/5/6.
 */
public class ProgressLoadDialogBuilder extends Dialog implements DialogInterface {

    private  Context mContext;
    private View mLoadDialogView;
    private ProgressSpinKitView spinKitView;
    private Sprite drawable;
    private TextView tvMsg;
    private String showMsg;


    public  void setmContext(Context context){
        this.mContext=context;
    }
    public Context getmContext(){
        return this.mContext;
    }
    public void setMessage(String showMsg) {
        this.showMsg = showMsg;
        if (!TextUtils.isEmpty(this.showMsg)) {
            tvMsg.setText(this.showMsg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ProgressLoadDialogBuilder(Context context) {

        super(context);
        initView(context);
    }

    @Override
    public void show() {
        //xucc将位置替换到setMessage方法中,动态设置提示信息
//        if (!TextUtils.isEmpty(this.showMsg)) {
//            tvMsg.setText(this.showMsg);
//        }
        super.show();
    }


    public ProgressLoadDialogBuilder(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private ProgressLoadDialogBuilder(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        mLoadDialogView = View.inflate(context, R.layout.widget_loading, null);
        spinKitView = (ProgressSpinKitView) mLoadDialogView.findViewById(R.id.spin_kit);
        tvMsg = (TextView) mLoadDialogView.findViewById(R.id.tv_loading);
//        drawable = new CubeGrid();
//        drawable = new Circle();
//        drawable=new FoldingCube();
        drawable = new ChasingDots();
        spinKitView.setIndeterminateDrawable(drawable);
//        mLoadDialogView.getBackground().setAlpha(80);
        setContentView(mLoadDialogView);
    }

}
