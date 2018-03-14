package cn.strong.leke.monitor.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.dao.mybatis.IWorkStatisticsDao;
import cn.strong.leke.monitor.core.model.WorkStatistics;
import cn.strong.leke.monitor.core.service.IWorkStatisticsService;

/**
 * @ClassName: WorkStatisticsServiceImpl
 * @Description: 作业系统新增统计实现层接口
 * @author huangkai
 * @date 2016年11月22日 下午3:12:04
 * @version V1.0
 */
@Service
public class WorkStatisticsServiceImpl implements IWorkStatisticsService
{
	@Resource
	private IWorkStatisticsDao workStatisticsDao;

	/**
	 * @Description: 获取学校对应的数量
	 * @param item
	 * @param listMap
	 * @return    参数
	 * @throws
	 */
	private Integer getNum(WorkStatistics item, List<Map<String, Object>> listMap)
	{
		Integer result = 0;
		for (Map<String, Object> entry : listMap)
		{
			if (item.getSchoolId().equals(Long.valueOf(entry.get("schoolId").toString()))
					&& item.getSchoolStageId().equals(Long.valueOf(entry.get("schoolStageId").toString()))
					&& entry.get("num") != null)
			{
				return Integer.valueOf(entry.get("num").toString());
			}
		}

		return result;
	}

	/**
	 * @Description: 查询学校及所属市场
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	public List<WorkStatistics> querySchoolMarket(WorkStatistics workStatistics, Page page)
	{
		List<WorkStatistics> listWorkStatistics = new ArrayList<WorkStatistics>();

		if (!StringUtils.isEmpty(workStatistics.getType()) &&workStatistics.getType().equals("1"))
		{
			listWorkStatistics = workStatisticsDao.queryLeaderMarket(workStatistics, page);
		} else
		{
			listWorkStatistics = workStatisticsDao.querySchoolMarket(workStatistics, page);
		}

		if (listWorkStatistics == null || listWorkStatistics.size() <= 0)
		{
			return Collections.emptyList();
		}

		// 学校ID
		List<Long> schoolId = ListUtils.map(listWorkStatistics, e ->
		{
			return e.getSchoolId();
		});

		// 学段ID
		List<Long> schoolStageId = ListUtils.map(listWorkStatistics, e ->
		{
			return e.getSchoolStageId();
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolId);
		map.put("schoolStageId", schoolStageId);
		map.put("statisticsTimeStart", workStatistics.getStatisticsTimeStart());
		map.put("statisticsTimeEnd", workStatistics.getStatisticsTimeEnd());

		// 新增学生数
		List<Map<String, Object>> listStudentNum = workStatisticsDao.getStudentNum(map);
		// 新增用户数
		List<Map<String,Object>> allUserNum = workStatisticsDao.getAllUserNum(map);
		// 新增老师数
		List<Map<String,Object>> teacherNum = workStatisticsDao.getTeacherNum(map);
		// 新增家长数
		List<Map<String,Object>> parentNum = workStatisticsDao.getParentNum(map);
		
		// 新增购课人数
		List<Map<String, Object>> listBuyNum = workStatisticsDao.getBuyNum(map);

		// 作业系统新增学生数
		List<Map<String, Object>> WorkStudentNum = workStatisticsDao.getWorkStudentNum(map);

		for (WorkStatistics item : listWorkStatistics)
		{
			item.setStudentNum(getNum(item, listStudentNum));
			item.setBuyNum(getNum(item, listBuyNum));
			item.setWorkStudentNum(getNum(item, WorkStudentNum));
			item.setTeacherNum(getNum(item, teacherNum));
			item.setAllUserNum(getNum(item, allUserNum));
			item.setParentNum(getNum(item, parentNum));
		}
		return listWorkStatistics;
	}

	/**
	 * @Description: 查询新增学生明细
	 * @param workStatistics（作业统计）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	public List<WorkStatistics> getNewStudent(WorkStatistics workStatistics, Page page)
	{
		return workStatisticsDao.getNewStudent(workStatistics, page);
	}

	/**
	 * @Description: 查询作业系统新增明细
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	public List<WorkStatistics> getWorkNewStudent(WorkStatistics workStatistics, Page page)
	{
		return workStatisticsDao.getWorkNewStudent(workStatistics, page);
	}
}
