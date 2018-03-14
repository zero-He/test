package cn.strong.leke.diag.util;

import java.util.Calendar;
import java.util.Date;

import cn.strong.leke.common.utils.DateUtils;

/**
 * 提供公共方法
 * @author Zhang Fujun
 * @date 2015年10月28日
 */
public class DiagHelp {

	/**
	 * 获取新学期开始日期 每年的 7.1号
	 * 如果是当前日期大于该日期，返回当年的7.1号，否则返回去年的7.1号
	 * @return
	 */
	public static Date getSemesterStarDate() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		if (month < 6) {
			c.set(year - 1, 6, 1);
		} else {
			c.set(year, 6, 1);
		}
		return DateUtils.parseDate(DateUtils.formatDate(c.getTime()));
	}
}
