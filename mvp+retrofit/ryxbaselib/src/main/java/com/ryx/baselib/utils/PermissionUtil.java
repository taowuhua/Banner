package com.ryx.baselib.utils;

import android.app.Activity;

import com.ryx.baselib.interfaces.IPermission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PermissionUtil {
    public static void checkPermission(final Activity activity, final IPermission iPermission, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            iPermission.permissionSuccess();
                        } else {
                            iPermission.permissionFail();
                        }
                    }
                });
    }

}
