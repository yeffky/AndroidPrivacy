package com.zjk.privacydemo.utils;

import android.content.Intent;

import de.robv.android.xposed.XposedBridge;

public class LogUtils {

    public static String getRequestLogInfo(String packageName, Integer requestCode) {
        String log;
        switch (requestCode) {
            case 1001:
                log = packageName + "：申请电话权限";
                break;
            case 1002:
                log = packageName + "：申请写入外存权限";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
        return log;
    }

    public static String getInvokeLogInfo(String packageName, String action) {
        String log;
        log = packageName + action;
        return log;
    }
}
