/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.question.FamousSchoolQuestionCheckLog;

/**
 * @author liulongbiao
 *
 */
public interface IFamousSchoolQuestionCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(FamousSchoolQuestionCheckLog log);

	/**
	 * 根据习题和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param questionId
	 * @param schoolId
	 * @return
	 */
	List<FamousSchoolQuestionCheckLog> findByQuestionAndSchool(
			@Param("questionId") Long questionId, @Param("schoolId") Long schoolId);
}
