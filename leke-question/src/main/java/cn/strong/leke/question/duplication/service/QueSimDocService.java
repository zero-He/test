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

import cn.strong.leke.question.duplication.QueSimDoc;


/**
 * 习题相似度存储文档服务接口
 * 
 * @author liulongbiao
 * @created 2015年1月17日 上午10:43:09
 * @since v3.2.2
 */
public interface QueSimDocService {

	/**
	 * 保存习题相似度存储文档
	 * 
	 * @author liulongbiao
	 * @created 2015年1月17日 下午3:02:13
	 * @since v3.2.2
	 * @param doc
	 */
	void saveQueSimDoc(QueSimDoc doc);

	/**
	 * 删除习题相似度存储文档
	 * 
	 * @author liulongbiao
	 * @created 2015年1月17日 下午3:07:33
	 * @since v3.2.2
	 * @param questionId
	 */
	void deleteQueSimDoc(Long questionId);

	/**
	 * 查找相似的习题ID
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午4:08:34
	 * @since v3.2.2
	 * @return
	 */
	List<Long> findSimilarQuestionIds(QueSimDoc doc);
}
