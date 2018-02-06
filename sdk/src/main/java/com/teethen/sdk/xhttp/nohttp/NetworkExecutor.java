package com.teethen.sdk.xhttp.nohttp;

/**
 * Created by xingq on 2017/10/15.
 */
public interface NetworkExecutor {

    /**
     * Perform network connection.
     *
     * @param request {@link BasicRequest}.
     * @return {@link Network}.
     * @throws Exception maybe.
     */
    Network execute(BasicRequest<?> request) throws Exception;

}
