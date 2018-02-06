package com.teethen.sdk.xhttp.nohttp.able;

/**
 * <p>Cancel interface.</p>
 */
public interface Cancelable {

    /**
     * Cancel handle.
     */
    void cancel();

    /**
     * Whether has been cancelled.
     *
     * @return true: canceled, false: there is no cancel.
     */
    boolean isCanceled();

}
