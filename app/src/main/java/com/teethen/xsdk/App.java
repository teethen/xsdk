package com.teethen.xsdk;

import android.app.Application;
import android.content.Context;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xutil.SharedPreferencesUtil;

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
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static SharedPreferencesUtil getSp() {
        return sp;
    }
}
