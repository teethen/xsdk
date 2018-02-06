package com.teethen.sdk.xhttp.nohttp.error;

/**
 * <p>The URL specified is incorrect.</p>
 * Created in 2017/12/25 9:49.
 *
 * @author xingq.
 */
public class URLError extends Exception {

    private static final long serialVersionUID = 114946L;

    public URLError() {
    }

    public URLError(String detailMessage) {
        super(detailMessage);
    }

    public URLError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public URLError(Throwable throwable) {
        super(throwable);
    }

}
