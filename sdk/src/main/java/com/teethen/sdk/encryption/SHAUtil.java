package com.teethen.sdk.encryption;

import android.text.TextUtils;
import android.util.Log;

import com.teethen.sdk.base.XConstant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xingq on 2018/1/26.
 * SHA 加密工具(安全性上略胜于MD5，但是速度上不如MD5)
 */

public class SHAUtil {
    private static final String TAG = "SHAUtil";

    private static String SHA_VER = "SHA-256";
    private static String SHA_256 = "SHA-256";
    private static String SHA_512 = "SHA-512";

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param str
     * @return
     */
    public static String SHA256(String str) {
        return SHA(str, SHA_256);
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param str
     * @return
     */
    public static String SHA512(String str) {
        return SHA(str, SHA_512);
    }


    /**
     * SHA 加密
     * @param src source string
     * @param ver sha version (SHA-256, ...)
     * @return
     */
    public static String SHA(String src, String... ver) {
        String result = "";

        if (!TextUtils.isEmpty(src)) {
            try {
                if (ver == null || ver.length == 0) {
                    SHA_VER = SHA_256;
                } else {
                    SHA_VER = ver[0];
                }

                MessageDigest md = MessageDigest.getInstance(SHA_VER);
                byte[] bytes = md.digest(src.getBytes(XConstant.CHARSET_UTF8));

                if (bytes != null && bytes.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : bytes) {
                        String hex = Integer.toHexString(b & 0xff);
                        if (hex.length() == 1) {
                            sb.append("0");
                        }
                        sb.append(hex);
                    }

                    result = sb.toString();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }

        return result;
    }

    /**
     * SHA 加密
     * @param input source byte[]
     * @param version sha version (SHA-256, ...)
     * @return
     */
    public static byte[] SHA(byte[] input, String... version) {

        byte[] output = null;

        if (input != null && input.length > 0) {
            if (version == null || version.length == 0) {
                SHA_VER = SHA_256;
            } else {
                SHA_VER = version[0];
            }

            try {
                MessageDigest md = MessageDigest.getInstance(SHA_VER);
                md.update(input);
                output = md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }

        return output;
    }

}
