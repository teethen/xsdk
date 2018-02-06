package com.teethen.sdk.xwidget.baseadapter.recyclermore.interfaces;

import com.teethen.sdk.xwidget.baseadapter.recyclermore.ViewHolder;

public interface OnSwipeMenuClickListener<T> {
    void onSwipMenuClick(ViewHolder viewHolder, T data, int position);
}
