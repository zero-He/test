package cn.strong.leke.diag.dao.studentMonitor;

import cn.strong.leke.diag.model.studentMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;

import java.util.List;

/**
 * @author LIU.SHITING
 * @version 2.7
 * @date 2017-11-30 20:15:19
 */
public interface OtherAnalyseDao {

	/**
	 * 查询有错题本的学生
	 * @param vo
	 * @return
	 */
	List<OtherBean> selectOtherAnalysePageData(RequestVo vo);

	/**
	 * 统计错题本数据
	 * @param vo
	 * @return
	 */
	WrongQuestionByOtherBean selectWrongQuestionData(RequestVo vo);

	/**
	 * 统计乐答数据
	 * @param vo
	 * @return
	 */
	DoubtByOtherBean selectDoubtData(RequestVo vo);

	/**
	 * 所有学生（按筛选条件查出的），有考勤有录像的课堂
	 * @param vo
	 * @return
	 */
	List<OtherLessonBean> selectRideoLesson(RequestVo vo);

	/**
	 * 指定学生（按筛选条件查出的），有考勤有录像的课堂
	 * @param vo
	 * @return
	 */
	List<OtherLessonBean> selectRideoLessonByStuId(RequestVo vo);

	/**
	 * 根据userId查该生有录像的课堂数
	 * @param vo
	 * @return
	 */
	int selectTotalSeeLessonNumById(RequestVo vo);

	/**
	 * 根据课堂IDS，查该课堂是否被该学生观看过
	 * @param bean
	 * @return
	 */
	int selectIsOrNoSeeBySinIds(OtherLessonBean bean);

	/**
	 * 查看总的有录像的课堂数
	 * @param vo
	 * @return
	 */
	int selectTotalSeeLessonNum(RequestVo vo);

	/**
	 * 查询总创建份数
	 * @param vo
	 * @return
	 */
	int selectCreateNum(RequestVo vo);

	/**
	 * 查看总份数
	 * @param vo
	 * @return
	 */
	int selectSeeNum(RequestVo vo);

	/**
	 * 平均每份查看次数
	 * @param vo
	 * @return
	 */
	String selectAvgReadNum(RequestVo vo);

	/**
	 * 查消灭/新增错题数，消灭错题率
	 * @param vo
	 * @return
	 */
	IsOrNoGraspBean selectIsOrNoGraspNum(RequestVo vo);

	/**
	 * 提问数/解答数，问题解答率
	 * @param vo
	 * @return
	 */
	AskOrSolveDoubtBean selectAskOrSolveDoubt(RequestVo vo);

	/**
	 * 查创建笔记数
	 * @param vo
	 * @return
	 */
	int selectCreateBookNum(RequestVo vo);

	/**
	 * 平均每份查看次数
	 * @param vo
	 * @return
	 */
	String selectAvgReadNumByStudentId(RequestVo vo);
}
