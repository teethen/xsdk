package com.teethen.sdk.xpermission.checker;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xingq on 2018/1/7.
 */
public final class StandardChecker implements PermissionChecker {

    public StandardChecker() {
    }

    @Override
    public boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        return hasPermission(context, Arrays.asList(permissions));
    }

    @Override
    public boolean hasPermission(@NonNull Context context, @NonNull List<String> permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        for (String permission : permissions) {
            int result = context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            String op = AppOpsManager.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) {
                return true;
            }

            AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
            result = appOpsManager.noteProxyOp(op, context.getPackageName());
            if (result != AppOpsManager.MODE_ALLOWED) {
                return false;
            }

        }
        return true;
    }
}