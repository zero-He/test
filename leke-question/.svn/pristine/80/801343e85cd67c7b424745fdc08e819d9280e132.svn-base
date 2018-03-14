/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service;

import java.util.Date;
import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.SimQueDTO;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月18日 上午9:52:41
 * @since   v3.2.2
 */
public interface DuplicationQuestionManager {

	/**
	 * 执行标记重复任务
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 上午9:59:55
	 * @since v3.2.2
	 */
	void runDupJob();

	/**
	 * 对移除的平台习题作相应更新
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午1:30:31
	 * @since v3.2.2
	 */
	void updateRemovedPlatformQuestions(Date now, Date last);

	/**
	 * 对有更新的平台习题作相应更新
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午1:37:44
	 * @since v3.2.2
	 */
	void updateUpdatedPlatformQuestions(Date now, Date last);

	/**
	 * 移除重复信息
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午1:07:56
	 * @since v3.2.2
	 * @param questionId
	 */
	void removeSimQue(Long questionId);

	/**
	 * 查询疑似重复的习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午3:34:11
	 * @since v3.2.2
	 * @param query
	 * @param page
	 * @return
	 */
	List<SimQueDTO> queryDupQuestions(DupQuestionQuery query, Page page);

	/**
	 * 按分页查找相似习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月30日 下午3:27:15
	 * @since v3.2.2
	 * @param questionId
	 * @param page
	 * @return
	 */
	Page findSimilarities(Long questionId, Page page);

	/**
	 * 删除习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午6:03:59
	 * @since v3.2.2
	 * @param questionId
	 * @param user
	 */
	void deleteQuestion(Long questionId, User user);

	/**
	 * 禁用习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午6:04:09
	 * @since v3.2.2
	 * @param questionId
	 * @param user
	 */
	void disableQuestionWithTx(Long questionId, User user);

	/**
	 * 标记为非重复
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午6:04:20
	 * @since v3.2.2
	 * @param questionId
	 * @param user
	 */
	void updateNotDup(Long questionId, User user);

	/**
	 * 禁用分组中其它习题，仅保留该题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月21日 下午4:24:55
	 * @since v3.2.2
	 * @param dupGroupId
	 * @param questionId
	 * @param user
	 */
	void disableOtherQuestionsWithTx(Long dupGroupId, Long questionId, User user);
}
