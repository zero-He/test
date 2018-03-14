/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.question.TeacherQuestion;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月23日 下午1:45:55
 * @since   v1.0.0
 */
public interface TeacherQuestionService {

	/**
	 * 
	 * 描述: 添加习题收藏
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午2:15:19
	 * @since v1.0.0
	 * @param teacherQuestion
	 * @return void
	 */
	Award addTeacherQuestion(TeacherQuestion teacherQuestion, User user);

	Award addBatchTeacherQuestion(Long[] questionIds, User user);

	/**
	 * 
	 * 描述: 移除习题收藏
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午2:15:44
	 * @since v1.0.0
	 * @param teacherQuestion
	 * @return void
	 */
	void deleteTeacherQuestion(TeacherQuestion teacherQuestion);

	void deleteBatchTeacherQuestion(Long[] questionIds, Long teacherId);
}
