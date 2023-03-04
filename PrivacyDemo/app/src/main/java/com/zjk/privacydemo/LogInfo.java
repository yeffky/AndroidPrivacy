package com.zjk.privacydemo;

public class LogInfo {
    String log;
    String currentTime;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public LogInfo(String log, String currentTime) {
        this.log = log;
        this.currentTime = currentTime;
    }
}
