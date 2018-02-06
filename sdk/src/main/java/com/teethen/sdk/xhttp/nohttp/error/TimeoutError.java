package com.teethen.sdk.xhttp.nohttp.error;

/**
 * <p>Request connection timeout.</p>
 * Created in 2017/12/25 10:17.
 *
 * @author xingq.
 */
public class TimeoutError extends Exception {

    private static final long serialVersionUID = 1164986L;

    public TimeoutError() {
    }

    public TimeoutError(String detailMessage) {
        super(detailMessage);
    }

    public TimeoutError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TimeoutError(Throwable throwable) {
        super(throwable);
    }

}
