package com.teethen.sdk.xhttp.nohttp.able;

/**
 * <p>Finish interface.</p>
 */
public interface Finishable {

    /**
     * Finish handle.
     */
    void finish();

    /**
     * Whether they have been completed.
     *
     * @return true: finished, false: unfinished.
     */
    boolean isFinished();
}
