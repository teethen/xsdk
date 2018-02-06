package com.teethen.sdk.xhttp.nohttp.able;

/**
 * <p>Start interface.</p>
 */
public interface Startable {

    /**
     * Start handle.
     */
    void start();

    /**
     * Whether has already begun.
     *
     * @return true: has already started, false: haven't started.
     */
    boolean isStarted();
}
