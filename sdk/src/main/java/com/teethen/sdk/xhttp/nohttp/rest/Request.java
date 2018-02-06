package com.teethen.sdk.xhttp.nohttp.rest;

import android.text.TextUtils;

import com.teethen.sdk.xhttp.nohttp.BasicRequest;
import com.teethen.sdk.xhttp.nohttp.Headers;
import com.teethen.sdk.xhttp.nohttp.RequestMethod;

/**
 * <p>
 * Support the characteristics of the queue.
 * </p>
 * Created by xingq on Oct 16, 2017 8:22:06 PM.
 */
public abstract class Request<Result> extends BasicRequest<Request> {
    /**
     * Cache key.
     */
    private String mCacheKey;
    /**
     * If just read from cache.
     */
    private CacheMode mCacheMode = CacheMode.DEFAULT;

    /**
     * Create a handle, handle method is {@link RequestMethod#GET}.
     *
     * @param url handle address, like: http://www.nohttp.net.
     */
    public Request(String url) {
        this(url, RequestMethod.GET);
    }

    /**
     * Create a handle
     *
     * @param url           handle address, like: http://www.nohttp.net.
     * @param requestMethod handle method, like {@link RequestMethod#GET}, {@link RequestMethod#POST}.
     */
    public Request(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    /**
     * Set the handle cache primary key, it should be globally unique.
     *
     * @param key unique key.
     */
    public Request setCacheKey(String key) {
        this.mCacheKey = key;
        return this;
    }

    /**
     * Get key of cache data.
     *
     * @return cache key.
     */
    public String getCacheKey() {
        return TextUtils.isEmpty(mCacheKey) ? url() : mCacheKey;
    }

    /**
     * Set the cache mode.
     *
     * @param cacheMode The value from {@link CacheMode}.
     */
    public Request setCacheMode(CacheMode cacheMode) {
        this.mCacheMode = cacheMode;
        return this;
    }

    /**
     * He got the handle cache mode.
     *
     * @return value from {@link CacheMode}.
     */
    public CacheMode getCacheMode() {
        return mCacheMode;
    }

    /**
     * Parse handle results for generic objects.
     *
     * @param responseHeaders response headers of server.
     * @param responseBody    response data of server.
     * @return your response result.
     * @throws Exception parse error.
     */
    public abstract Result parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception;

}
