package com.teethen.sdk.xhttp.nohttp.rest;

import com.teethen.sdk.xhttp.nohttp.InitializationConfig;
import com.teethen.sdk.xhttp.nohttp.NoHttp;

/**
 * Synchronization handle executor.
 * Created by xingq on 2017/10/12.
 */
public enum SyncRequestExecutor {

    INSTANCE;

    private RequestHandler mRequestHandler;

    SyncRequestExecutor() {
        InitializationConfig initializationConfig = NoHttp.getInitializeConfig();
        mRequestHandler = new RequestHandler(
                initializationConfig.getCacheStore(),
                initializationConfig.getNetworkExecutor(),
                initializationConfig.getInterceptor()
        );
    }

    /**
     * Perform a handle.
     */
    public <T> Response<T> execute(Request<T> request) {
        return mRequestHandler.handle(request);
    }
}
