package com.teethen.sdk.encryption;

import android.util.Log;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xutil.Base64Util;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xingq on 2017/11/28.
 * AES工具类，密钥必须是16位字符串
 */
public class AESUtil {

	/*必须与后台约定好*/
    private static String STR_IV = "/p19^tY4&{-`2Qx$";  //iv must 16 bits.
    private static String DFT_K1 = "$Xi2q<;s?86|i}G^";  //encrypt data to api with the k1.
    private static String DFT_K2 = "d^J5/q|Os[2n+Ig,";  //decrypt data from api with the k2.

    public static String encrypt(String content, int... iv_key) {
        String s = null;

        if (content != null) {
            try {
                byte[] byteContent = content.getBytes(XConstant.CHARSET_UTF8);
                //注意:为了能与IOS统一,这里的key不可以使用KeyGenerator,SecureRandom,SecretKey生成
                String ivk = DFT_K1;
                if (iv_key != null && iv_key.length == 1 && iv_key[0] == 2) {
                    ivk = DFT_K2;
                }
                SecretKeySpec secretKeySpec = new SecretKeySpec(ivk.getBytes(), "AES");
                byte[] ivParam = STR_IV.getBytes();
                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParam);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//指定加密的算法,工作模式和填充方式
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
                byte[] encryptedBytes = cipher.doFinal(byteContent);
                s = Base64Util.encode(encryptedBytes);//同样对加密后数据进行base64编码
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return s;
    }

    public static String decrypt(String content, int... iv_key) {
        String s = null;

        if (content != null) {
            try {
                //确定key
                String ivk = DFT_K1;
                if (iv_key != null && iv_key.length == 1 && iv_key[0] == 2) {
                    ivk = DFT_K2;
                }
                SecretKeySpec secretKey = new SecretKeySpec(ivk.getBytes(), "AES");
                byte[] ivParam = STR_IV.getBytes();
                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParam);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                byte[] encryptedBytes = Base64Util.decode(content); // base64 解码
                byte[] result = cipher.doFinal(encryptedBytes);
                s = new String(result, XConstant.CHARSET_UTF8);
            } catch (Exception e) {
                Log.e("AESUtil:", e.toString());
            }
        }

        return s;
    }

    /**
     * 产生随机密钥(这里产生密钥必须是16位)
     */
    public static String generateKey() {
        String key = UUID.randomUUID().toString();
        key = key.replace("-", "").substring(6, 21);// uuid共32位,替换掉-号
        return key;
    }
}
