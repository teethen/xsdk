package com.teethen.sdk.xhttp.nohttp.error;

/**
 * <p>The target host not found.</p>
 * Created in 2017/12/25 10:49.
 *
 * @author xingq.
 */
public class UnKnownHostError extends Exception {

    private static final long serialVersionUID = 1149646L;

    public UnKnownHostError() {
    }

    public UnKnownHostError(String detailMessage) {
        super(detailMessage);
    }

    public UnKnownHostError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnKnownHostError(Throwable throwable) {
        super(throwable);
    }

}
