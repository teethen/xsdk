package com.teethen.sdk.xwidget.baseadapter.recyclermore.interfaces;

import com.teethen.sdk.xwidget.baseadapter.recyclermore.ViewHolder;

public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
