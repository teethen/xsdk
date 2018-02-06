package com.teethen.sdk.xpermission;

import android.support.annotation.NonNull;

/**
 * <p>Permission request.</p>
 * Created by xingq on 2017/9/9.
 */
public interface Request {

    /**
     * One or more permissions.
     */
    @NonNull
    Request permission(String... permissions);

    /**
     * One or more permission groups.
     */
    @NonNull
    Request permission(String[]... groups);

    /**
     * Set request rationale.
     */
    @NonNull
    Request rationale(Rationale listener);

    /**
     * Action to be taken when all permissions are granted.
     */
    @NonNull
    Request onGranted(Action granted);

    /**
     * Action to be taken when all permissions are denied.
     */
    @NonNull
    Request onDenied(Action denied);

    /**
     * Request permission.
     */
    void start();

}