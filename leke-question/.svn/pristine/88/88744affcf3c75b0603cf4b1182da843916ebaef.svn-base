/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.question.FamousTeacherQuestion;
import cn.strong.leke.question.model.question.query.AgencyFmsTchQuestionQuery;

/**
 * 名师习题DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousTeacherQuestionDao {
	/**
	 * 添加名师习题
	 * 
	 * @param assoc
	 * @return
	 */
	int add(FamousTeacherQuestion assoc);

	/**
	 * 移除名师习题
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(FamousTeacherQuestion assoc);

	/**
	 * 根据习题和老师获取记录
	 * 
	 * @param questionId
	 * @param teacherId
	 * @return
	 */
	FamousTeacherQuestion getByQuestionAndTeacher(@Param("questionId") Long questionId,
			@Param("teacherId") Long teacherId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(FamousTeacherQuestion assoc);

	/**
	 * 根据习题ID获取关联记录
	 * 
	 * @param questionIds
	 * @param teacherId
	 * @return
	 */
	List<FamousTeacherQuestion> findByQuestionIds(@Param("questionIds") List<Long> questionIds,
			@Param("teacherId") Long teacherId);

	/**
	 * 查询我的代录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryMyAgencyFmsTchQuestions(AgencyFmsTchQuestionQuery query, Page page);
}
