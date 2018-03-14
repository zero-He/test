package cn.strong.leke.monitor.core.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.ClassStatistics;

/**
 * @ClassName: IClassStatisticsDao
 * @Description: CRM课堂统计数据层接口
 * @author huangkai
 * @date 2016年11月16日 上午11:06:07
 * @version V1.0
 */
public interface IClassStatisticsDao
{
	/**
	 * @Description: 查询学校课堂统计中客户经理负责的学校学段
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	List<ClassStatistics> querySellerSchool(ClassStatistics classStatistics, Page page);

	/**
	 * @Description: 根据学校ID学段ID查询已结束课堂数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getEndClassNum(Map<String, Object> map);

	/**
	 * @Description: 根据学校ID学段ID查询备课课堂数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getBeikeClassNum(Map<String, Object> map);

	/**
	 * @Description: 根据学校ID学段ID查询上课课堂数
	 * @param map 包括：
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getClassNum(Map<String, Object> map);

	/**
	 * @Description: 根据学校ID学段ID查询作业数及课件数
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getWorkAndCoursewareNum(Map<String, Object> map);

	/**
	 * @Description: 根据学校ID学段ID查询测试次数及问答次数
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getClassTestAndAskNum(Map<String, Object> map);
	
	/**
	 * @Description: 根据学校ID学段ID查询课后数据
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getEndClassData(Map<String, Object> map);
	
	/**
	 * @Description: 查询老师课堂统计的学科年级老师
	 * @param classStatistics（课堂统计）
	 * @param page（分页）
	 * @return List<ClassStatistics>
	 * @throws
	 */
	List<ClassStatistics> queryTeacherSchool(ClassStatistics classStatistics, Page page);
	
	/**
	 * @Description: 根据学科ID年级ID老师ID查询已结束课堂数
	 * @param map 包括：
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getTeacherEndClassNum(Map<String, Object> map);

	/**
	 * @Description: 根据学科ID年级ID老师ID查询备课课堂数
	 * @param map 包括：
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getTeacherBeikeClassNum(Map<String, Object> map);

	/**
	 * @Description: 根据学科ID年级ID老师ID查询上课课堂数
	 * @param map 包括：
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return Map<String, Object>
	 * @throws
	 */
	List<Map<String, Object>> getTeacherClassNum(Map<String, Object> map);
	
	/**
	 * @Description: 根据学科ID年级ID老师ID查询作业数及课件数
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getTeacherWorkAndCoursewareNum(Map<String, Object> map);

	/**
	 * @Description: 根据学科ID年级ID老师ID查询测试次数及问答次数
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getTeacherClassTestAndAskNum(Map<String, Object> map);
	
	/**
	 * @Description: 根据学科ID年级ID老师ID查询课后数据
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @param teacherId（老师ID）
	 * @param statisticsTimeStart（开始时间）
	 * @param statisticsTimeEnd（结束时间））
	 * @return ClassStatistics
	 * @throws
	 */
	List<ClassStatistics> getTeacherEndClassData(Map<String, Object> map);
	
	/**
	 * @Description: 查询年级
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	List<Map<String, Object>> getGrade(@Param("schoolId")Long schoolId,@Param("schoolStageId")Long schoolStageId);
	
	/**
	 * @Description: 查询学科
	 * @param subjectId（学科ID）
	 * @param gradeId（年级ID）
	 * @return ClassStatistics
	 * @throws
	 */
	List<Map<String, Object>> getSubject(@Param("schoolId")Long schoolId,@Param("schoolStageId")Long schoolStageId);
	
	/**
	 * @Description: crm1.1 获取上级管辖的下级人员
	 * @param secondDptIds（市场）
	 * @return List<Long>
	 * @throws
	 */
	List<Long> getSeller(@Param("secondDptIds") List<Long> secondDptIds);
}
