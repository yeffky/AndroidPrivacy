package com.zjk.privacydemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zjk.privacydemo.utils.LogUtils;

import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTrack implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        //申请获取权限
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                ClassLoader classLoader = context.getClassLoader();
                Class<?> clazz = classLoader.loadClass("com.zjk.permissiondemo.MainActivity");
                XposedHelpers.findAndHookMethod(clazz, "request_permission", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String logInfo = LogUtils.getRequestLogInfo(loadPackageParam.packageName, (Integer) param.args[0]);
                        XposedBridge.log(logInfo);

                        new LogThread(loadPackageParam.packageName, logInfo, context).start();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });

                Class<?> clazz_intent = classLoader.loadClass("java.io.Writer");
                XposedHelpers.findAndHookMethod(clazz_intent, "write", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("zjk123", "write");
                        String logInfo = LogUtils.getInvokeLogInfo(loadPackageParam.packageName, "调用写入权限");
                        XposedBridge.log(logInfo);

                        new LogThread(loadPackageParam.packageName, logInfo, context).start();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
            }
        });
    }
}
