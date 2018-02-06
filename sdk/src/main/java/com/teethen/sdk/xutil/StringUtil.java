package com.teethen.sdk.xutil;

/**
 * Created by xingq on 2017/12/4.
 */

public class StringUtil {

    /**
     * String左侧补齐
     * @param src 源字符串
     * @param len 目标长度
     * @param c 补位字符
     */
    public static String padLeft(String src, int len, char c) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] chars = new char[len];
        System.arraycopy(src.toCharArray(), 0, chars, diff, src.length());
        for (int i = 0; i < diff; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    /**
     * String右侧补齐
     * @param src 源字符串
     * @param len 目标长度
     * @param c 补位字符
     */
    public static String padRight(String src, int len, char c) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] chars = new char[len];
        System.arraycopy(src.toCharArray(), 0, chars, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

}
