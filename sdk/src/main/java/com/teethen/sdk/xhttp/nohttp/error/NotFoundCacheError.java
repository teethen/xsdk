package com.teethen.sdk.xhttp.nohttp.error;

/**
 * Created in 2017/12/3 13:34.
 *
 * @author xingq.
 */
public class NotFoundCacheError extends Exception {

    private static final long serialVersionUID = 115481468L;

    public NotFoundCacheError() {
    }

    public NotFoundCacheError(String detailMessage) {
        super(detailMessage);
    }

    public NotFoundCacheError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NotFoundCacheError(Throwable throwable) {
        super(throwable);
    }
}
