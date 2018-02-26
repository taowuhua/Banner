package com.bank.quickpay.widget;

import android.content.Context;

import com.bank.quickpay.R;


/**
 * 加载中对话框
 * Created by XCC on 2016/8/19.
 */
public class ProgressLoadDialog {
    private ProgressLoadDialogBuilder instance=null;
    public ProgressLoadDialogBuilder getInstance(Context context) {

        if (instance == null || !context.equals(instance.getmContext())) {
            synchronized (ProgressLoadDialogBuilder.class) {
                if (instance == null || !context.equals(instance.getmContext())) {
                    if(instance!=null){
                        instance=null;
                    }
                    instance = new ProgressLoadDialogBuilder(
                            context, R.style.CustomProgressDialog);
                    instance.setmContext( context);
                }
            }
        }
        return instance;
    }
}
