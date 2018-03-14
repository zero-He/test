/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.UpdatedQuestionQuery;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月18日 下午1:46:20
 * @since   v3.2.2
 */
public interface DuplicationQuestionService {
	/**
	 * 查询更新的平台习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午2:04:02
	 * @since v3.2.2
	 * @param query
	 * @return
	 */
	List<Long> queryUpdatedPlatformQuestionIds(UpdatedQuestionQuery query);

	/**
	 * 查询更新的导入习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午4:00:30
	 * @since v3.2.2
	 * @param query
	 * @return
	 */
	List<Long> queryRemovedPlatformQuestionIds(UpdatedQuestionQuery query);

	/**
	 * 更新疑似重复标识
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 上午11:26:26
	 * @since v3.2.2
	 * @param questionId
	 * @param mayDup
	 */
	void updateMayDup(Long questionId, boolean mayDup);

	/**
	 * 查找疑似重复习题ID列表
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午3:38:31
	 * @since v3.2.2
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryDupQuestionIds(DupQuestionQuery query, Page page);
}
