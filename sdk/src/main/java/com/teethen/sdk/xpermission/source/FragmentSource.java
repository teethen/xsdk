package com.teethen.sdk.xpermission.source;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

/**
 * <p>android.app.Fragment Wrapper.</p>
 * Created by xingq on 2017/5/1.
 */
public class FragmentSource extends Source {

    private Fragment mFragment;

    public FragmentSource(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public Context getContext() {
        return mFragment.getActivity();
    }

    @Override
    public void startActivity(Intent intent) {
        mFragment.startActivity(intent);
    }
}
