/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionRejectionService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-7-29
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.QuestionRejection;

/**
 * 习题退回服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionRejectionService {

	/**
	 * 查询习题退回信息
	 * @param questionId
	 * @return
	 * author：lavender
	 * 2014-7-29下午3:45:02
	 */
	List<QuestionRejection> findQuestionRejection(Long questionId);
	
	
	/**
	 * 忽略退回信息
	 * @param qr
	 * @return
	 * author：lavender
	 * 2014-7-29下午3:44:44
	 */
	int ignoreQuestionRejection(QuestionRejection qr);

	/**
	 * 
	 * 描述: 添加退回信息
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午5:05:38
	 * @since v1.1.0
	 * @param rejection
	 */
	void addQuestionRejection(QuestionRejection rejection);
}
