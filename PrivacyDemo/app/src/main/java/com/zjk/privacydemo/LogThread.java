package com.zjk.privacydemo;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogThread extends Thread{

    LogDatabase database;
    LogInfo logInfo;
    String packageName;

    LogThread(String packageName, String log, Context context) {
        Date dateTime = new Date(System.currentTimeMillis());
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy年MM月dd日-hh时mm分ss秒");
        String currentTime = pattern.format(dateTime);
        this.logInfo = new LogInfo(log, currentTime);
        this.packageName = packageName;
        database = new LogDatabase(context);
    }

    @Override
    public void run() {
        super.run();
        database.open();
        database.insertData(packageName, logInfo);
    }




}
