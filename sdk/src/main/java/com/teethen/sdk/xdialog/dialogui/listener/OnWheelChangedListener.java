package com.teethen.sdk.xdialog.dialogui.listener;

import com.teethen.sdk.xdialog.dialogui.widget.WheelView;

/**
 * 描 述：wheel改变监听事件
 * Wheel changed listener interface.
 * <p>The currentItemChanged() method is called whenever current wheel positions is changed:
 *  New Wheel position is set
 *  Wheel view is scrolled
 */
public interface OnWheelChangedListener {
    /**
     * Callback method to be invoked when current item changed
     *
     * @param wheel    the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
