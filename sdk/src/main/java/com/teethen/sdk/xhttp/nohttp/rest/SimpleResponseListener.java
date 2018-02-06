package com.teethen.sdk.xhttp.nohttp.rest;

/**
 * <p>Simple {@link SimpleResponseListener}.</p>
 * Created on 2017/12/7.
 *
 * @author xingq.
 */
public abstract class SimpleResponseListener<T> implements OnResponseListener<T> {
    @Override
    public void onStart(int what) {
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
    }

    @Override
    public void onFailed(int what, Response<T> response) {
    }

    @Override
    public void onFinish(int what) {
    }
}
