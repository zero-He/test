/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.question.FamousSchoolQuestion;
import cn.strong.leke.question.model.question.query.AgencyFmsSchQuestionQuery;

/**
 * 名校习题DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousSchoolQuestionDao {
	/**
	 * 添加名校习题
	 * 
	 * @param assoc
	 * @return
	 */
	int add(FamousSchoolQuestion assoc);

	/**
	 * 移除名校习题
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(FamousSchoolQuestion assoc);

	/**
	 * 根据习题和学校获取记录
	 * 
	 * @param questionId
	 * @param schoolId
	 * @return
	 */
	FamousSchoolQuestion getByQuestionAndSchool(@Param("questionId") Long questionId,
			@Param("schoolId") Long schoolId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(FamousSchoolQuestion assoc);

	/**
	 * 根据习题ID获取关联记录
	 * 
	 * @param questionIds
	 * @param schoolId
	 *            名校ID，可为 null
	 * @return
	 */
	List<FamousSchoolQuestion> findByQuestionIds(@Param("questionIds") List<Long> questionIds,
			@Param("schoolId") Long schoolId);

	/**
	 * 查询我的代录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryMyAgencyFmsSchQuestions(AgencyFmsSchQuestionQuery query, Page page);
}
