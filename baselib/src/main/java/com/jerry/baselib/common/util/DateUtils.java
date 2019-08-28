package com.jerry.baselib.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期类
 *
 * @author Tina
 */
public class DateUtils {

    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String HHMMSS = "HH:mm:ss";
    private static final String YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(YYYYMMDD, Locale.CHINA);
    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(HHMMSS, Locale.CHINA);
    private static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(YYYYMMDD_HHMM, Locale.CHINA);
    private static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.CHINA);

    public static synchronized void main(String[] dfsdfs) {
        Date date = new Date();
        date.setTime(1560664101360L);
        String dsfds = FORMAT_DATE_TIME.format(date);
        System.out.println(dsfds);
    }

    /**
     *
     */
    public static synchronized long getLongByDate(String dateStr) {
        try {
            return FORMAT_DATETIME.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取日期
     */
    public static synchronized String getDateByLong(long time) {
        Date date = new Date();
        date.setTime(time);
        return FORMAT_DATE.format(date);
    }

    /**
     * 获取时分秒
     */
    public static synchronized String getTimeByLong(long time) {
        Date date = new Date();
        date.setTime(time);
        return FORMAT_TIME.format(date);
    }

    /**
     * 获取日期时分秒
     */
    public static synchronized String getDateTimeByLong(long time) {
        Date date = new Date();
        date.setTime(time);
        return FORMAT_DATETIME.format(date);
    }

    /**
     * 获取日期时分秒
     */
    public static synchronized String getDateWTimeByLong(long time) {
        Date date = new Date();
        date.setTime(time);
        return FORMAT_DATE_TIME.format(date);
    }
}
