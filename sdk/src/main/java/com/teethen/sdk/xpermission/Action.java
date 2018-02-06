package com.teethen.sdk.xpermission;

import java.util.List;

/**
 * Created by xingq on 2018/1/1.
 */

public interface Action {

    /**
     * An action.
     *
     * @param permissions the current action under permissions.
     */
    void onAction(List<String> permissions);

}
