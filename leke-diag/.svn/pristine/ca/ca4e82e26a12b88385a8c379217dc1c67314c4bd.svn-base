package cn.strong.leke.diag.dao.homework.mybatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.diag.dao.homework.model.AnalysePhase;
import cn.strong.leke.diag.dao.homework.model.LessonExamAnaly;
import cn.strong.leke.diag.dao.homework.model.WorkStatInfo;
import cn.strong.leke.diag.model.CorrectInfoDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkDetailsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsQueryDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsQueryDto;
import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.framework.page.jdbc.Page;

public interface HomeworkDao {

	/**
	 * 描述:根据教师作业id查询详情
	 * @param homeworkId
	 * @return
	 */
	public Homework getHomeworkByHomeworkId(Long homeworkId);

	/**
	 * 按备课标记查询作业列表
	 * @param beikeGuid
	 * @return
	 */
	public List<AnalysePhase> findAnalysePhasesByBeikeGuid(String beikeGuid);

	/**
	 * 更新作业统计状态。
	 * @param homeworkId 作业ID
	 * @param statsStatus 统计状态
	 */
	public void updateHomeworkStatsStatus(@Param("homeworkId") Long homeworkId,
			@Param("statsStatus") Integer statsStatus);

	/**
	 * 根据条件查询学生成绩统计
	 * @param subjectId 学科ID
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @param teacherId 老师ID，可为空
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<Map<String, Object>> findStudentScoreStat(@Param("subjectId") Long subjectId,
			@Param("classType") Long classType, @Param("classId") Long classId, @Param("studentId") Long studentId,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * 根据学科和老师查询老师作业批改情况
	 * @param subjectId 学科ID
	 * @param teacherId 老师ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<CorrectInfoDto> findCorrectInfoDtoList(@Param("subjectId") Long subjectId,
			@Param("teacherId") Long teacherId, @Param("schoolId") Long schoolId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

	/**
	 *	
	 * 描述:学生个人统计
	 *
	 * @author  DuanYanming
	 * @created 2014年7月30日 上午11:05:52
	 * @since   v1.0.0 
	 * @param subjectId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return  List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findStudentScoreSelf(@Param("studentId") Long studentId,
			@Param("subjectId") Long subjectId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	public List<Map<String, Object>> findStuSubjectScore(@Param("studentId") Long studentId);

	/**作业勤奋报告：按课程、学科分组统计列表数据
	 * @param teacherId
	 * @param subjectId
	 * @param className
	 * @return
	 */
	List<CourseSubjectHomeworkStatisticsDto> findCourseSubjectInfo(CourseSubjectHomeworkStatisticsQueryDto queryDto,
			Page page);

	/**作业勤奋报告：查看某一套课下学生作业列表数据
	 * @param queryDto
	 * @param page
	 * @return
	 */
	List<CourseSubjectHomeworkStatisticsStudentsDto> findCourseSubjectStudents(
			CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto, Page page);

	/** 
	 * 老师：学生成绩分析
	 * 按照课程和学科 查询作业数统计
	 * @param queryDto
	 * @param page
	 * @return
	 */
	List<CourseSubjectHomeworkDto> findCourseSubject(CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto,
			Page page);

	/**
	 * 老师：学生成绩分析
	 * 按照课程和学科 查询各单课的作业成绩统计
	 * @param queryDto
	 * @return
	 */
	List<CourseSubjectHomeworkDetailsDto> findCourseSubjectDetails(
			CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto);

	/**
	 * 获取某班某个学科的所有作业（仅限本学期的）
	 * @param classId
	 * @param subjectId
	 * @param SemesterStarDate 学期的起始时间
	 * @return
	 */
	List<Homework> findHwByClassSubject(@Param("classId") Long classId, @Param("subjectId") Long subjectId,
			@Param("closeTime") Date SemesterStarDate);

	/**
	 * 获取老师在某班级布置的作业
	 * @param classId
	 * @param teacherId
	 * @param SemesterStarDate
	 * @return
	 */
	List<Homework> findHwByTeacherClass(@Param("classId") Long classId, @Param("teacherId") Long teacherId,
			@Param("closeTime") Date SemesterStarDate);

	/**
	 * 查询单课某阶段作业成绩。
	 * @param lessonId 单课
	 * @param usePhase 阶段
	 * @return
	 */
	public List<LessonExamAnaly> findExamScoreByLessonIdAndUsePhase(@Param("lessonId") Long lessonId,
			@Param("usePhase") Integer usePhase);

	/**
	 * 查询一段时间内老师作业统计信息
	 * @param teacherId
	 * @param start
	 * @param end
	 * @return
	 */
	public WorkStatInfo getTeachWorkStatInfoByTeacherIdAndTime(@Param("teacherId") Long teacherId,
			@Param("start") Date start, @Param("end") Date end);
}
