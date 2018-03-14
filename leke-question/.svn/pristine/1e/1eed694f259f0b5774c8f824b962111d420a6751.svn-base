package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.model.question.query.AgencySchoolQuestionQuery;
import cn.strong.leke.question.model.question.query.SchoolQuestionCheckQuery;

/**
 * 学校组卷DAO
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolQuestionDao {

	/**
	 * 添加学校课件
	 * 
	 * @param assoc
	 * @return
	 */
	int add(SchoolQuestion assoc);

	/**
	 *
	 * 描述: 批量添加分享到学校课件记录
	 *
	 * @author raolei
	 */
	int insertBatchSchoolQuestion(@Param("assocs") List<SchoolQuestion> schoolQuestions);

	/**
	 * 移除学校课件
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(SchoolQuestion assoc);

	/**
	 * 根据课件和学校获取记录
	 * 
	 * @param questionId
	 * @param schoolId
	 * @return
	 */
	SchoolQuestion getByQuestionAndSchool(@Param("questionId") Long questionId,
			@Param("schoolId") Long schoolId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(SchoolQuestion assoc);

	/**
	 *
	 * 描述: 根据习题ID和学校Id获取学校备课分享的记录
	 *
	 * @author raolei
	 * @param questionId
	 * @param schoolId
	 * @return
	 */
	int countSchoolQuestion(@Param("questionId") Long questionId, @Param("schoolId") Long schoolId);

	/**
	 * 根据习题ID列表查找关联关系
	 * 
	 * @param questionIds
	 * @param schoolId
	 * @return
	 */
	List<SchoolQuestion> findByQuestionIds(@Param("questionIds") List<Long> questionIds,
			@Param("schoolId") Long schoolId);

	/**
	 * 查找我的代录习题列表
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryMyAgencySchoolQuestions(AgencySchoolQuestionQuery query, Page page);

	/**
	 * 查找学校记录审核数据列表
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> querySchoolQuestionForCheck(SchoolQuestionCheckQuery query, Page page);
}
