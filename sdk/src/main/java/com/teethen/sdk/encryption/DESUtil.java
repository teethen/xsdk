package com.teethen.sdk.encryption;

import android.text.TextUtils;
import android.util.Log;

import com.teethen.sdk.xutil.Base64Util;

import java.security.Key;
import java.util.Formatter;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xingq on 2017/12/17.
 *
 * 3DES 加解密工具类
 */

public class DESUtil {

    private static final String TAG = "DESUtil";

    //============通用加解密 - BEGIN============//
    // 编码
    private final static String charsetName = "UTF-8";
    // 密钥
    private final static String secretKey = "1DD244DE9033B96F99A6B979";
    // 向量
    private final static String iv = null;

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainText) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            if (TextUtils.isEmpty(iv)) {
                cipher.init(Cipher.ENCRYPT_MODE, desKey);
            } else {
                IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            }
            byte[] encryptData = cipher.doFinal(plainText.getBytes(charsetName));

            return Base64Util.encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return plainText;
        }
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptText) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key desKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            if (TextUtils.isEmpty(iv)) {
                cipher.init(Cipher.DECRYPT_MODE, desKey);
            } else {
                IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
            }
            byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));

            return new String(decryptData, charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptText;
        }
    }

    /**
     * 生成随机密钥
     * @param keyLength Des Key length is 24 bits
     * @return
     */
    public static String generateKey(int keyLength) {
        String key = "";

        if(keyLength <= 0 || keyLength > 32) keyLength = 24;
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        Random r = new Random();
        int begin = r.nextInt(uuid.length() - keyLength);
        key = uuid.substring(begin, keyLength + begin);

        return key;
    }

    /*public static void main(String[] args) {
        try {
            System.out.println(encode("13003221671"));
            System.out.println(decode("5uyfs4qr2bNRADXRGYxGnw=="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    //============通用加解密 - END============//


    //============自定义3DES加解密算法 - BEGIN============//
    private static byte[] key;
    static{
        try {
            key = secretKey.getBytes(charsetName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString().toUpperCase();
        formatter.close();
        return result;
    }

    private static byte[] hexToBytes(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    /**
     * 加密
     * @param plainText 要加密的对象
     * @return
     * @throws Exception
     */
    public static String encrypt3DES(String plainText) {
        String result = null;

        try {
            String algorithm = "DESede"; //加密算法
            SecretKey desKey = new SecretKeySpec(key, algorithm); //生成密钥
            Cipher c = Cipher.getInstance(algorithm); //加密
            c.init(Cipher.ENCRYPT_MODE, desKey);
            result = byteToHex(c.doFinal(plainText.getBytes(charsetName)));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        return result;
    }

    /**
     * 解密
     * @param encryptText 要解密的对象
     * @return
     * @throws Exception
     */
    public static String decrypt3DES(String encryptText) {
        String result = null;

        try {
            String algorithm = "DESede"; //解密算法
            SecretKey desKey = new SecretKeySpec(key, algorithm); //生成密钥
            Cipher c = Cipher.getInstance(algorithm); //解密
            c.init(Cipher.DECRYPT_MODE, desKey);
            //return byteToHex(c.doFinal(hexToBytes(encryptText)));

            result = new String(c.doFinal(hexToBytes(encryptText)), charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        return result;
    }

    /*public static void main(String[] args){
        try {
            String miwen = byteToHex(Des.encrypt(key, "admin".toString().getBytes("UTF-8"))).toUpperCase();
            System.out.println(miwen);
            String str = new String(decrypt(key, hexToBytes(miwen.toUpperCase())),"UTF-8");
            System.out.println(str);
        }catch (Exception e){
        }
    }*/
    //============自定义3DES加解密算法 - END============//

}
