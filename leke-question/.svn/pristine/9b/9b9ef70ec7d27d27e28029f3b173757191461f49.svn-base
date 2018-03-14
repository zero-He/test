/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.que;

import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;

/**
 * 按题型转换器
 * 
 * @author liulongbiao
 * @created 2014年12月10日 上午10:27:50
 * @since v3.2
 */
public interface TemplateConverter {

	/**
	 * 根据模板转换 QuestionPart
	 * 
	 * @author liulongbiao
	 * @created 2014年12月12日 下午2:53:53
	 * @since v3.2
	 * @param part
	 * @param info
	 * @param isSub
	 * @param parser
	 * @return
	 */
	QuestionDTO convert(QuestionPart part, WordQuestionInfo info,
			boolean isSub, BaseQuestionPartParser parser);
}
