package com.teethen.sdk.xhttp.nohttp.rest;

/**
 * <p>
 * NoHttp caching pattern, the default value is {@link CacheMode#DEFAULT}, other value may be
 * {@link CacheMode#REQUEST_NETWORK_FAILED_READ_CACHE}, {@link CacheMode#ONLY_READ_CACHE},
 * {@link CacheMode#ONLY_REQUEST_NETWORK}, {@link CacheMode#NONE_CACHE_REQUEST_NETWORK}.
 * </p>
 * Created in 2017/9/20 23:17.
 *
 * @author xingq.
 */
public enum CacheMode {

    /**
     * The default mode, according to the standard HTTP protocol cache, such as response header is 304.
     */
    DEFAULT,

    /**
     * If the handle is successful return data server, if the handle is returned failure cache data, if does not
     * cache the data failed.
     */
    REQUEST_NETWORK_FAILED_READ_CACHE,

    /**
     * If there is no cache handle, it returns the cache cache exists.
     */
    NONE_CACHE_REQUEST_NETWORK,

    /**
     * If the cache exists, the handle is successful, other wise is failed.
     */
    ONLY_READ_CACHE,

    /**
     * Just handle to the server, can't read cache anyway, also won't add cache related to head to the handle.
     */
    ONLY_REQUEST_NETWORK
}