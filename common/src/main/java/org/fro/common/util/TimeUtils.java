package org.fro.common.util;

import android.content.Context;
import android.text.TextUtils;


import java.util.Calendar;
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
}