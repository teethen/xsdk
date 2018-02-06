package com.teethen.sdk.xhttp.nohttp.download;

import com.teethen.sdk.xhttp.nohttp.Headers;
import com.teethen.sdk.xhttp.nohttp.error.NetworkError;
import com.teethen.sdk.xhttp.nohttp.error.ServerError;
import com.teethen.sdk.xhttp.nohttp.error.StorageReadWriteError;
import com.teethen.sdk.xhttp.nohttp.error.StorageSpaceNotEnoughError;
import com.teethen.sdk.xhttp.nohttp.error.TimeoutError;
import com.teethen.sdk.xhttp.nohttp.error.URLError;
import com.teethen.sdk.xhttp.nohttp.error.UnKnownHostError;

/**
 * The download process monitor.
 * @author xingq.
 */
public interface DownloadListener {

    /**
     * An error occurred while downloading.
     *
     * @param what      which is used to mark the download tasks.
     * @param exception error types. Error types include the following:
     *                  <p>{@link NetworkError} The network is not available, please check
     *                  the network.</p>
     *                  <p>{@link ServerError} When the response code is more than 400, you
     *                  need to look at the response code specific how much is the judgement is something wrong.</p>
     *                  <p>{@link StorageReadWriteError} An error occurred when read/write
     *                  memory CARDS, please check the memory card.</p>
     *                  <p>{@link StorageSpaceNotEnoughError} There is insufficient space on
     *                  the memory card, please check the memory card.</p>
     *                  <p>{@link TimeoutError} Timeout connecting to the server or read the
     *                  file.</p>
     *                  <p>{@link UnKnownHostError} Is not found in the network of the
     *                  target server.</p>
     *                  <p>{@link URLError} Download url is wrong.</p>
     */
    void onDownloadError(int what, Exception exception);

    /**
     * When this download task starts the callback method.
     *
     * @param what            which is used to mark the download tasks.
     * @param isResume        whether to continue to download, if it is true that has download before, and have already.
     *                        download the file size is not zero.
     * @param rangeSize       hTTP starting point size, the size of the data already exists.
     * @param responseHeaders server response headers.
     * @param allCount        total file size.
     */
    void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount);

    /**
     * When the download process change.
     *
     * @param what      which is used to mark the download tasks.
     * @param progress  this method is the time to change the progress of the download.
     * @param fileCount have downloaded the file size.
     * @param speed     the number of bytes downloaded in a second, such as: {@code 1048576 b/s} = {@code 1024 kb/s}
     *                  = {@code 1 M/s}.
     */
    void onProgress(int what, int progress, long fileCount, long speed);

    /**
     * Download is complete.
     *
     * @param what     which is used to mark the download tasks.
     * @param filePath where is the file after the download is complete.
     */
    void onFinish(int what, String filePath);

    /**
     * Download handle is canceled.
     *
     * @param what which is used to mark the download tasks.
     */
    void onCancel(int what);
}