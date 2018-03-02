package com.teethen.xsdk.ninegrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teethen.sdk.xwidget.ninegrid.complex.NineGridView;
import com.teethen.xsdk.R;

/**
 * Created by xingq on 2018/2/24.
 * Picasso 加载
 */

public class PicassoImageLoader implements NineGridView.ImageLoader {

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.error_image)
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
    NineGridView.setImageLoader(new PicassoImageLoader());
}*/

