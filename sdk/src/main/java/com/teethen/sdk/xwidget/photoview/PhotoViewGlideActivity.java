package com.teethen.sdk.xwidget.photoview;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.teethen.sdk.R;
import com.teethen.sdk.base.BaseActivity;
import com.teethen.sdk.xwidget.photoview.widget.PhotoView;

public class PhotoViewGlideActivity extends BaseActivity {

    private final String TAG = "PhotoViewGlide";
    public static String TAG_IMG_URL = "img_res_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview_glide);
        try {
            PhotoView photoView = findViewById(R.id.iv_photoview_glide);

            String url = getIntent().getStringExtra(TAG_IMG_URL);

            //1.加载本地图片
            //String sdk = FileUtil.getSDCardPath(true);
            //Uri uri = Uri.parse(sdk + "/Pictures/Screenshots/Screenshot_20180201-095358.png");
            //url = uri.toString();
            //Glide.with(this).load(url).into(photoView);

            //2.加载网络图片
            //String url = "http://himg.bdimg.com/sys/portrait/item/399d78696e6771696e677869616e676403.jpg";
            //Picasso.with(this).load(url).into(photoView);

            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.error_image)
                    .into(photoView);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
