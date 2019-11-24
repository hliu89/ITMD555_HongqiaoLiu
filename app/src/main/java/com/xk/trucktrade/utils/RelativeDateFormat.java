package com.xk.trucktrade.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取相对时间
 * Copy from http://my.oschina.net/u/1177694/blog/498218
 */
public class RelativeDateFormat {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;

    private static final String ONE_SECOND_AGO = "";
    private static final String ONE_MINUTE_AGO = "";
    private static final String ONE_HOUR_AGO = "";

    /**
     * 返回相对时间字符串， 如果超过两天，就返回用 format 格式化的字符串
     *
     * @param date   要转换的日期
     * @param format 超过两天使用的转换方式
     * @return 日期字符串
     */
    public static String format(Date date, SimpleDateFormat format) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "Yesterday";
        }
        if (delta < 72L * ONE_HOUR) {
            return "The day before yesterday";
        }

        return format.format(date);

    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }
}
