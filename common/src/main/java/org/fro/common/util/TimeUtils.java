package org.fro.common.util;

import android.content.Context;
import android.text.TextUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.fro.common.R;

/**
 * desc : 各种时间工具
 */
public class TimeUtils {

    /**
     * 通过 1+2+3+4+5+6+7 计算循环周期
     *
     * @param ctx
     * @param timeDay 字符串对象 1+2+3+4+5+6+7
     * @return 工作日、每天、星期一，星期二
     */
    public static String getCycle(Context ctx, String timeDay) {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(timeDay)) {
            if (timeDay.contains("1") && timeDay.contains("2") && timeDay.contains("3") && timeDay.contains("4") && timeDay.contains("5") && !timeDay.contains("6") && !timeDay.contains("7")) {
                sb.append(ctx.getString(R.string.cd_week_work));
            } else if (!timeDay.contains("1") && !timeDay.contains("2") && !timeDay.contains("3") && !timeDay.contains("4") && !timeDay.contains("5") && timeDay.contains("6") && timeDay.contains("7")) {
                sb.append(ctx.getString(R.string.cd_week_weekend));
            } else if (timeDay.contains("1") && timeDay.contains("2") && timeDay.contains("3") && timeDay.contains("4") && timeDay.contains("5") && timeDay.contains("6") && timeDay.contains("7")) {
                sb.append(ctx.getString(R.string.cd_week_every));
            } else {
                if (timeDay.contains("1")) {
                    sb.append(ctx.getString(R.string.cd_week1));
                    sb.append(" ");
                }
                if (timeDay.contains("2")) {
                    sb.append(ctx.getString(R.string.cd_week2));
                    sb.append(" ");
                }
                if (timeDay.contains("3")) {
                    sb.append(ctx.getString(R.string.cd_week3));
                    sb.append(" ");
                }
                if (timeDay.contains("4")) {
                    sb.append(ctx.getString(R.string.cd_week4));
                    sb.append(" ");
                }
                if (timeDay.contains("5")) {
                    sb.append(ctx.getString(R.string.cd_week5));
                    sb.append(" ");
                }
                if (timeDay.contains("6")) {
                    sb.append(ctx.getString(R.string.cd_week6));
                    sb.append(" ");
                }
                if (timeDay.contains("7")) {
                    sb.append(ctx.getString(R.string.cd_week7));
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return 1->01, 14->14
     */
    public static String format2Double(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.length() == 1) {
                return "0".concat(time);
            } else {
                return time;
            }
        } else {
            return "";
        }
    }

    /**
     * @param startTime 开始时间 (12:35)
     * @param endTime   结束时间 (16:11)
     * @return 返回当前时间 是否包含在这两个时间中，true包含，false不包含
     */
    public static boolean isContainTime(String startTime, String endTime) {

        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || startTime.equals(endTime)) {
            return false;
        }

        String TIME_SEPARATOR = ":";
        String startH = startTime.split(TIME_SEPARATOR)[0];
        String startM = startTime.split(TIME_SEPARATOR)[1];
        String endH = endTime.split(TIME_SEPARATOR)[0];
        String endM = endTime.split(TIME_SEPARATOR)[1];

        int startI = Integer.valueOf(startH) * 60 + Integer.valueOf(startM);
        int endI = Integer.valueOf(endH) * 60 + Integer.valueOf(endM);

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int nowI = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);

        if (endI < startI) {
            if (startI <= nowI && 1439 >= nowI) {
                return true;
            } else if (0 <= nowI && endI >= nowI) {
                return true;
            }
        } else if (startI <= nowI && endI >= nowI) {
            return true;
        }
        return false;
    }

    /**
     * @param weeks 星期(1 2 3)
     * @return 返回是否包含当前星期 true包含，false不包含
     */
    public static boolean isContainWeek(String weeks) {
        if (TextUtils.isEmpty(weeks)) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 8;
        }
        String weekStr = String.valueOf(week - 1);
        if (weeks.contains(weekStr)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean getTimerSection(String startStr, String endStr) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String[] startTime = startStr.split(":");
        String[] endTime = endStr.split(":");
        int minuteOfDay = hour * 60 + minute;
        int start = Integer.parseInt(startTime[0]) * 60 + Integer.parseInt(startTime[1]);
        int end = Integer.parseInt(endTime[0]) * 60 + Integer.parseInt(endTime[1]);

        if (start > minuteOfDay) {
            minuteOfDay += (24 * 60);
        }
        if (start > end) {
            end += (24 * 60);
        }
        return minuteOfDay >= start && minuteOfDay <= end;
    }


    public static String parseTimeToYM(long time) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return format.format(new Date(time));
    }

    public static String parseTimeToYM2(long time) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date(time));
    }

    public static String getCurrentTime(String format) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    /**
     * 精确到分~月
     *
     * @return
     */
    public static String getCurrentTimeType2() {
        return getCurrentTime("MM月dd日HH:mm");
    }

    public static long currentMillis() {
        return System.currentTimeMillis();
    }

    public static long getMidnightMills() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getMillisOfDay() {
        return currentMillis() % 86400000;
    }

    public static String getCurrentTimeMMSS() {
        return getCurrentTime("mm:ss");
    }

    public static String getCurrentTimeHHMM() {
        return getCurrentTime("HH:mm");
    }

    public static String getTimeYYYY_MM_DD_HH_MM_SS(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }

    public static boolean isToday(long time) {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        return (time > zero && time < twelve);
    }

    public static boolean isThisWeek(long time) {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        long sevenDay = 7 * 24 * 60 * 60 * 1000;
        long thisWeekZero = zero - sevenDay;
        return (time > thisWeekZero && time < twelve);
    }

    /**
     * 时间格式转化
     *
     * @param time 时间
     * @return yyyy/MM/dd week HH:mm:ss 或者 今天HH：MM
     */
    public static String getTimeTodayOrYYYY_MM_DD_Week_HH_MM_SS(long time) {
        return getTimeYYYY_MM_DD_Week_HH_MM_SS(time);
    }

    /**
     * 时间格式转化
     *
     * @param time 时间
     * @return yyyy/MM/dd week HH:mm:ss
     */
    public static String getTimeYYYY_MM_DD_Week_HH_MM_SS(long time) {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        long thisWeekZero = zero - 6 * 24 * 60 * 60 * 1000;//7天前0点0分0秒的毫秒数
        if (time >= zero && time <= twelve) {
            return "今天" + getTimeHHMM(time);
        }
        Date date = new Date(time);
        String timeTemp = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(date);
        if (time >= thisWeekZero && time < zero) {
            return getFullDateWeekTime(timeTemp) + timeTemp.substring(10);
        } else {
            return timeTemp;
        }
    }

    public static String getTimeHHMMSS(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date);
    }

    public static String getTimeHHMM(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
    }

    public static String getTimeMMSS(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("mm:ss", Locale.getDefault()).format(date);
    }

    public static String getCurrentDay() {

        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("dd", Locale.getDefault()).format(date);
    }

    public static String getDay(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("dd", Locale.getDefault()).format(date);
    }

    public static String getCurrentMonth() {
        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("MM", Locale.getDefault()).format(date);
    }

    public static String getMonth(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("MM月", Locale.getDefault()).format(date);
    }

    public static String getCurrentWeek() {
        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    public static String getWeek(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    /**
     * 将日期转换成星期几
     *
     * @param time
     * @return
     */
    public static String getFullDateWeekTime(String time) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = new java.util.Date();
        try {
            date = sdfInput.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return dayNames[dayOfWeek];
    }

    /**
     * 当前年份
     *
     * @return
     */
    public static String getcurrentYear() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 获取时间戳
     */
    public static String getLongTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd ");
        Date d;
        try {
            d = sdf.parse(" " + timeString + " ");
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String getLongTime1(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyyMMdd ");
        Date d;
        try {
            d = sdf.parse(" " + timeString + " ");
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }
}