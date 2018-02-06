package com.teethen.sdk.xhttp.nohttp.download;

import com.teethen.sdk.xhttp.nohttp.NoHttp;

/**
 * <p>Synchronize File Downloader.</p>
 * Created by xingq on 2017/10/12.
 */
public enum SyncDownloadExecutor {

    INSTANCE, AsyncRequestExecutor;

    private Downloader mDownloader;

    SyncDownloadExecutor() {
        mDownloader = new Downloader(NoHttp.getInitializeConfig().getNetworkExecutor());
    }

    /**
     * Start a download.
     *
     * @param what            what.
     * @param downloadRequest {@link DownloadRequest}.
     * @param listener        accept various download status callback..
     */
    public void execute(int what, DownloadRequest downloadRequest, DownloadListener listener) {
        mDownloader.download(what, downloadRequest, listener);
    }
}
