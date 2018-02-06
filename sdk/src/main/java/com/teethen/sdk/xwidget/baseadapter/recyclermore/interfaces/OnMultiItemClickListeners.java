package com.teethen.sdk.xwidget.baseadapter.recyclermore.interfaces;

import com.teethen.sdk.xwidget.baseadapter.recyclermore.ViewHolder;

public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
