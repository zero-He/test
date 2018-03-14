package cn.strong.leke.monitor.core.service.activeuser.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.strong.leke.monitor.core.service.activeuser.IPlatformActiveUserService;
import cn.strong.leke.monitor.mongo.course.model.query.PlatformActiveStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;
import cn.strong.leke.monitor.mongo.online.service.IPlatformActiveUserDao;

@Service
public class PlatformActiveUserServiceImpl implements IPlatformActiveUserService{
	
	@Resource
	private  IPlatformActiveUserDao iPlatformActiveUserDao;
	
	@Override
	
	public List<PlatformActiveStat> getPlatformActiveUserInfo(ActiveUserQuery query) {
		List<PlatformActiveStat> platformActiveStats = aggregatePlatformActiveUserInfo(query);
		return platformActiveStats;
		
	}
	
	private List<PlatformActiveStat> aggregatePlatformActiveUserInfo(ActiveUserQuery query) {
		List<PlatformActiveStat> result = new ArrayList<>();
		switch (query.getCycle()) {
			case "日":
				List<PlatformActiveStat> activeUser = iPlatformActiveUserDao
				.getDateActiveUser(query,parseTime(query.getEndDate()), parseTime(query.getFromDate()));
				for (PlatformActiveStat platformActiveStat : activeUser) {
					int allValidUser = platformActiveStat.getAllValidUser();
					int activeUsersCount = platformActiveStat.getActiveUsers().size();
					String activePro = percent(activeUsersCount, allValidUser);
					platformActiveStat.setAllValidUser(allValidUser);
					platformActiveStat.setActiveUsersCount(activeUsersCount);
					platformActiveStat.setActivePro(activePro);
					result.add(platformActiveStat);
				}
				break;
				
			case "周":
				List<PlatformActiveStat> weekActiveUser = iPlatformActiveUserDao
				.getWeekActiveUser(query, parseTime(query.getStartWeek()), parseTime(query.getEndWeek()));
				for (PlatformActiveStat platformActiveStat : weekActiveUser) {
					int allValidUser = platformActiveStat.getAllValidUser();
					int activeUsersCount = platformActiveStat.getActiveUsers().size();
					String activePro = percent(activeUsersCount, allValidUser);
					platformActiveStat.setAllValidUser(allValidUser);
					platformActiveStat.setActiveUsersCount(activeUsersCount);
					platformActiveStat.setActivePro(activePro);
					result.add(platformActiveStat);
				}
				break;
				
			case "月":
				List<PlatformActiveStat> monthActiveUser = iPlatformActiveUserDao
				.getMonthActiveUser(query, parseTime(query.getStartMonth()), parseTime(query.getEndMonth()));
				for (PlatformActiveStat platformActiveStat : monthActiveUser) {
					int allValidUser = platformActiveStat.getAllValidUser();
					int activeUsersCount = platformActiveStat.getActiveUsers().size();
					String activePro = percent(activeUsersCount, allValidUser);
					platformActiveStat.setAllValidUser(allValidUser);
					platformActiveStat.setActiveUsersCount(activeUsersCount);
					platformActiveStat.setActivePro(activePro);
					result.add(platformActiveStat);
				}
				break;
		}
		
		return result;
	}
	/**
	 * 解析时间
	 * 结果：2017-09-13变成20170913
	 * @param str
	 * @return
	 */
	public static int parseTime(String str){
		String[] split = str.split("-");
		String string = "";
		for (int i = 0; i < split.length; i++) {
			string += split[i];
		}
		return Integer.valueOf(string);
	}
	
	/**
	 * 求a.b的百分比
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String percent(int a, int b) {
		String probability = "";
		if (b != 0) {
			// 创建一个数值格式化对象
			NumberFormat numberFormat = NumberFormat.getInstance();
			// 设置精确到小数点后2位
			numberFormat.setMaximumFractionDigits(2);
			probability = numberFormat.format((float) a / (float) b * 100) + "%";
		}else {
			probability = "0%";
		}
		return probability;
	}
	
	
	public static ActiveUserQuery setParam(ActiveUserQuery query){
		Integer ts = query.getTs();
		switch (query.getCycle()) {
		case "日":
			query.setDay(ts);
			break;
		case "周":
			query.setWeek(ts);
			break;
		case "月":
			query.setMonth(ts);
			break;
		}
		return query;
	}
	

}
