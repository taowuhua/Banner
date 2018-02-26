package com.bank.quickpay.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;


/**
 * Created by Administrator on 2017/2/4.
 */

public class UriUtils {
    public static Uri fromFile(File f, Context context){
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion<24){
            return Uri.fromFile(f);
        }else {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
        }
    }
}
