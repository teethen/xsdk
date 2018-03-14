package com.teethen.sdk.xupdater;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.teethen.sdk.xutil.FileUtil;
import com.teethen.sdk.xutil.NetworkUtil;
import com.teethen.sdk.xutil.ToastUtil;

import java.io.File;

/**
 * @author xingq on 2018/2/17.
 */
public class XUpdater {

    private Context context;
    private String apiToken;
    private String appId;
    private String appVersionUrl;
    private String apkPath;
    private AppInfo appInfo;
    private boolean isBackgroundDownload = false;
    private boolean forceShowDialog = false;

    private UpDialog dialog;
    private Downloader downloader;
    private UpNotification notification;

    public XUpdater(Context context) {
        this(context, null, null);
    }

    public XUpdater(Context context, String apiToken, String appId) {
        this.context = context;
        this.apiToken = apiToken;
        this.appId = appId;
    }

    public XUpdater apiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    public XUpdater appId(String appId) {
        this.appId = appId;
        return this;
    }

    public XUpdater apkPath(String apkPath) {
        this.apkPath = apkPath;
        return this;
    }

    public XUpdater forceShowDialog(boolean enable) {
        this.forceShowDialog = enable;
        return this;
    }

    public void checkVersion(String url, boolean... showLatestTip) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            showToast("网络连接失败");
            return;
        }
        if (TextUtils.isEmpty(apiToken) || TextUtils.isEmpty(appId)) {
            showToast("请设置 API TOKEN && APP ID");
            return;
        }

        //this.appVersionUrl = "http://api.teethen.com/apps?app_id=" + appId + "&api_token=" + apiToken;
        this.appVersionUrl = String.format(url, appId, apiToken);

        if (downloader != null && downloader.isContinue()) {
            showToast("正在下载【" + appInfo.apkName + "】，请稍后");
            return;
        }

        try {
            UpPermissionUtil.getInstant().requestPermission(context, new UpPermissionUtil.OnPermissionCallback() {
                @Override
                public void onGranted() {
                    requestAppInfo(showLatestTip);
                }

                @Override
                public void onDenied() {
                    showToast("申请权限未通过");//UpdaterUtil.loggerError("申请权限未通过");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestAppInfo(boolean... showTip) {
        new Thread(() -> {
            appInfo = new AppInfoUtil().requestAppInfo(appVersionUrl);
            if (appInfo == null) {
                return;
            }

            String apkName = appInfo.appName + "_" + appInfo.appVersionName + ".apk";
            if (TextUtils.isEmpty(apkPath)) {
                apkPath = Environment.getExternalStorageDirectory() + File.separator + "download" + File.separator;
            }

            appInfo.appId = appId;
            appInfo.apkName = apkName;
            appInfo.apkPath = apkPath;
            appInfo.apkLocalUrl = apkPath + apkName;
            //UpdaterUtil.logger(appInfo.toString());
            //boolean needUpdate = appInfo.appVersionCode > UpdaterUtil.getVersionCode(context);
            boolean needUpdate = compareVersion(appInfo.appVersionName, UpdaterUtil.getVersionName(context)) > 0;
            if (!needUpdate) {
                if (showTip != null && showTip[0] == true) {
                    ToastUtil.showToast(context, "已经是最新版本");
                }
            } else if (forceShowDialog || needUpdate) {
                UpdaterUtil.runOnMainThread(this::initUpDialog);
            }
        }).start();
    }

    private void initUpDialog() {
        dialog = new UpDialog();
        dialog.showAppInfoDialog(context, appInfo);
        dialog.setOnClickDownloadDialogListener(new UpDialog.OnClickDownloadDialogListener() {
            @Override
            public void onClickDownload(DialogInterface dialog) {
                downloadApk();
            }

            @Override
            public void onClickBackgroundDownload(DialogInterface dialog) {
                isBackgroundDownload = true;
            }

            @Override
            public void onClickCancelDownload(DialogInterface dialog) {
                downloader.cancel();
            }
        });
    }

    private void downloadApk() {
        try {
            if (FileUtil.isExternalStorageWritable()) {
                File apkFile = new File(appInfo.apkLocalUrl);
                if (apkFile.exists()) {
                    UpdaterUtil.installApk(context, appInfo.apkLocalUrl);
                    return;
                }

                notification = new UpNotification();
                notification.createBuilder(context);
                notification.setContentTitle("正在下载" + appInfo.appName);

                downloader = new Downloader(context.getApplicationContext(), appInfo);
                downloader.setOnDownLoadListener(new Downloader.OnDownLoadListener() {
                    @Override
                    public void onProgress(int progress) {
                        dialog.showDownloadDialog(context, progress);
                        if (isBackgroundDownload) {
                            notification.setContentText(progress + "%");
                            notification.notifyNotification(progress);
                        }
                    }

                    @Override
                    public void onSuccess() {
                        dialog.dismissDownloadDialog();
                        if (isBackgroundDownload) {
                            notification.cancel();
                        }
                        UpdaterUtil.installApk(context, appInfo.apkLocalUrl);
                    }

                    @Override
                    public void onError() {

                    }
                });
                downloader.downloadApk();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UpDialog getDialog() {
        return dialog;
    }

    public UpNotification getNotification() {
        return notification;
    }

    /**
     * 版本号比较
     * 结果说明：-1:version1小于version2，0:相等，1:version1大于version2
     * @param version1
     * @param version2
     * @return int
     */
    public int compareVersion(String version1, String version2) {
        try {
            if (version1.equals(version2)) {
                return 0;
            }
            String[] version1Array = version1.split("\\.");
            String[] version2Array = version2.split("\\.");
            int index = 0;
            //获取最小长度值
            int minLen = Math.min(version1Array.length, version2Array.length);
            int diff = 0;
            //循环判断每位的大小
            while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
                index++;
            }
            if (diff == 0) {
                // 如果位数不一致，比较多余位数
                for (int i = index; i < version1Array.length; i++) {
                    if (Integer.parseInt(version1Array[i]) > 0) {
                        return 1;
                    }
                }
                for (int i = index; i < version2Array.length; i++) {
                    if (Integer.parseInt(version2Array[i]) > 0) {
                        return -1;
                    }
                }
                return 0;
            } else {
                return diff > 0 ? 1 : -1;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private void showToast(String text) {
        //Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        ToastUtil.showToast(context, text);
    }
}
