/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.question.QuestionDTO;

/**
 *
 * 描述:
 *
 * @author  lavender
 * @created 2014年12月30日 下午2:50:54
 * @since   v1.0.0
 */
public interface CheckerQuestionManager {
	/**
	 *	
	 * 描述:导入退回
	 *
	 * @author  lavender
	 * @created 2014年12月30日 下午2:51:28
	 * @since   v1.0.0 
	 * @param dto
	 * @param rejectReason
	 * @return  void
	 */
	void updateImportReject(QuestionDTO dto, String rejectReason);

	/**
	 *	
	 * 描述:导入审核
	 *
	 * @author  lavender
	 * @created 2014年12月30日 下午3:15:17
	 * @since   v1.0.0 
	 * @param dto
	 * @return  void
	 */
	void updateImportCheck(QuestionDTO dto);
}
