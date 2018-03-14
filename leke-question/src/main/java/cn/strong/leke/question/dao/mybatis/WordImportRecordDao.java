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

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.WordImportRecord;
import cn.strong.leke.question.model.WordImportRecordDTO;

/**
 * Word 导入操作记录Dao
 * 
 * @author liulongbiao
 * @created 2014年12月12日 下午4:43:38
 * @since v3.2
 */
public interface WordImportRecordDao {

	/**
	 * 添加记录
	 * 
	 * @author liulongbiao
	 * @created 2014年12月12日 下午5:35:24
	 * @since v3.2
	 * @param record
	 * @return
	 */
	int addWordImportRecord(WordImportRecord record);
	
	/**
	 * 给记录成功处理数加一
	 * 
	 * @author liulongbiao
	 * @created 2014年12月12日 下午5:35:34
	 * @since v3.2
	 * @param recordId
	 * @return
	 */
	int incSuccessedCount(String recordId);

	/**
	 * 给记录失败处理数加一
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 上午10:46:28
	 * @since v3.2.1
	 * @param recordId
	 * @return
	 */
	int incFailedCount(String recordId);

	/**
	 * 更新处理状态
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午11:21:29
	 * @since v3.2
	 * @param record
	 * @return
	 */
	int updateProcessStatus(WordImportRecord record);

	/**
	 * 获取记录信息
	 * 
	 * @author liulongbiao
	 * @created 2014年12月12日 下午5:35:50
	 * @since v3.2
	 * @param recordId
	 * @return
	 */
	WordImportRecordDTO getWordImportRecord(String recordId);

	/**
	 * 更新未处理错误题量
	 * 
	 * @author liulongbiao
	 * @created 2014年12月23日 下午2:20:31
	 * @since v3.2.2
	 * @param record
	 * @return
	 */
	int updateRemainFailedCount(WordImportRecord record);

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
