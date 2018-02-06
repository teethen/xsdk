package com.teethen.sdk.xhttp.okgo.adapter;

import com.teethen.sdk.xhttp.okgo.callback.Callback;
import com.teethen.sdk.xhttp.okgo.model.Response;
import com.teethen.sdk.xhttp.okgo.request.base.Request;

public interface Call<T> {
    /** 同步执行 */
    Response<T> execute() throws Exception;

    /** 异步回调执行 */
    void execute(Callback<T> callback);

    /** 是否已经执行 */
    boolean isExecuted();

    /** 取消 */
    void cancel();

    /** 是否取消 */
    boolean isCanceled();

    Call<T> clone();

    Request getRequest();
}
