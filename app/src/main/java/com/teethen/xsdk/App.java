package com.teethen.xsdk;

import android.app.Application;
import android.content.Context;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xdialog.dialogui.DialogUIUtils;
import com.teethen.sdk.xhttp.okgo.OkHttp;
import com.teethen.sdk.xutil.SharedPreferencesUtil;
import com.teethen.sdk.xwidget.ninegrid.complex.NineGridView;
import com.teethen.xsdk.ninegrid.GlideImageLoader;
import com.teethen.xsdk.ninegrid.PicassoImageLoader;

/**
 * Created by xingq on 2018/2/6.
 */

public class App extends Application {

    private static Context mContext;
    private static SharedPreferencesUtil sp;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        sp = SharedPreferencesUtil.getInstance(mContext, XConstant.SHARED_PREF_CFG);
        DialogUIUtils.init(mContext);
        initOkHttp();
        initImageLoader();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static SharedPreferencesUtil getSp() {
        return sp;
    }

    private void initOkHttp() {
        OkHttp.getInstance().init(this);
    }
    private void initImageLoader() {
        NineGridView.setImageLoader(new GlideImageLoader());
        //or
        //NineGridView.setImageLoader(new PicassoImageLoader());
    }
}
