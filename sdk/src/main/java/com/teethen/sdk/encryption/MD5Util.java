package com.teethen.sdk.encryption;

import com.teethen.sdk.base.XConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xingq on 2017/6/13.
 */

public class MD5Util {
    /**
     * 获取字符串的 MD5
     */
    public static String encrypt(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes(XConstant.CHARSET_UTF8));
            byte messageDigest[] = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件的 MD5
     */
    public static String encrypt(File file) {

        String md5 = null;
        FileInputStream inputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(file);

            //必须把文件读取完毕才能拿到md5
            int length = -1;
            byte[] buffer = new byte[4096];
            while ((length = inputStream.read(buffer,0,buffer.length)) != -1) {
                messageDigest.update(buffer, 0, length);
            }

            BigInteger bi = new BigInteger(1, messageDigest.digest()); //1表示得到的结果为正数
            md5 = bi.toString(16); //16表示把数值转换为16进制
        } catch (NoSuchAlgorithmException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return md5;
    }

    /**
     * 获取文件的 MD5
     */
    public static String encode(File file) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream inputStream = new FileInputStream(file);
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
            //必须把文件读取完毕才能拿到md5
            byte[] buffer = new byte[4096];
            while (digestInputStream.read(buffer) > -1) {

            }
            MessageDigest digest = digestInputStream.getMessageDigest();
            digestInputStream.close();
            byte[] md5 = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : md5) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
}
