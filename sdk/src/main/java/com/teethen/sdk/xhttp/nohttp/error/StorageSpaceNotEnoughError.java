package com.teethen.sdk.xhttp.nohttp.error;

/**
 * <p>Specify the location of the file space is not enough.</p>
 * Created in 2017/12/25 11:50.
 *
 * @author xingq.
 */
public class StorageSpaceNotEnoughError extends Exception {

    private static final long serialVersionUID = 11786348L;

    public StorageSpaceNotEnoughError() {
    }

    public StorageSpaceNotEnoughError(String detailMessage) {
        super(detailMessage);
    }

    public StorageSpaceNotEnoughError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public StorageSpaceNotEnoughError(Throwable throwable) {
        super(throwable);
    }

}
