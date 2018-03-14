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
import cn.strong.leke.question.model.WordImportRecord;
import cn.strong.leke.question.model.WordImportRecordDTO;
import cn.strong.leke.question.model.WordImportRecordError;

/**
 * Word 导入记录服务接口
 * 
 * @author liulongbiao
 * @created 2014年12月12日 下午4:43:51
 * @since v3.2
 */
public interface WordImportRecordService {

	/**
	 * 添加记录
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午9:26:04
	 * @since v3.2
	 * @param record
	 */
	void addWordImportRecord(WordImportRecord record);

	/**
	 * 给记录成功处理数加一
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午9:26:50
	 * @since v3.2
	 * @param recordId
	 */
	void updateIncSuccessedCount(String recordId);

	/**
	 * 更新处理状态
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午11:30:24
	 * @since v3.2
	 * @param recordId
	 * @param status
	 */
	void updateProcessStatus(String recordId, int status);

	/**
	 * 获取记录信息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午9:27:19
	 * @since v3.2
	 * @param recordId
	 * @return
	 */
	WordImportRecordDTO getWordImportRecord(String recordId);

	/**
	 * 添加记录错误消息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 上午10:54:44
	 * @since v3.2.1
	 * @param error
	 */
	void addWordImportRecordError(WordImportRecordError error);

	/**
	 * 删除错误消息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月23日 下午3:28:25
	 * @since v3.2.2
	 * @param error
	 */
	void deleteWordImportRecordError(WordImportRecordError error);

	/**
	 * 查询指定用户的导入记录
	 * <p>
	 * 仅包含当天的成功记录和具有失败题量的记录
	 * </p>
	 * 
	 * @author liulongbiao
	 * @created 2014年12月23日 下午3:06:35
	 * @since v3.2.2
	 * @param userId
	 * @param page
	 * @return
	 */
	List<WordImportRecordDTO> queryMyWordImportRecord(Long userId, Page page);
}
