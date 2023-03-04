package com.zjk.privacydemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionUtils {

    private static String[] PERMISSIONS_READ_AND_WRITE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static boolean isGrantExternalRW(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int writeStoragePermission = activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readStoragePermission = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writeStoragePermission != PackageManager.PERMISSION_GRANTED ||
                    readStoragePermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(PERMISSIONS_READ_AND_WRITE, requestCode);
                return false;
            }
        }
        return true;
    }
}

