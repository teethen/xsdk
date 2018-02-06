package com.teethen.sdk.xpermission.checker;

import android.database.Cursor;

/**
 * Created by xingq on 2018/1/14.
 */
interface PermissionTest {

    boolean test() throws Throwable;

    class CursorTest {
        public static void read(Cursor cursor) {
            int count = cursor.getCount();
            if (count > 0) {
                int type = cursor.getType(0);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                    case Cursor.FIELD_TYPE_NULL: {
                        break;
                    }
                    case Cursor.FIELD_TYPE_INTEGER:
                    case Cursor.FIELD_TYPE_FLOAT:
                    case Cursor.FIELD_TYPE_STRING:
                    default: {
                        cursor.getString(0);
                        break;
                    }
                }
            }
        }
    }
}