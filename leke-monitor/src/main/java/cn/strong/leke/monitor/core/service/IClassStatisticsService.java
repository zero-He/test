package cn.strong.leke.monitor.core.service;

import java.util.List;
import java.util.Map;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.ClassStatistics;

/**
 * @ClassName: IClassStatisticsService
 * @Description: CRM课堂统计业务接口
 * @author huangkai
 * @date 2016年11月16日 上午11:38:29
 * @version V1.0
 */
public interface IClassStatisticsService
{
	/**
	 * @Description: 查询学校课堂统计
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	List<ClassStatistics> querySchoolClassStatistics(ClassStatistics classStatistics, Page page);

	/**
	 * @Description: 获取所有的学段
	 * @return List<Map<String, String>>
	 * @throws
	 */
	List<Map<String, String>> getSchoolStage();

	/**
	 * @Description: 课堂明细
	 * @param classStatistics（课堂统计）
	 * @return ClassStatistics
	 * @throws
	 */
	ClassStatistics getClassDetails(ClassStatistics classStatistics);

	/**
	 * @Description: 导出数据源
	 * @param classStatistics
	 * @return classStatistics
	 * @throws
	 */
	List<ClassStatistics> getExportData(ClassStatistics classStatistics);
	
	/**
	 * @Description: 查询老师课堂统计
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	List<ClassStatistics> queryTeacherClassStatistics(ClassStatistics classStatistics, Page page);
	
	/**
	 * @Description: 查询老师课堂明细
	 * @param classStatistics（课堂统计）
	 * @return ClassStatistics
	 * @throws
	 */
	ClassStatistics getTeacherClassDetails(ClassStatistics classStatistics);
	
	/**
	 * @Description: 老师统计导出数据源
	 * @param classStatistics
	 * @return classStatistics
	 * @throws
	 */
	List<ClassStatistics> getTeacherExportData(ClassStatistics classStatistics);
	
	/**
	 * @Description: 查询年级
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	List<Map<String, Object>> getGrade(Long schoolId,Long schoolStageId);
	
	/**
	 * @Description: 查询学科
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	List<Map<String, Object>> getSubject(Long schoolId,Long schoolStageId);
}
