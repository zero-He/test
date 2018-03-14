/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQuestionPraiseDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.question.dao.mybatis;

import cn.strong.leke.model.question.QuestionPraise;

/**
 * 题目点赞数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionPraiseDao {
	/**
	 * 保存
	 * @param qp
	 * @return
	 * author：lavender
	 * 2014-6-18下午2:29:23
	 */
	int insertQuestionPraise(QuestionPraise qp);
	
	/**
	 * 验证该用户是否已赞
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-6-18下午2:29:32
	 */
	int countUserPraises(QuestionPraise qp);
}
