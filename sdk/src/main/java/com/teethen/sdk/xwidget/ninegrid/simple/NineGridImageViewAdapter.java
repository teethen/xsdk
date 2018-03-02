package com.teethen.sdk.xwidget.ninegrid.simple;

import android.content.Context;
import android.widget.ImageView;
import java.util.List;

public abstract class NineGridImageViewAdapter<T> {
    protected abstract void onDisplayImage(Context context, ImageView imageView, T t);

    protected void onItemImageClick(Context context, ImageView imageView, int index, List<T> list) { }

    protected void onItemImageLongClick(Context context, ImageView imageView, int index, List<T> list) { }

    protected ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}