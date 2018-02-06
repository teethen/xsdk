package com.teethen.sdk.xhttp.nohttp.download;

import com.teethen.sdk.xhttp.nohttp.Headers;

/**
 * Created by xingq on 2017/12/19.
 */
public class SimpleDownloadListener implements DownloadListener {

    @Override
    public void onDownloadError(int what, Exception exception) {

    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {

    }

    @Override
    public void onFinish(int what, String filePath) {

    }

    @Override
    public void onCancel(int what) {

    }

}
