/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQuestionCommentDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionComment;

/**
 * 题目评论数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionCommentDao {
	
	/**
	 * 保存评论
	 * @param qc
	 * @return
	 * author：lavender
	 * 2014-6-18下午2:29:23
	 */
	int insertQuestionComment(QuestionComment qc);
	
	/**
	 * 验证该用户是否已评论
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-6-18下午2:29:32
	 */
	int countUserComments(QuestionComment qc);
	
	/**
	 * 
	 * 描述: 根据题目ID获取评论列表
	 * 
	 * @author liulb
	 * @created 2014年6月24日 上午11:20:14
	 * @since v1.0.0
	 * @param questionId
	 * @param page
	 * @return List<QuestionComment>
	 */
	List<QuestionComment> queryCommentsByQuestionId(Long questionId, Page page);
}
