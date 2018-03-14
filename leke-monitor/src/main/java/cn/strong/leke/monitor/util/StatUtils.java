/**
 * 
 */
package cn.strong.leke.monitor.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;


/**
 * 统计工具类
 * 
 * @author liulongbiao
 *
 */
public class StatUtils {

	public static final int ONLINE_MIN = 15;
	public static final long TS_MIN_5 = 300000L;
	private static final int MUL_YEAR = 10000; // 年份乘数因子
	private static final int MUL_MONTH = 100; // 月份乘数因子
	public static final String SHORT_DATE_PATTERN = "yyyyMMdd";

	/**
	 * 获取日期的整型表示
	 * 
	 * @param date
	 * @return
	 */
	public static final int ofDay(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("arg day must not be null.");
		}
		LocalDate ld = new LocalDate(date);
		return ld.getYear() * MUL_YEAR + ld.getMonthOfYear() * MUL_MONTH + ld.getDayOfMonth();
	}

	/**
	 * 将 yyyyMMdd 格式的日期转换成 LocalDate
	 * 
	 * @param day
	 * @return
	 */
	public static final LocalDate dayToLocalDate(int day) {
		int y = day / MUL_YEAR;
		int md = day % MUL_YEAR;
		int m = md / MUL_MONTH;
		int d = md % MUL_MONTH;
		return new LocalDate(y, m, d);
	}

	/**
	 * 获取当前在线时间(15分钟前)
	 * 
	 * @return
	 */
	public static Date getCurrentOnlineTs() {
		return LocalDateTime.now().minusMinutes(ONLINE_MIN).toDate();
	}

	/**
	 * 获取指定时间的快照时间戳(5分钟起始点)
	 * 
	 * @param in
	 * @return
	 */
	public static Date getSnapshotTs(Date in) {
		long time = in.getTime();
		return new Date(time - time % TS_MIN_5);
	}

	/**
	 * 获取当前时间的快照时间戳(5分钟起始点)
	 * 
	 * @return
	 */
	public static Date getSnapshotTs() {
		long time = System.currentTimeMillis();
		return new Date(time - time % TS_MIN_5);
	}
	
	/**
	 * 获取时间在第几周
	 * @param date
	 * @return
	 */
	public static String getWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        String week = weekOfYear < 10 ? "0"+weekOfYear :String.valueOf(weekOfYear);
        return week;
	}
	
	/**
	 * 获取时间在第几月
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthOfYear = calendar.get(Calendar.MONTH)+1;
        String month = "";
        if (monthOfYear < 10) {
			month = "0" + monthOfYear;
		}else {
			month = String.valueOf(monthOfYear);
		}
        return month;
	}
	/**
	 * 获取年
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
	}
	
	/**
	 * 获取某年某周的开始和结束时间
	 * @param year
	 * @param weekindex
	 * @return
	 */
    public static String[] getDayOfWeek(int year, int weekindex) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, weekindex);

        c.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
        c.add(Calendar.DATE, -dayOfWeek); // 得到本周的第一天
        String begin = sdf.format(c.getTime());
        c.add(Calendar.DATE, 6); // 得到本周的最后一天
        String end = sdf.format(c.getTime());
        String[] range = new String[2];
        range[0] = begin;
        range[1] = end;
        return range;
    }
    
	public static String formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SHORT_DATE_PATTERN);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取某周的结束时间
	 * @param week
	 * @return
	 */
	public static String getEndDayOfWeek(String week){
		String[] split = week.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(split[0]));
        c.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(split[1]));

        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.add(Calendar.DATE, 5); 
        String end = sdf.format(c.getTime());
        return end;
	}
	
	/**
	 * 获取某月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
    public static String getLastDayOfMonth(String month) {
    	String[] split = month.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(split[0]));
        cal.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
    }
    
    /**
     * 获取开始结束日期间的日期集合
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(beginDate);// 把开始时间加入集合  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        if (beginDate.getTime() != endDate.getTime()) {
        	lDate.add(endDate);// 把结束时间加入集合  
		}
        return lDate;  
    }

}
