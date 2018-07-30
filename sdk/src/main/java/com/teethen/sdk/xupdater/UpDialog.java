package com.teethen.sdk.xupdater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import com.teethen.sdk.xutil.FileUtil;

import java.lang.reflect.Field;

/**
 * @author xingq on 2018/2/17.
 */
public class UpDialog {

    private final int TEXT_SIZE = 14;
    private final String COLOR_NEGATIVE = "#9A9A9A";
    private final String COLOR_POSITIVE = "#333333";

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private OnClickDownloadDialogListener onClickDownloadDialogListener;

    public void showAppInfoDialog(Context context, AppInfo appInfo) {
        if (alertDialog == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("名称：").append(appInfo.appName);
            sb.append("\n版本：").append(appInfo.appVersionName);
            sb.append("\n大小：").append(FileUtil.getMeasureSize(appInfo.appSize));
            if (!TextUtils.isEmpty(appInfo.appChangeLog)) {
                sb.append("\n\n更新内容：").append(appInfo.appChangeLog);
            }

            alertDialog = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("检测到新版本")
                    .setMessage(sb)
                    .setPositiveButton("立即更新", (dialog, which) -> {
                        if (onClickDownloadDialogListener != null) {
                            onClickDownloadDialogListener.onClickDownload(dialog);
                        }
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create();
            alertDialog.show();

            //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(COLOR_POSITIVE));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor(COLOR_NEGATIVE));

            try {
                Field alert = AlertDialog.class.getDeclaredField("mAlert");
                alert.setAccessible(true);
                Object alertController = alert.get(alertDialog);
                Field messageView = alertController.getClass().getDeclaredField("mMessageView");
                messageView.setAccessible(true);
                TextView textView = (TextView) messageView.get(alertController);
                textView.setTextSize(TEXT_SIZE);
                textView.setTextColor(Color.parseColor(COLOR_NEGATIVE));
            } catch (IllegalAccessException e) {
                UpdaterUtil.loggerError(e);
            } catch (NoSuchFieldException e) {
                UpdaterUtil.loggerError(e);
            }
        }
    }

    public void showDownloadDialog(Context context, int progress) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMax(100);
            progressDialog.setTitle("正在下载");
            progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "后台下载", (dialog, which) -> {
                if (onClickDownloadDialogListener != null) {
                    onClickDownloadDialogListener.onClickBackgroundDownload(dialog);
                }
            });
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog, which) -> {
                if (onClickDownloadDialogListener != null) {
                    onClickDownloadDialogListener.onClickCancelDownload(dialog);
                }
            });
            progressDialog.show();

            //progressDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(COLOR_POSITIVE));
            progressDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor(COLOR_NEGATIVE));
        }

        if (progressDialog.isShowing()) {
            progressDialog.setProgress(progress);
        }
    }

    public void dismissDownloadDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setOnClickDownloadDialogListener(OnClickDownloadDialogListener onClickDownloadDialogListener) {
        this.onClickDownloadDialogListener = onClickDownloadDialogListener;
    }

    public interface OnClickDownloadDialogListener {
        void onClickDownload(DialogInterface dialog);

        void onClickBackgroundDownload(DialogInterface dialog);

        void onClickCancelDownload(DialogInterface dialog);
    }

}
