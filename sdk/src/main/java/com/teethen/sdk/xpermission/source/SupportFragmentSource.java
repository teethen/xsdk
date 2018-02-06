package com.teethen.sdk.xpermission.source;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * <p>android.support.v4.app.Fragment Wrapper.</p>
 * Created by xingq on 2017/5/1.
 */
public class SupportFragmentSource extends Source {

    private Fragment mFragment;

    public SupportFragmentSource(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public Context getContext() {
        return mFragment.getContext();
    }

    @Override
    public void startActivity(Intent intent) {
        mFragment.startActivity(intent);
    }
}
