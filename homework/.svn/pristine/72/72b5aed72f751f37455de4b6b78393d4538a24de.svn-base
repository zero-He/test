package cn.strong.leke.homework.model;

import cn.strong.leke.common.utils.DateUtils;

import java.util.Date;

/**
 * @Author LIU.SHITING
 * @Version 2.6
 * @Date 2017-05-04 11:14:14
 */
public class DateDistance {

	/**
	 * 两个时间之间相差距离多少天
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDays(String str1, String str2) throws Exception {
		long time1 = DateUtils.parseDate(str1, DateUtils.SHORT_DATE_PATTERN).getTime(), time2 = DateUtils.parseDate(str2, DateUtils.SHORT_DATE_PATTERN).getTime();
		long diff = Math.abs(time1 - time2);
		return diff / (1000 * 60 * 60 * 24);
	}

	/**
	 * pc端学生提交时间处理
	 * @return java.lang.String
	 * @Author LIU.SHITING
	 * @Version 2.6
	 * @Date 2017/5/4 11:45
	 * @Param [one, two]
	 * one:提交时间
	 * two:当前系统时间
	 */
	public static String getDistanceTimes(Date one, Date two) {
		long diff = Math.abs(one.getTime() - two.getTime()) / 1000;
		long sec = diff, min = sec / 60, hour = min / 60, day = hour / 24;

		if (day == 0 && hour == 0 && min == 0) {
			return sec + "秒之前";
		} else if (day == 0 && hour == 0) {
			return min + "分钟之前";
		} else if (day == 0) {
			return hour + "小时之前";
		} else {
			if (day < 7) {
				return day + "天之前";
			}
			return DateUtils.formatTime(one);
		}

	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTimes(String str1, String str2) {
		long time1 = DateUtils.parseDate(str1, DateUtils.SHORT_DATE_PATTERN).getTime(), time2 = DateUtils.parseDate(str2, DateUtils.SHORT_DATE_PATTERN).getTime();
		long diff = Math.abs(time1 - time2) / 1000;
		long sec = diff, min = sec / 60, hour = min / 60, day = hour / 24;
		long[] times = {day, hour, min, sec};
		return times;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		long time1 = DateUtils.parseDate(str1, DateUtils.LONG_DATE_PATTERN).getTime(), time2 = DateUtils.parseDate(str2, DateUtils.LONG_DATE_PATTERN).getTime();
		long diff = Math.abs(time1 - time2) / 1000;
		long sec = diff, min = sec / 60, hour = min / 60, day = hour / 24;
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}
}
