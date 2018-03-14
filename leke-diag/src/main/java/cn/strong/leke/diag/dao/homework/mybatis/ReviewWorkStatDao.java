package cn.strong.leke.diag.dao.homework.mybatis;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.diag.dao.homework.model.ResTypeStat;
import cn.strong.leke.diag.model.ReviewCount;
import cn.strong.leke.diag.model.report.ResReviewState;

public interface ReviewWorkStatDao {

	/**
	 * 查询单课下某阶段某类型作业的数量
	 * @param lessonId 单课ID
	 * @param resType 资源类型
	 * @param phase 备课阶段
	 * @return
	 */
	public int findReviewResNumByLessonId(@Param("lessonId") Long lessonId, @Param("resType") Integer resType,
			@Param("phase") Integer phase);

	/**
	 * 查询单课下按时完成某阶段某类型作业的学生
	 * @param lessonId 单课ID
	 * @param resType 资源类型
	 * @param phase 备课阶段
	 * @return
	 */
	public Set<Long> findReviewUserIdsByLessonId(@Param("lessonId") Long lessonId, @Param("resType") Integer resType,
			@Param("phase") Integer phase);

	/**
	 * 获取学生的单预复习完成情况
	 * @param studentId 学生ID
	 * @param lessonIds 单课ID列表
	 * @param resType 资源类型
	 * @param phase 备课阶段
	 * @return
	 */
	public List<ResReviewState> findResReviewStateByStudentId(@Param("studentId") Long studentId,
			@Param("lessonIds") List<Long> lessonIds, @Param("resType") Integer resType, @Param("phase") Integer phase);

	/**
	 * 查询有备课的课堂数量
	 * @param lessonIds
	 * @return
	 */
	public int getBeikeLessonNumByLessonIds(@Param("lessonIds") List<Long> lessonIds);

	/**
	 * 获取按类型的备课统计信息
	 * @param lessonIds
	 * @return
	 */
	public List<ResTypeStat> findResTypeStatByLessonIds(@Param("lessonIds") List<Long> lessonIds);

	/**
	 * 查询老师备课数量统计
	 * @param lessonIds
	 * @param phase
	 * @return
	 */
	public List<ReviewCount> findTeacherReviewCountByLessonIds(@Param("lessonIds") List<Long> lessonIds,
			@Param("phase") Integer phase);

	/**
	 * 查询某个学生预复习数量统计
	 * @param userId
	 * @param lessonIds
	 * @param phase
	 * @param resType
	 * @return
	 */
	public List<ReviewCount> getStudentReviewCountByLessonIds(@Param("userId") Long userId,
			@Param("lessonIds") List<Long> lessonIds, @Param("phase") Integer phase, @Param("resType") Integer resType);

	/**
	 * 查询学生预复习数量统计
	 * @param lessonIds
	 * @param phase
	 * @param resType
	 * @return
	 */
	public List<ReviewCount> findStudentReviewCountByLessonIds(@Param("lessonIds") List<Long> lessonIds,
			@Param("phase") Integer phase, @Param("resType") Integer resType);
}
