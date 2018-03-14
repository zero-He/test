package cn.strong.leke.diag.dao.lesson.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.diag.dao.lesson.model.EvalQuery;
import cn.strong.leke.diag.dao.lesson.model.EvaluateDTO;
import cn.strong.leke.diag.dao.lesson.model.EvaluateInfo;
import cn.strong.leke.diag.model.UserRate;
import cn.strong.leke.framework.page.jdbc.Page;

public interface EvaluateDao {

	/**
	 * 获取课堂的评价信息
	 * @param lessonId
	 * @return
	 */
	public EvaluateInfo getEvaluateInfoByLessonId(Long lessonId);

	/**
	 * 获取一些课堂的评价信息
	 * @param courseSingleIds
	 * @return
	 */
	public EvaluateInfo getEvaluateInfoByCourseSingleIds(@Param("courseSingleIds") List<Long> courseSingleIds);

	/**
	 * 查询评价信息
	 * @param query
	 * @param page
	 * @return
	 */
	public List<EvaluateDTO> findEvaluateByLessonId(EvalQuery query, Page page);

	/**
	 * 获取老师的评价信息
	 * @param teacherId
	 * @return
	 */
	public EvaluateInfo getEvaluateInfoByTeacherId(Long teacherId);

	/**
	 * 查询学生一批单课的评价比例
	 * @param studentId
	 * @param courseSingleIds
	 * @return
	 */
	public int getEvalRateByCourseSingleIds(@Param("studentId") Long studentId,
			@Param("courseSingleIds") List<Long> courseSingleIds);

	/**
	 * 查询一批单课的学生评价比例
	 * @param courseSingleIds
	 * @return
	 */
	public List<UserRate> findEvalRatesByCourseSingleIds(@Param("courseSingleIds") List<Long> courseSingleIds);
}
