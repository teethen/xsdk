package com.teethen.sdk.xutil;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.LruCache;

import com.teethen.sdk.base.XConstant;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by xingq on 2017/12/3.
 */

public class DateTimeUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm";
    private static long DayMilliseconds = 24 * 60 * 60 * 1000; //1天内的毫秒数

    public static String getDateTimeStr(String datetime, String format) {
        String result = datetime;
        if (!TextUtils.isEmpty(datetime)) {
            String full = XConstant.DATETIME_FORMAT;
            try {
                if (TextUtils.isEmpty(format)) {
                    format = full;
                    format = format.substring(0, datetime.length());
                }
                SimpleDateFormat sdf = new SimpleDateFormat(full);
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                Date date = sdf.parse(datetime);
                result = formatter.format(date);
            } catch (Exception e) {
                Log.e("getDateTimeStrWth", e.toString());
            }
        }
        return result;
    }

    public static String format(long time, @Nullable String pattern) {
        pattern = TextUtils.isEmpty(pattern) ? DEFAULT_PATTERN : pattern;
        return getDateFormat(pattern).format(new Date(time));
    }

    private static Locale locale = Locale.getDefault();
    public static Locale getLocale() {
        return locale;
    }
    public static void setLocale(Locale locale) {
        DateTimeUtil.locale = locale;
    }

    private volatile static LruCache<String, DateFormat> dateFormatLruCache;
    public static DateFormat getDateFormat(String pattern) {
        if (dateFormatLruCache == null) {
            synchronized (DateTimeUtil.class) {
                if (dateFormatLruCache == null) {
                    dateFormatLruCache = new LruCache<>(4);
                }
            }
        }
        DateFormat format = dateFormatLruCache.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern, locale);
            dateFormatLruCache.put(pattern, format);
        }
        return format;
    }

    /**
     * 获取日期字符串
     * @param dtStr must be like "20180125" or "20180125003"
     * @param split 分隔符
     * @return
     */
    public static String getDateStr(String dtStr, String split) {
        String result = dtStr;
        if (dtStr != null) {
            if (dtStr.contains(split)) {
                dtStr = dtStr.replace(split, "");
            }
            if (dtStr.length() >= 8) {
                if (TextUtils.isEmpty(split)) {
                    split = "-";
                }
                result = dtStr.substring(0, 4) + split + dtStr.substring(4, 6) + split + dtStr.substring(6, 8);
            }
        }
        return result;
    }

    /**
     * 获取当前网络时间
      * @param timeZone 时区，为空则默认GMT+08
     * @return
     */
    public static String getNetworkTime(@Nullable String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(XConstant.DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TextUtils.isEmpty(timeZone) ? XConstant.TIME_ZONE : timeZone));
        return sdf.format(new Date());
    }

    /**
     * 获取 dt1 - dt2 的时间差，结果>0则 dt1>dt2;结果=0则dt1=dt2;结果<0则dt1<dt2
     * @param dt1
     * @param dt2
     */
    public static long getTimeDiff(String dt1, String dt2) {
        long diff = 0;
        DateFormat df = new SimpleDateFormat(XConstant.DATETIME_FORMAT);
        try {
            Date d1 = df.parse(dt1);
            Date d2 = df.parse(dt2);
            diff = d1.getTime() - d2.getTime();
            //long days = diff/(1000*60*60*24);
            //long hours = (diff-days*(1000*60*60*24))/(1000*60*60);
            //long minutes = (diff-days*(1000*60*60*24)-hours*(1000*60*60))/(1000*60);
        } catch (Exception e) {
        }
        return diff;
    }

    /**
     * 返回要增加的天数后的日期字符串
     * @param dtStr 日期字符串
     * @param pattern 日期格式
     * @param days 要增加的天数
     * @return String
     */
    public static String getAddedDate(String dtStr, String pattern, int days) {
        String datetime = dtStr;
        if(TextUtils.isEmpty(pattern)) pattern = XConstant.DATETIME_FORMAT;
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(dtStr);
            Calendar c = Calendar.getInstance();
            c.setTime(dt1);
            c.add(Calendar.DAY_OF_MONTH, days); // +?天

            Date dt2 = c.getTime();
            datetime = df.format(dt2);
        } catch (Exception e) {
        }
        return datetime;
    }

    public static String fromTimeStamp(long timestamp) {
        if(timestamp == 0) return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ts = new Timestamp(timestamp);
        return df.format(ts);
    }
    public static String fromTimeStamp(String timestamp, String format) {
        if(TextUtils.isEmpty(timestamp)) return null;
        SimpleDateFormat df = new SimpleDateFormat(format);
        Timestamp ts = new Timestamp(Long.parseLong(timestamp));
        return df.format(ts);
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour 开始小时，如 22
     * @param beginMin  开始小时的分钟数，如 30
     * @param endHour   结束小时，如 8
     * @param endMin    结束小时的分钟数，如 0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;

        final long aDayInMillis = DayMilliseconds;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

        if (!startTime.before(endTime)) {//跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); //startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {// 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); //startTime <= now <= endTime
        }

        return result;
    }
}
