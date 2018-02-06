package com.teethen.sdk.xhttp.nohttp.rest;

import com.teethen.sdk.xhttp.nohttp.RequestMethod;

/**
 * Based on the implementation of the queue handle.
 *
 * @deprecated use {@link Request} instead.
 */
@Deprecated
public abstract class RestRequest<Result> extends Request<Result> {

    /**
     * Create a handle, RequestMethod is {@link RequestMethod#GET}.
     *
     * @param url handle address, like: {@code http://www.nohttp.net}.
     */
    public RestRequest(String url) {
        this(url, RequestMethod.GET);
    }

    /**
     * Create a handle
     *
     * @param url           handle address, like: {@code http://www.nohttp.net}.
     * @param requestMethod handle method, like {@link RequestMethod#GET}, {@link RequestMethod#POST}.
     */
    public RestRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

}