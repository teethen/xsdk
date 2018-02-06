package com.teethen.sdk.xwidget.baseadapter.recyclerview.base;

/**
 * Created by xingq on 17/6/19.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
