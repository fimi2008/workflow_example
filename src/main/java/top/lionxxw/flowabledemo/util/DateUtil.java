package top.lionxxw.flowabledemo.util;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 */
public class DateUtil {

    private static final int MINUTE_VALUE = 5;

    public static Date getDate(String s) {
        return getDate(s, null);
    }

    public static Date getJustDate(String s) {
        return getDate(s, DateFormat.Y_M_D);
    }

    public static Date getDate(long date) {
        return getDate(date, null);
    }

    public static Date getJustDate(long date) {
        return getDate(date, DateFormat.Y_M_D);
    }

    public static Date getDate(long date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DateFormat.SETTLE_PATTERN;
        }

        return getDate(formatDate(new Date(date), format), format);
    }

    public static Date getDate(String s, String format) {
        Date date;
        try {
            if (StringUtils.isEmpty(format)) {
                format = DateFormat.SETTLE_PATTERN;
            }

            date = new SimpleDateFormat(format).parse(s);
        } catch (Exception e) {
            date = new Date(0L);
        }

        return date;
    }

    public static String formatDate(long date, String format) {
        return formatDate(new Date(date), format);
    }

    public static String formatDate(long date) {
        return formatDate(new Date(date), null);
    }

    public static String formatJustDate(long date) {
        return formatDate(new Date(date), DateFormat.Y_M_D);
    }
    public static String formatJustDate() {
        return formatDate(new Date(), DateFormat.Y_M_D);
    }
    public static String formatJustDate(String format) {
        return formatDate(new Date(), format);
    }

    public static String formatDate(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DateFormat.SETTLE_PATTERN;
        }

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取days(+表示后，-表示前)天的时间
     * @param days +后，-前
     * @return
     */
    public static Date beforeDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static String getTimeDesc(Date createTime) {
        Date now = new Date();
        long diff = now.getTime() - createTime.getTime();
        if (diff <= MINUTE_VALUE * DateFormat.MINUTE) {
            return "刚刚";
        }
        // 一小时以内
        if (diff <= DateFormat.HOUR) {
            return diff / DateFormat.MINUTE + "分钟前";
        }
        // 一天以内
        if (diff <= DateFormat.DAY) {
            return diff / DateFormat.HOUR + "小时前";
        }
        // 昨天
        if (diff <= 2 * DateFormat.DAY) {
            return "昨天" + DateUtil.formatDate(createTime, DateFormat.HOUR_OF_MINUTE);
        }
        // 前天
        if (diff <= 3 * DateFormat.DAY) {
            return "前天" + DateUtil.formatDate(createTime, DateFormat.HOUR_OF_MINUTE);
        }
        // 格式化yyyy-MM-dd HH:mm
        return DateUtil.formatDate(createTime, DateFormat.YMDHM);
    }
}
