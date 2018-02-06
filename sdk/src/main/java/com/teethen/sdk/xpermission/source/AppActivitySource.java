package com.teethen.sdk.xpermission.source;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * <p>Activity Wrapper.</p>
 * Created by xingq on 2017/5/1.
 */
public class AppActivitySource extends Source {

    private Activity mActivity;

    public AppActivitySource(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }
}
