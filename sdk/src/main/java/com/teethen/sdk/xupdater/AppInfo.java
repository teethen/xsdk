package com.teethen.sdk.xupdater;

import java.io.Serializable;

/**
 * Created by xingq on 2018/3/12.
 */

public class AppInfo implements Serializable {
    // 从 服务器 获取的信息
    public String appName = "";
    //public int appVersionCode;
    public String appVersionName = "";
    public String appChangeLog = "";
    public String appDownloadUrl = "";
    public long appSize = 0;

    // 自己添加的信息
    public String appId = "";
    public String apkName = "";
    public String apkPath = "";
    public String apkLocalUrl = "";

    @Override
    public String toString() {
        return "AppInfo \n{" +
                "\n appName='" + appName +
                "'\n appVersionName='" + appVersionName +
                "'\n appChangeLog='" + appChangeLog +
                "'\n appDownloadUrl='" + appDownloadUrl +
                "'\n appSize=" + appSize +
                "'\n appId='" + appId +
                "'\n apkName='" + apkName +
                "'\n apkPath='" + apkPath +
                "'\n apkLocalUrl='" + apkLocalUrl +
                "'\n}";
    }
}