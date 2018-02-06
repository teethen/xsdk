package com.teethen.sdk.xpermission.checker;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.RequiresPermission;

import com.teethen.sdk.xpermission.Permission;

/**
 * Created by xingq on 2018/1/14.
 */
class CallLogWriteTest implements PermissionTest {

    private ContentResolver mResolver;

    CallLogWriteTest(Context context) {
        this.mResolver = context.getContentResolver();
    }

    @RequiresPermission(Permission.WRITE_CALL_LOG)
    @Override
    public boolean test() throws Throwable {
        try {
            ContentValues content = new ContentValues();
            content.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
            content.put(CallLog.Calls.NUMBER, "1");
            content.put(CallLog.Calls.DATE, 20080808);
            content.put(CallLog.Calls.NEW, "0");
            Uri resourceUri = mResolver.insert(CallLog.Calls.CONTENT_URI, content);
            return ContentUris.parseId(resourceUri) > 0;
        } finally {
            mResolver.delete(CallLog.Calls.CONTENT_URI, CallLog.Calls.NUMBER + "=?", new String[]{"1"});
        }
    }
}