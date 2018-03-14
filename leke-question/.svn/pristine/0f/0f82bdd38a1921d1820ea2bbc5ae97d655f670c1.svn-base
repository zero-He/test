package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.question.SchoolQuestionCheckLog;


/**
 * 学校组卷审核日志DAO
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolQuestionCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(SchoolQuestionCheckLog log);

	/**
	 *
	 * 描述: 查找一条审核记录
	 *
	 * @author raolei
	 */
	SchoolQuestionCheckLog getCheckLog(Long checkLogId);

	/**
	 * 根据习题和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param questionId
	 * @param schoolId
	 * @return
	 */
	List<SchoolQuestionCheckLog> findByQuestionAndSchool(@Param("questionId") Long questionId,
			@Param("schoolId") Long schoolId);
}
