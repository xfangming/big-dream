package org.dream.toolkit.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tobiasy
 */
public class DateUtils {
    public static String defaultPattern = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        if (pattern == null) {
            return getSimpleDateFormat();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern);
        return simpleDateFormat;
    }

    public static String getDateFormat(Date date) {
        return getSimpleDateFormat().format(date);
    }

    public static String getDateFormat(Date date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }

    /**
     * 将时间戳转化为时间字符串
     *
     * @param lt
     * @param pattern
     * @return
     */
    public static String stampToDateString(Long lt, String pattern) {
        String res;
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern == null ? defaultPattern : pattern);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Date stampToDate(String s) {
        Long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }

    public static Date stampToDate(Long lt) {
        Date date = new Date(lt);
        return date;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s
     * @return
     */
    public static String dateToStamp(String s, String pattern) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern == null ? defaultPattern : pattern);
        try {
            Date date = simpleDateFormat.parse(s);
            return dateToStamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToStamp(Date date) {
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 根据时分秒创建时间对象
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return
     */
    public static Date getDate(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date time = calendar.getTime();
        return time;
    }

    public static Date getDateLike(String text){
        return getDateWithPattern(text, defaultPattern);
    }

    public static Date getDateWithPattern(String text, String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
