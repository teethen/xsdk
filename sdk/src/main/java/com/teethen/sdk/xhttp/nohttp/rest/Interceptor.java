package com.teethen.sdk.xhttp.nohttp.rest;

/**
 * Created by xingq on 2018/1/12.
 */
public interface Interceptor {

    /**
     * When any one request will be launched.
     */
    <T> Response<T> intercept(RequestHandler handler, Request<T> request);
}
