/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionPraiseService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.question.service;

import cn.strong.leke.model.question.QuestionPraise;

/**
 * 题目点赞服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionPraiseService {

	/**
	 * 保存
	 * @param qp
	 * @return
	 * author：lavender
	 * 2014-6-18下午2:29:23
	 */
	void addQuestionPraise(QuestionPraise qp);
}
