package com.teethen.sdk.xwidget.baseadapter.listview.base;

import com.teethen.sdk.xwidget.baseadapter.listview.ViewHolder;

/**
 * Created by xingq on 17/6/19.
 */
public interface ItemViewDelegate<T>
{
    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);
}
