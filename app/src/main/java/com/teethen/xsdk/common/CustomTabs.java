package com.teethen.xsdk.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.teethen.sdk.xutil.ToastUtil;
import com.teethen.xsdk.R;

/**
 * Created by xingq on 2017/10/9.
 */

public class CustomTabs {

    public static void openUrl(Context context, String url) {
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SpKeys.KEY_CUSTOM_TABS, true)) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            builder.build().launchUrl(context, Uri.parse(url));
        } else {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
            } catch (ActivityNotFoundException e) {
                ToastUtil.showToast(context, context.getString(R.string.error_no_browser));
            }
        }
    }
}
