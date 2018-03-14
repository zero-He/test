package cn.strong.leke.diag.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.strong.leke.diag.dao.homework.model.AnalysePhase;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDtlDao;
import cn.strong.leke.diag.model.ClassAnalysisDto;
import cn.strong.leke.diag.model.ClassDiligentDto;
import cn.strong.leke.diag.model.ClassSubjectScoreDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsQueryDto;
import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.model.StuAvgScoreDto;
import cn.strong.leke.diag.model.StuDiligentDto;
import cn.strong.leke.diag.model.StuSubjQuery;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.StuSubjWork;
import cn.strong.leke.diag.model.StuSubjectScoreDto;
import cn.strong.leke.diag.model.SubjectAnalysisDto;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.remote.model.homework.HomeworkDtlRemote;

@Repository
public class HomeworkDtlService {

	@Resource
	private HomeworkDtlDao homeworkDtlDao;

	/**
	 * 查询作业明细信息
	 * @param homeworkDtlId
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByHomeworkDtlId(Long homeworkDtlId) {
		return this.homeworkDtlDao.getHomeworkDtlByHomeworkDtlId(homeworkDtlId);
	}

	/**
	 * 按备课标记查询作业列表
	 * @param beikeGuid
	 * @param studentId
	 * @return
	 */
	public List<AnalysePhase> findStudentAnalysePhasesByBeikeGuid(String beikeGuid, Long studentId) {
		return this.homeworkDtlDao.findStudentAnalysePhasesByBeikeGuid(beikeGuid, studentId);
	}

	/**
	 * 根据作业ID及学生ID获取学生作业
	 * @param homeworkId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByHomeworkIdAndStudentId(Long homeworkId, Long studentId) {
		return this.homeworkDtlDao.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId, studentId);
	}

	/**
	 * 根据作业标识，查询作业统计信息
	 * @param homeworkId
	 * @return
	 */
	public List<HomeworkDtl> findHomeworkDtlByHomeworkId(Long homeworkId) {
		return this.homeworkDtlDao.findHomeworkDtlByHomeworkId(homeworkId);
	}

	/**
	 * 获取一批学生的学科作业成绩（分析用，成绩为得分率换算出来的分数）
	 * @param query
	 * @return
	 */
	public List<StuSubjScore> findStuSubjScores(StuSubjQuery query) {
		return this.homeworkDtlDao.findStuSubjScores(query);
	}

	/**
	 * 获取一批学生的学科完成情况（分析用，参与findStuSubjScores）
	 * @param query
	 * @return
	 */
	public List<Long> findHomeworkDtlIds(StuSubjQuery query) {
		return this.homeworkDtlDao.findHomeworkDtlIds(query);
	}

	/**
	 * 获取一批学生的学科完成情况（分析用）
	 * @param query
	 * @return
	 */
	public List<StuSubjWork> findStuSubjWorks(StuSubjQuery query) {
		return this.homeworkDtlDao.findStuSubjWorks(query);
	}

	/**
	 * 根据班级和学科查询学生平均成绩列表
	 * @param classId 班级类型
	 * @param classId 班级ID
	 * @param subjectId 学科ID
	 * @param teacherId 教师ID
	 * @return
	 */
	public List<StuAvgScoreDto> findStuAvgScoreDtoList(Long classType, Long classId, Long subjectId, Long teacherId) {
		return this.homeworkDtlDao.findStuAvgScoreDtoList(classType, classId, subjectId, teacherId);
	}

	/**
	 * 根据条件查询学生作业提交情况列表
	 * @param classId 班级类型
	 * @param classId 班级ID
	 * @param subjectId 学科ID
	 * @param teacherId 教师ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<StuDiligentDto> findStuDiligentDtoList(Long classType, Long classId, Long subjectId, Long teacherId,
			Date startTime, Date endTime) {
		return this.homeworkDtlDao.findStuDiligentDtoList(classType, classId, subjectId, teacherId, startTime, endTime);
	}

	/**
	 * 根据条件查询班级作业提交情况列表
	 * @param classIdList 班级ID列表
	 * @param classId 班级类型
	 * @param subjectId 学科ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<ClassDiligentDto> findClassDiligentDtoList(List<Long> classIdList, Long classType, Long subjectId,
			Date startTime, Date endTime) {
		return this.homeworkDtlDao.findClassDiligentDtoList(classIdList, classType, subjectId, startTime, endTime);
	}

	/**
	 * 根据班级统计该班级学科优劣
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @return
	 */
	public List<SubjectAnalysisDto> findSubjectAnalysisDtoList(Long classType, Long classId) {
		return this.homeworkDtlDao.findSubjectAnalysisDtoList(classType, classId);
	}

	/**
	 * 统计年级下所有班级学科优劣
	 * @param classId 班级类型
	 * @param classIdList 班级ID列表
	 * @param subjectId 学科ID
	 * @return
	 */
	public List<ClassAnalysisDto> findClassAnalysisDtoList(Long classType, List<Long> classIdList, Long subjectId) {
		return this.homeworkDtlDao.findClassAnalysisDtoList(classType, classIdList, subjectId);
	}

	/**
	 * 根据班级统计该班级学生各学科平均分
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @return
	 */
	public List<StuSubjectScoreDto> findStuSubjectScoreDtoList(Long classType, Long classId) {
		return this.homeworkDtlDao.findStuSubjectScoreDtoList(classType, classId);
	}

	/**
	 * 统计单个学生各学科平均分
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @return
	 */
	public List<StuSubjectScoreDto> findOneStuSubjectScoreDtoList(Long classType, Long classId, Long studentId) {
		return this.homeworkDtlDao.findOneStuSubjectScoreDtoList(classType, classId, studentId);
	}

	/**
	 * 根据班级统计该班级整体各学科成绩
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @return
	 */
	public List<ClassSubjectScoreDto> findClassSubjectScoreDtoList(Long classType, Long classId) {
		return this.homeworkDtlDao.findClassSubjectScoreDtoList(classType, classId);
	}

	/**
	 *	
	 * 描述:学生作业提交状态统计
	 *
	 * @author  DuanYanming
	 * @created 2014年7月30日 上午11:25:07
	 * @since   v1.0.0 
	 * @param studentId
	 * @param subjectId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return  List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findStudentSubmitState(Long studentId, Long subjectId, Long classType,
			Long classId, Date startTime, Date endTime) {
		return this.homeworkDtlDao.findStudentSubmitState(studentId, subjectId, classType, classId, startTime, endTime);
	}

	/**
	 * 查询一批学生的一段时间中某学科所有作业的平均得分率（百分制）
	 * @param userIds 学生列表
	 * @param subjectId 学科ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public BigDecimal getUsersAvgScore(List<Long> userIds, Long subjectId, Date startTime, Date endTime) {
		return this.homeworkDtlDao.getUsersAvgScore(userIds, subjectId, startTime, endTime);
	}

	/**作业勤奋报告：学生查询按照课程学科分组的作业统计信息
	 * @param queryDto
	 * @param page
	 * @return
	 */
	public List<CourseSubjectHomeworkStatisticsStudentsDto> findMyCourseSubject(
			CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto, Page page) {
		return this.homeworkDtlDao.findMyCourseSubject(queryDto, page);
	}

	/**
	 * 根据作业ID ，获取所有学生作业
	 * @param hwIds
	 * @return
	 */
	public List<HomeworkDtlRemote> findHwDtlByHomeworkIds(@Param("hwIds") List<Long> hwIds) {
		return this.homeworkDtlDao.findHwDtlByHomeworkIds(hwIds);
	}
}
