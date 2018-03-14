package cn.strong.leke.diag.dao.teachingMonitor;

import cn.strong.leke.diag.model.teachingMonitor.ClassBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.EvaluateBean;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.EvaluateDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.QueryEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 11:38:06
 */
public interface EvaluateXDao {

	/**
	 * 获取所有的评价数据
	 * @param vo
	 * @return
	 */
	List<EvaluateBean> selectEvaluateData(RequestVo vo);

	/**
	 * 根据筛选条件查询课堂评价的明细数据
	 * @param vo
	 * @return
	 */
	List<EvaluateDetailBean> selectEvaluateDetailPartData(RequestVo vo);

	/**
	 * 根据条件筛选得到teacherIds
	 * @param queryEvaluate
	 * @return
	 */
	List<Long> selectTeacherIdsBySchoolIdGradeIdSubjectId(QueryEvaluate queryEvaluate);

	/**
	 * 根据schoolId、gradeId查该校该年级的老师ids
	 * @param schoolId
	 * @param gradeId
	 * @return
	 */
	List<Long> selectTeacherIdsBySchoolIdGradeId(@Param("schoolId") Long schoolId, @Param("gradeId") Long gradeId);

	List<ClassBean> selectIsLessonClazz(RequestVo vo);
}
