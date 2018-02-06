package com.teethen.sdk.xpermission.checker;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.RequiresPermission;

import com.teethen.sdk.xpermission.Permission;

/**
 * Created by xingq on 2018/1/25.
 */
class CallLogReadTest implements PermissionTest {

    private ContentResolver mResolver;

    CallLogReadTest(Context context) {
        mResolver = context.getContentResolver();
    }

    @RequiresPermission(Permission.READ_CALL_LOG)
    @Override
    public boolean test() throws Throwable {
        String[] projection = new String[]{CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE};
        Cursor cursor = mResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            try {
                CursorTest.read(cursor);
            } finally {
                cursor.close();
            }
            return true;
        } else {
            return false;
        }
    }
}