package com.teethen.sdk.xmediapicker.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import com.teethen.sdk.R;

public class Utils {

    //public static String FILE_PROVIDER = "com.teethen.sdk.media.fileProvider"; //注意：要与com.teethen.sdk.syncombat清单文件中的保持一致(包括大小写)
    public static String FILE_PROVIDER = "com.teethen.sdk.fileProvider"; //注意：要与com.teethen.sdk.syncombat清单文件中的保持一致(包括大小写)

    public static void getFileProvider(Activity activity) {
        FILE_PROVIDER = activity.getApplication().getPackageName() + ".fileProvider";
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static int getActionbarHeight(Activity activity) {
        int attr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            attr = android.R.attr.actionBarSize;
        } else {
            attr = R.attr.actionBarSize;
        }
        final TypedArray styledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{attr});
        int actionbarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionbarSize;
    }

    /**
     * Create temp file. If has external storage create in external else create
     * in internal.
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context) throws IOException {
        if (!hasExternalStorage()) {
            return createTempFile(context, context.getCacheDir());
        } else {
            return createTempFile(context, context.getExternalFilesDir("caches"));
        }
    }

    /**
     * Check external exist or not.
     *
     * @return
     */
    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Create temp file in folder
     *
     * @param context
     * @param folder  where place temp file
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context, File folder) throws IOException {
        String prefix = String.valueOf(System.currentTimeMillis());
        return File.createTempFile(prefix, null, folder);
    }
}
