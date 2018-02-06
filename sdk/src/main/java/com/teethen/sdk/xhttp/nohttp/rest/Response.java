package com.teethen.sdk.xhttp.nohttp.rest;

/**
 * <p>Http response, Including header information and response packets.</p>
 * Created in Oct 15, 2017 8:55:37 PM.
 *
 * @param <T> a generic, on behalf of you can accept the result type,.It should be with the {@link Request},
 * {@link OnResponseListener}.
 * @author xingq.
 */

import com.teethen.sdk.xhttp.nohttp.Headers;

/**
 * <p>Http response, Including header information and response packets.</p>
 * Created in Oct 15, 2015 8:55:37 PM.
 *
 * @param <T> The handle data type, it should be with the {@link Request}, {@link OnResponseListener}.
 * @author xingq.
 */
public interface Response<T> {

    /**
     * Get the Request.
     */
    Request<T> request();

    /**
     * Get the response code of handle.
     *
     * @return response code.
     */
    int responseCode();

    /**
     * Request is executed successfully.
     *
     * @return True: Succeed, false: failed.
     */
    boolean isSucceed();

    /**
     * Whether the data returned from the cache.
     *
     * @return True: the data from cache, false: the data from server.
     */
    boolean isFromCache();

    /**
     * Get http response headers.
     *
     * @return {@link Headers}.
     */
    Headers getHeaders();

    /**
     * Get handle results.
     *
     * @return {@link T}.
     */
    T get();

    /**
     * When the handle fail to getList the exception type.
     *
     * @return The exception.
     */
    Exception getException();

    /**
     * Gets the tag of handle.
     *
     * @return {@link Object}.
     */
    Object getTag();

    /**
     * Gets the millisecond of handle.
     *
     * @return {@link Long}.
     */
    long getNetworkMillis();
}