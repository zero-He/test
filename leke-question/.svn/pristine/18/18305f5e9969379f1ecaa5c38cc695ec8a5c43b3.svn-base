/**
 * 
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.question.model.question.SchoolQuestion;

/**
 * 学校共享习题关联服务
 * 
 * @author liulb
 * 
 */
public interface SchoolQuestionService {

	/**
	 * 添加学校共享习题关联
	 * 
	 * @param schoolQuestion
	 */
	void addSchoolQuestion(SchoolQuestion schoolQuestion);

	/**
	 *
	 * 描述: 批量添加学校共享习题关联
	 *
	 * @author raolei
	 * @created 2016年6月12日 上午10:36:25
	 * @since v1.0.0
	 * @param schoolQuestions
	 * @return void
	 */
	void addBatchSchoolQuestion(List<SchoolQuestion> schoolQuestions);

	/**
	 * 删除学校共享习题关联
	 * 
	 * @param schoolQuestion
	 */
	void deleteSchoolQuestion(SchoolQuestion schoolQuestion);

	List<SchoolQuestion> findByQuestionIds(List<Long> questionIds, Long schoolId);
}
