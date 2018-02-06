package com.teethen.sdk.xpermission.checker;

import android.os.Environment;

import java.io.File;

/**
 * Created by xingq on 2018/1/16.
 */
class StorageReadTest implements PermissionTest {

    StorageReadTest() {
    }

    @Override
    public boolean test() throws Throwable {
        File directory = Environment.getExternalStorageDirectory();
        if (directory.exists() && directory.canRead()) {
            long modified = directory.lastModified();
            String[] pathList = directory.list();
            return modified > 0 && pathList != null;
        }
        return false;
    }
}