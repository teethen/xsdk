package com.teethen.sdk.xhttp.nohttp.cache;

import android.content.Context;

import com.teethen.sdk.xhttp.nohttp.tools.CacheStore;
import com.teethen.sdk.xhttp.nohttp.tools.Encryption;

/**
 * Created by xingq on 2017/12/21.
 */
public abstract class BasicCacheStore implements CacheStore<CacheEntity> {

    private Context mContext;

    public BasicCacheStore(Context context) {
        mContext = context;
    }

    protected String uniqueKey(String key) {
        key += mContext.getApplicationInfo().packageName;
        return Encryption.getMD5ForString(key);
    }

}