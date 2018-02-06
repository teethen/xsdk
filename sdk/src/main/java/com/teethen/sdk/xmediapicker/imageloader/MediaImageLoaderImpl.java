package com.teethen.sdk.xmediapicker.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.teethen.sdk.R;

public class MediaImageLoaderImpl implements MediaImageLoader {

    public MediaImageLoaderImpl(Context context) {

    }

    @Override
    public void displayImage(Uri uri, ImageView imageView) {
        Glide.with(imageView.getContext()).load(uri).placeholder(R.drawable.default_image).error(R.drawable.ic_picker_imagefail).into(imageView);
    }
}