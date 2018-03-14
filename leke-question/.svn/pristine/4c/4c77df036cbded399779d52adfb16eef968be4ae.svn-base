/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionCommentService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionComment;
import cn.strong.leke.question.dao.mybatis.IQuestionCommentDao;
import cn.strong.leke.question.service.IQuestionCommentService;
import cn.strong.leke.question.service.QuestionService;

/**
 * 题目评论服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionCommentService implements IQuestionCommentService {
	@Resource
	private IQuestionCommentDao questionCommentDao;
	@Resource
	private QuestionService questionService;

	@Override
	public void addQuestionComment(QuestionComment qc) {
		if (qc == null || qc.getQuestionId() == null
				|| StringUtils.isEmpty(qc.getComment())) {
			throw new ValidateException("que.questionComment.info.incomplete");
		}
		int count = questionCommentDao.countUserComments(qc);
		if (count > 0) {
			throw new ValidateException("que.questionComment.exist");
		}
		if (qc.getComment().length() > 200) {
			throw new ValidateException("que.questionComment.info.overlong", 200);
		}
		questionCommentDao.insertQuestionComment(qc);

		questionService.updateIncCommentCount(qc.getQuestionId());
	}

	@Override
	public List<QuestionComment> queryCommentsByQuestionId(Long questionId,
			Page page) {
		return questionCommentDao.queryCommentsByQuestionId(questionId, page);
	}

}
