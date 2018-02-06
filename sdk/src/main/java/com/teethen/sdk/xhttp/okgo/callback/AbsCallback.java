package com.teethen.sdk.xhttp.okgo.callback;

import com.teethen.sdk.xhttp.okgo.callback.*;
import com.teethen.sdk.xhttp.okgo.model.Progress;
import com.teethen.sdk.xhttp.okgo.model.Response;
import com.teethen.sdk.xhttp.okgo.request.base.Request;
import com.teethen.sdk.xhttp.okgo.utils.OkLogger;

/**
 * 描述：抽象的回调接口
 */
public abstract class AbsCallback<T> implements Callback<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void uploadProgress(Progress progress) {
    }

    @Override
    public void downloadProgress(Progress progress) {
    }
}
