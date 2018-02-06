package com.teethen.sdk.xpermission.source;

import android.content.Context;
import android.content.Intent;

/**
 * <p>Context Wrapper.</p>
 * Created by xingq on 2017/5/1.
 */
public class ContextSource extends Source {

    private Context mContext;

    public ContextSource(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }
}
