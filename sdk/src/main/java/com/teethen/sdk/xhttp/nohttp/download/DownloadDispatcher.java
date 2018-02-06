package com.teethen.sdk.xhttp.nohttp.download;

import android.os.Process;

import com.teethen.sdk.xhttp.nohttp.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Download queue polling thread.
 *
 * @author xingq.
 */
class DownloadDispatcher extends Thread {

    private final BlockingQueue<DownloadRequest> mRequestQueue;
    private final Map<DownloadRequest, Messenger> mMessengerMap;

    private boolean mQuit = false;

    public DownloadDispatcher(BlockingQueue<DownloadRequest> requestQueue, Map<DownloadRequest, Messenger> messengerMap) {
        this.mRequestQueue = requestQueue;
        this.mMessengerMap = messengerMap;
    }

    /**
     * Quit this thread.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (!mQuit) {
            final DownloadRequest request;
            try {
                request = mRequestQueue.take();
            } catch (InterruptedException e) {
                if (mQuit)
                    return;
                continue;
            }

            if (request.isCanceled()) {
                mRequestQueue.remove(request);
                mMessengerMap.remove(request);
                Logger.d(request.url() + " is canceled.");
                continue;
            }

            request.start();
            SyncDownloadExecutor.INSTANCE.execute(0, request, new ListenerDelegate(request, mMessengerMap));
            request.finish();

            // remove it from queue.
            mRequestQueue.remove(request);
            mMessengerMap.remove(request);
        }
    }
}
