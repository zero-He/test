/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionRejection;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.model.ImportedQuestionQuery;

/**
 *
 *
 * @author  liulongbiao
 * @created 2014年12月26日 下午3:35:57
 * @since   v3.2.2
 */
public interface ResearcherQuestionManager {

	/**
	 * 查询导入的习题列表
	 * 
	 * @author liulongbiao
	 * @created 2014年12月26日 下午4:09:37
	 * @since v3.2.2
	 * @param query
	 * @param page
	 * @return
	 */
	List<QuestionDTO> queryImportedQuestions(ImportedQuestionQuery query,
			Page page);

	/**
	 *	
	 * 描述:审核通过
	 *
	 * @author  lavender
	 * @created 2015年1月9日 上午10:37:21
	 * @since   v1.0.0 
	 * @param dto
	 * @return  void
	 */
	void updateChecked(Long questionId, Researcher researcher);

	/**
	 * 教师共享审核通过
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午2:09:53
	 * @since v3.2.2
	 * @param questionId
	 * @param researcher
	 */
	void updateCheckedTeacherShare(Long questionId, Researcher researcher);

	/**
	 * 教师共享审核退回
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午2:48:56
	 * @since v3.2.2
	 * @param rejection
	 * @param researcher
	 */
	void updateRejectedTeacherShare(QuestionRejection rejection,
			Researcher researcher);
}
