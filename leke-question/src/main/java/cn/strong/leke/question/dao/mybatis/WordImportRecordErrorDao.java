/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.question.model.WordImportRecordError;

/**
 * Word 导入记录错误明细
 * 
 * @author liulongbiao
 * @created 2014年12月18日 上午11:09:25
 * @since v3.2.1
 */
public interface WordImportRecordErrorDao {

	/**
	 * 添加错误信息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 上午11:11:18
	 * @since v3.2.1
	 * @param error
	 * @return
	 */
	int addWordImportRecordError(WordImportRecordError error);

	/**
	 * 根据记录标识查找错误列表
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 上午11:11:27
	 * @since v3.2.1
	 * @param recordId
	 * @return
	 */
	List<WordImportRecordError> findWordImportRecordErrors(String recordId);

	/**
	 * 删除错误信息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月23日 下午3:10:47
	 * @since v3.2.2
	 * @param errorId
	 * @return
	 */
	int deleteWordImportRecordError(Long errorId);

	/**
	 * 根据 recordId 查询错误信息数量
	 * 
	 * @author liulongbiao
	 * @created 2014年12月23日 下午3:15:21
	 * @since v3.2.2
	 * @param recordId
	 * @return
	 */
	int countErrorsByRecordId(String recordId);
}
