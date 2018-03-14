package com.teethen.sdk.xupdater;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.teethen.sdk.xutil.ToastUtil;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xingq on 2018/2/17.
 */
public class Downloader {

    private static final int STATE_SUCCESS = -1;
    private static final int STATE_ERROR = -2;

    private Context context;
    private AppInfo appInfo;
    private long fileLength;
    private long currLength;

    private boolean isContinue;
    private int lastProgress = 0;
    private OnDownLoadListener onDownLoadListener;

    public Downloader(Context context, AppInfo appInfo) {
        this.context = context;
        this.appInfo = appInfo;
        this.fileLength = appInfo.appSize;
    }

    public void downloadApk() {
        new Thread(() -> {
            try {
                isContinue = true;
                HttpURLConnection conn = (HttpURLConnection) new URL(appInfo.appDownloadUrl).openConnection();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                currLength = UpdaterUtil.getCurrLengthValue(context, appInfo.apkName);
                conn.setRequestProperty("Range", "bytes=" + currLength + "-" + fileLength);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    InputStream is = conn.getInputStream();
                    RandomAccessFile raf = new RandomAccessFile(appInfo.apkLocalUrl, "rw");
                    raf.setLength(fileLength);
                    raf.seek(currLength);

                    int len;
                    int lastProgress = 0;
                    byte[] buffer = new byte[1024];

                    while ((len = is.read(buffer)) != -1) {
                        if (!isContinue) {
                            break;
                        }

                        if (onDownLoadListener != null) {
                            currLength += len;
                            int progress = (int) ((float) currLength / (float) fileLength * 100);
                            if (lastProgress != progress) {
                                lastProgress = progress;
                                handler.sendEmptyMessage(progress);
                            }
                        }

                        raf.write(buffer, 0, len);
                    }

                    UpdaterUtil.closeQuietly(is, raf);

                    if (!isContinue && currLength < fileLength) {
                        UpdaterUtil.putCurrLengthValue(context, appInfo.apkName, currLength);
                    } else {
                        UpdaterUtil.putCurrLengthValue(context, appInfo.apkName, 0);
                        handler.sendEmptyMessage(100);
                        handler.sendEmptyMessage(STATE_SUCCESS);
                    }
                } else {
                    handler.sendMessage(handler.obtainMessage(STATE_ERROR, "下载受限"));
                }
            } catch (ConnectException e) {
                UpdaterUtil.putCurrLengthValue(context, appInfo.apkName, 0);
                Message msg = new Message();
                msg.what = STATE_ERROR;
                msg.obj = "网络连接失败，无法连接到服务器";//e.getMessage();
                handler.sendMessage(msg);
            } catch (Exception e) {
                UpdaterUtil.putCurrLengthValue(context, appInfo.apkName, 0);
                handler.sendEmptyMessage(STATE_ERROR);
            } finally {
                isContinue = false;
            }
        }).start();
    }

    public boolean isContinue() {
        return isContinue;
    }

    public boolean isContinueWithAppId(String appId) {
        return isContinue && appInfo.appId.equalsIgnoreCase(appId);
    }

    public void cancel() {
        isContinue = false;
    }

    public void setOnDownLoadListener(OnDownLoadListener onDownLoadListener) {
        this.onDownLoadListener = onDownLoadListener;
    }

    public interface OnDownLoadListener {
        void onProgress(int progress);

        void onSuccess();

        void onError();
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (onDownLoadListener != null) {
                switch (msg.what) {
                    case STATE_SUCCESS:
                        onDownLoadListener.onSuccess();
                        break;
                    case STATE_ERROR:
                        onDownLoadListener.onError();
                        if (msg.obj != null && msg.obj instanceof String) {
                            ToastUtil.showLongToast(context, msg.obj.toString());
                        }
                        break;
                    default:
                        if (lastProgress != msg.what) {
                            lastProgress = msg.what;
                            onDownLoadListener.onProgress(msg.what);
                        }
                        break;
                }
            }
        }
    };
}
