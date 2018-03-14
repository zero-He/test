/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word;

import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.word.model.QuestionPart;
import cn.strong.leke.question.word.model.WordQuestionInfo;

/**
 * 习题解析器接口
 * 
 * @author liulongbiao
 * @created 2014年12月11日 下午7:41:49
 * @since v3.2
 */
public interface QuestionPartParser {

	/**
	 * 将 QuestionPart 解析为 QuestionDTO
	 * 
	 * @author liulongbiao
	 * @created 2014年12月11日 下午7:42:16
	 * @since v3.2
	 * @param part
	 * @param info
	 * @return
	 */
	QuestionDTO parse(QuestionPart part, WordQuestionInfo info);
}
