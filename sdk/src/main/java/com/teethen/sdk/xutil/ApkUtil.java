package com.teethen.sdk.xutil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by xingq on 2018/3/12.
 */

public class ApkUtil {

    private static String deviceId = null;
    private static String apkInstallationId = null;
    private static PermissionUtil permissionUtil = null;

    /**
     * 获取应用程序安装的ID
     * @param context
     * @return
     */
    public synchronized static String getInstallationId(Context context) {
        if (TextUtils.isEmpty(apkInstallationId)) {
            try {
                File installation = new File(context.getFilesDir(), "INSTALLATION");
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }
                apkInstallationId = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return apkInstallationId;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "rw");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString().replace("-", "");
        out.write(id.getBytes());
        out.close();
    }


    public static String getCombineDeviceId() {
        if (TextUtils.isEmpty(deviceId)) {
            String combineId = String.valueOf(Build.VERSION.SDK_INT) +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HARDWARE.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;

            String serial = null;
            try {
                serial = Build.class.getField("SERIAL").get(null).toString();
            }
            catch (Exception e) {
                serial = "com.teethen.xingq";
            }

            deviceId = new UUID(combineId.hashCode(), serial.hashCode()).toString().replace("-","");
        }
        return deviceId;
    }


    public static String getDeviceId(Context context) {
        String id = null;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                id = tm.getDeviceId();
            } catch (NullPointerException e) {
            } catch (SecurityException e) {
            }
        } else {
            if (permissionUtil == null) {
                permissionUtil = new PermissionUtil(context);
            }
            permissionUtil.requestPermissions("该功能需要申请读取设备硬件状态的权限", new PermissionUtil.PermissionListener() {
                @Override
                public void doAfterGrand(String... permission) {
                    getDeviceId(context);
                }

                @Override
                public void doAfterDenied(String... permission) {
                    ToastUtil.showToast(context, "未授予本应用读取手机硬件状态的权限");
                }
            }, Manifest.permission.READ_PHONE_STATE);
        }

        return id;
    }
}
