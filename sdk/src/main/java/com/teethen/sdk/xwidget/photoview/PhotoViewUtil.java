package com.teethen.sdk.xwidget.photoview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.teethen.sdk.base.XConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingq on 2018/2/1.
 */

public class PhotoViewUtil {

    private static final String TAG = PhotoViewUtil.class.getSimpleName();

    public static void startViewWithGlide(Context context, String url) {
        try {
            Intent intent = new Intent(context, PhotoViewGlideActivity.class);
            intent.putExtra(PhotoViewGlideActivity.TAG_IMG_URL, url);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    public static void startViewPagerWithId(Context context, ArrayList<Integer> idList) {
        try {
            Intent intent = new Intent(context, PhotoViewPagerActivity.class);
            intent.putExtra(PhotoViewPagerActivity.TAG_IMG_RES_TYPE, XConstant.IMG_RES_TYPE_ID);
            intent.putIntegerArrayListExtra(PhotoViewPagerActivity.TAG_IMG_RES_DATA, idList);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void startViewPagerWithUrl(Context context, List<String> urlList) {
        try {
            Intent intent = new Intent(context, PhotoViewPagerActivity.class);
            intent.putExtra(PhotoViewPagerActivity.TAG_IMG_RES_TYPE, XConstant.IMG_RES_TYPE_URL);
            intent.putExtra(PhotoViewPagerActivity.TAG_IMG_RES_DATA, (Serializable) urlList);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void startViewPagerWithUri(Context context, List<Uri> uriList) {
        try {
            Intent intent = new Intent(context, PhotoViewPagerActivity.class);
            intent.putExtra(PhotoViewPagerActivity.TAG_IMG_RES_TYPE, XConstant.IMG_RES_TYPE_URI);
            intent.putExtra(PhotoViewPagerActivity.TAG_IMG_RES_DATA, (Serializable) uriList);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

}
