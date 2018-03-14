package cn.strong.leke.diag.dao.studentMonitor;

import cn.strong.leke.diag.model.studentMonitor.HomeworkDetailBean;
import cn.strong.leke.diag.model.studentMonitor.StuHomeworkDetailBean;
import cn.strong.leke.diag.model.studentMonitor.StudentHomeworkSubmitBean;
import cn.strong.leke.diag.model.teachingMonitor.ClazzBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.model.base.Clazz;

import java.util.List;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-15 23:44:53
 */
public interface HomeworkAnalyseDao {

	/**
	 * 根据schoolId、gradeId、classId查学生studentId
	 * @param vo
	 * @return
	 */
	List<Long> selectStudentIdByParam(RequestVo vo);

	/**
	 * 根据schoolId、gradeId、classId查学生Clazz
	 * @param vo
	 * @return
	 */
	List<ClazzBean> selectClazzByParam(RequestVo vo);

	/**
	 * 查每个学生的作业提交情况
	 * @param vo
	 * @return
	 */
	List<StudentHomeworkSubmitBean> selectStudentHomeworkSubmitStatus(RequestVo vo);

	/**
	 * 查所有学生的所有作业提交情况
	 * @param vo
	 * @return
	 */
	List<StudentHomeworkSubmitBean> selectStudentHomework(RequestVo vo);

	/**
	 * 查共需完成的班级作业份数
	 * @param vo
	 * @return
	 */
	int selectTotalOverClassHomeworkNum(RequestVo vo);

	/**
	 * 查询明细数据
	 * @param vo
	 * @return
	 */
	List<HomeworkDetailBean> selectHomeworkDetailDataPage(RequestVo vo);

	/**
	 * 查看作业详情数据
	 * @param vo
	 * @return
	 */
	List<StuHomeworkDetailBean> selectStuHomeworkDetailData(RequestVo vo);
}
