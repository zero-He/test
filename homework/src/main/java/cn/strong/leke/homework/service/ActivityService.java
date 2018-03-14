package cn.strong.leke.homework.service;

import java.util.List;
import java.util.Map;

import cn.strong.leke.homework.model.activities.UserActivitiesRecord;
import cn.strong.leke.homework.model.activity.BugFixHwInfo;
import cn.strong.leke.homework.model.activity.MonthHwInfo;
import cn.strong.leke.homework.model.activity.SimpleExercise;

/**
 * 活动服务接口
 * @author Zhang Fujun
 * @date 2017年9月21日
 */
public interface ActivityService {

	/**
	 * 获取学生指定月份的需订正数和已订正数
	 * @param userId
	 * @param month 0-11
	 * @return
	 */
	BugFixHwInfo getBugFixInfo(Long userId,Integer month);
	
	/**获取学生某个月份的月错题集
	 * @param userId
	 * @param month 0-11
	 * @return
	 */
	List<MonthHwInfo> findMonthHw(Long userId, Integer month);
	
	/**
	 * 获取学生当天的自主练习列表（仅限已提交的）
	 * @param userId
	 * @return
	 */
	List<SimpleExercise> findTodayExercis(Long userId);
	
	UserActivitiesRecord nextMission(Long userId);
	
	UserActivitiesRecord getUserActivitiesRecord(Long userId);
	
	Integer acceptPrize(Long userId,String userName,Long schoolId);
	
	void addAccessRecord(Long userId,String userName);
	
	List<Map<String,Object>> findAllStatistics();
}
