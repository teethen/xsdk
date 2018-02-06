package com.teethen.sdk.xpermission;

/**
 * <p>Request executor.</p>
 * Created by xingq on 2017/9/10.
 */
public interface RequestExecutor {

    /**
     * Go request permission.
     */
    void execute();

    /**
     * Cancel the operation.
     */
    void cancel();

}
