package com.teethen.sdk.xwidget.ninegridview;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by xingq on 2017/6/29.
 */

public interface ItemImageLongClickListener<T> {
    void onItemImageLongClick(Context context, ImageView imageView, int index, List<T> list);
}
