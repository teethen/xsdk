package com.teethen.sdk.xpermission;

import android.content.Context;

import java.util.List;

/**
 * <p>Rationale listener</p>
 * Created by xingq on 2017/9/10.
 */
public interface Rationale {

    /**
     * Show rationale of permissions to user.
     *
     * @param context     context.
     * @param permissions show rationale permissions.
     * @param executor    executor.
     */
    void showRationale(Context context, List<String> permissions, RequestExecutor executor);
}