package cn.strong.leke.diag.dao.studentMonitor;

import cn.strong.leke.diag.model.studentMonitor.ReadyHomeworkBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-21 10:38:59
 */
public interface ReadyAnalyseDao {

	/**
	 * 根据schoolId、gradeId、classId、subjectId等查所有预习作业
	 * @param vo
	 * @return
	 */
	List<ReadyHomeworkBean> queryAllReadyHomework(RequestVo vo);

	/**
	 * 根据schoolId、gradeId、classId、subjectId等查所有SingleIds
	 * @param vo
	 * @return
	 */
	List<Long> querySingleIds(RequestVo vo);

	/**
	 * 根据studentId查该生有预习的课堂
	 * @param studentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> querySingleIdByStudentId(@Param("schoolId") Long schoolId, @Param("studentId") Long studentId, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("subjectId") Long subjectId);

	/**
	 * 查该生该课堂有哪些预习作业
	 * @param singleId
	 * @param studentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ReadyHomeworkBean> queryReadyHomeworkBySingleId(@Param("schoolId") Long schoolId, @Param("singleId") Long singleId, @Param("studentId") Long studentId, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("subjectId") Long subjectId);



}
