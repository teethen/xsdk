package com.teethen.xsdk.ninegrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.teethen.sdk.xwidget.ninegrid.complex.NineGridView;
import com.teethen.xsdk.R;

/**
 * Created by xingq on 2018/2/24.
 */

public class GlideImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
            //.placeholder(R.drawable.ic_default_color)
            //.error(R.drawable.ic_default_color)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.error_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}

/*在Application中初始化
@Override
public void onCreate() {
    super.onCreate();
    NineGridView.setImageLoader(new GlideImageLoader());
}*/