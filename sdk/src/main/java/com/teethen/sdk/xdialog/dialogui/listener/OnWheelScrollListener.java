package com.teethen.sdk.xdialog.dialogui.listener;

import com.teethen.sdk.xdialog.dialogui.widget.WheelView;

/**
 * 描 述：wheel滚动监听事件
 * Wheel scroll listener interface.
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     *
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     *
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}
