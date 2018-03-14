package cn.strong.leke.monitor.core.dao.mybatis;

import java.util.List;
import java.util.Map;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.WorkStatistics;

/**
 * @ClassName: IWorkStatisticsDao
 * @Description: 作业系统新增统计数据层接口
 * @author huangkai
 * @date 2016年11月22日 下午2:25:26
 * @version V1.0
 */
public interface IWorkStatisticsDao
{
	/**
	 * @Description: 查询学校及所属市场
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	List<WorkStatistics> querySchoolMarket(WorkStatistics workStatistics, Page page);
	
	/**
	 * @Description: CRM1.1查询学校及所属市场
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	List<WorkStatistics> queryLeaderMarket(WorkStatistics workStatistics, Page page);
	
	/**
	 * @Description: 新增学生数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getStudentNum(Map<String, Object> map);
	
	/**
	 * @Description: 新增家长数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getParentNum(Map<String, Object> map);
	
	/**
	 * @Description: 新增用户数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getAllUserNum(Map<String, Object> map);
	
	/**
	 * @Description: 新增老师数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getTeacherNum(Map<String, Object> map);
	
	/**
	 * @Description: 新增购课人数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getBuyNum(Map<String, Object> map);
	
	/**
	 * @Description: 作业系统新增学生数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return List<Map<String, Object>>
	 * @throws
	 */
	List<Map<String, Object>> getWorkStudentNum(Map<String, Object> map);
	
	/**
	 * @Description: 查询新增学生明细
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	List<WorkStatistics> getNewStudent(WorkStatistics workStatistics,Page page);
	
	/**
	 * @Description: 查询作业系统新增明细
	 * @param workStatistics（作业统计）
	 * @param page（分页）
	 * @return List<WorkStatistics>
	 * @throws
	 */
	List<WorkStatistics> getWorkNewStudent(WorkStatistics workStatistics,Page page);
}
