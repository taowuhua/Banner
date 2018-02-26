package com.bank.quickpay.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.bank.quickpay.R;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.rey.material.app.Dialog;
/**
 * Created by xucc on 2017/6/12.
 */

public class AppDownLoadDialog extends Dialog {
    NumberProgressBar numberProgressBar;
    TextView download_dialog_title;
    public AppDownLoadDialog(Context context) {
        super(context, R.style.SimpleDialogLight);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_appdownload);
        numberProgressBar= (NumberProgressBar) findViewById(R.id.numberprogressbar);
        download_dialog_title=(TextView) findViewById(R.id.download_dialog_title);
    }

    /**
     * 设置进度值
     * @param progress
     */
    public void setProgress(int progress){
        numberProgressBar.setProgress(progress);
    }

    /**
     * 设置下载框的标题
     * @param title
     */
    public void setDownloadDialogTitle(String title){
        download_dialog_title.setText(title);
    }

    /**
     * 默认下载框展示
     * @param context
     */
    public  static AppDownLoadDialog simpleShowDownLoadDialog(Context context){
        AppDownLoadDialog    appDownLoadDialog=new AppDownLoadDialog(context);
        appDownLoadDialog.setCancelable(false);
        appDownLoadDialog.setCanceledOnTouchOutside(false);
        appDownLoadDialog.show();
        appDownLoadDialog.setDownloadDialogTitle("正在升级"+context.getResources().getString(R.string.app_name));
        return appDownLoadDialog;
    }
}
