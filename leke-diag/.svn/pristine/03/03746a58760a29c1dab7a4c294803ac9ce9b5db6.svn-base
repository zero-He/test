package cn.strong.leke.diag.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.diag.model.report.ReportCycle;

public class CycleGenMain {

	public static List<ReportCycle> build() {
		Date date1 = DateUtils.parseDate("2014-07-01");
		Date date2 = DateUtils.parseDate("2024-06-30");
		List<ReportCycle> list = new ArrayList<>();
		list.addAll(buildWeek(date1, date2));
		list.addAll(buildMonth(date1, date2));
		list.addAll(buildTerm(date1, date2));
		list.addAll(buildYear(date1, date2));
		return list;
	}

	private static List<ReportCycle> buildYear(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		List<ReportCycle> labels = new ArrayList<>();
		for (int i = 1; calendar.getTimeInMillis() < date2.getTime(); i++) {
			int year = calendar.get(Calendar.YEAR);
			String label = year + "--" + (year + 1) + "学年";
			ReportCycle cycle = new ReportCycle();
			cycle.setId(400000 + i);
			cycle.setType(4);
			cycle.setLabel(label);
			cycle.setStart(DateUtils.parseDate(year + "-07-01"));
			cycle.setEnd(maximizeTime(DateUtils.parseDate((year + 1) + "-06-30")));
			labels.add(cycle);
			calendar.add(Calendar.YEAR, 1);
		}
		return labels;
	}

	private static List<ReportCycle> buildTerm(Date date1, Date date2) {
		// 2015—2016学年上学期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		List<ReportCycle> labels = new ArrayList<>();
		for (int i = 1; calendar.getTimeInMillis() < date2.getTime(); i++) {
			int year = calendar.get(Calendar.YEAR);
			String label = year + "--" + (year + 1) + "学年";

			// 上学期
			ReportCycle cycle = new ReportCycle();
			cycle.setId(300000 + i * 2 - 1);
			cycle.setType(3);
			cycle.setLabel(label + "上学期");
			cycle.setStart(DateUtils.parseDate(year + "-07-01"));
			cycle.setEnd(maximizeTime(DateUtils.parseDate((year + 1) + "-01-31")));
			labels.add(cycle);

			ReportCycle cycle2 = new ReportCycle();
			cycle2.setId(300000 + i * 2);
			cycle2.setType(3);
			cycle2.setLabel(label + "下学期");
			cycle2.setStart(DateUtils.parseDate((year + 1) + "-02-01"));
			cycle2.setEnd(maximizeTime(DateUtils.parseDate((year + 1) + "-06-30")));
			labels.add(cycle2);
			calendar.add(Calendar.YEAR, 1);
		}
		return labels;
	}

	private static List<ReportCycle> buildMonth(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		List<ReportCycle> labels = new ArrayList<>();
		for (int i = 1; calendar.getTimeInMillis() < date2.getTime(); i++) {
			Date start = calendar.getTime();
			Date end = toMonthEnd(start);
			String label = DateUtils.format(start, "yyyy年MM月");
			ReportCycle cycle = new ReportCycle();
			cycle.setId(200000 + i);
			cycle.setType(2);
			cycle.setLabel(label);
			cycle.setStart(start);
			cycle.setEnd(end);
			labels.add(cycle);
			calendar.add(Calendar.MONTH, 1);
		}
		return labels;
	}

	private static List<ReportCycle> buildWeek(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.setFirstDayOfWeek(7);
		calendar.set(Calendar.DAY_OF_WEEK, 0);
		List<ReportCycle> labels = new ArrayList<>();
		for (int i = 1; calendar.getTimeInMillis() < date2.getTime(); i++) {
			Date start = calendar.getTime();
			Date end = toWeekEnd(start);
			String label = "第" + calendar.get(Calendar.WEEK_OF_YEAR) + "周";
			label += "（" + DateUtils.format(start, "MM.dd") + "-" + DateUtils.format(end, "MM.dd") + "）";
			ReportCycle cycle = new ReportCycle();
			cycle.setId(100000 + i);
			cycle.setType(1);
			cycle.setLabel(label);
			cycle.setStart(start);
			cycle.setEnd(end);
			labels.add(cycle);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return labels;
	}

	public static List<ReportCycle> buildDate(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.truncateDate(date1));
		List<ReportCycle> labels = new ArrayList<>();
		while (calendar.getTimeInMillis() <= date2.getTime()) {
			Date start = calendar.getTime();
			Date end = maximizeTime(start);
			ReportCycle cycle = new ReportCycle();
			cycle.setType(1);
			cycle.setLabel(DateUtils.format(start, "MM月dd日"));
			cycle.setStart(start);
			cycle.setEnd(end);
			labels.add(cycle);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return labels;
	}

	private static Date toMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		maximizeTime(calendar);
		return calendar.getTime();
	}

	private static Date toWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		maximizeTime(calendar);
		return calendar.getTime();
	}

	private static void maximizeTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

	private static Date maximizeTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
}
