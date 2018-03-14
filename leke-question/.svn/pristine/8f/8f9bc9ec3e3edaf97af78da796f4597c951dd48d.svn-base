/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.question.FamousTeacherQuestionCheckLog;

/**
 * 名师习题审核日志DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousTeacherQuestionCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(FamousTeacherQuestionCheckLog log);

	/**
	 * 根据习题和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param questionId
	 * @param teacherId
	 * @return
	 */
	List<FamousTeacherQuestionCheckLog> findByQuestionAndTeacher(
			@Param("questionId") Long questionId, @Param("teacherId") Long teacherId);
}
