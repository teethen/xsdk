package com.teethen.sdk.xwidget.baseadapter.recyclermore.interfaces;

import com.teethen.sdk.xwidget.baseadapter.recyclermore.ViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
