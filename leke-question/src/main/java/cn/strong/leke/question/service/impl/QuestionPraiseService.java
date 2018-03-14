/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionPraiseService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.question.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.QuestionPraise;
import cn.strong.leke.question.dao.mybatis.IQuestionPraiseDao;
import cn.strong.leke.question.service.IQuestionPraiseService;
import cn.strong.leke.question.service.QuestionService;

/**
 * 题目点赞服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionPraiseService implements IQuestionPraiseService {
	@Resource
	private IQuestionPraiseDao questionPraiseDao;
	@Autowired
	private QuestionService questionService;

	@Override
	public void addQuestionPraise(QuestionPraise qp) {
		if (qp == null || qp.getQuestionId() == null
				|| qp.getCreatedBy() == null) {
			throw new ValidateException("que.questionPraise.info.incomplete");
		}
		int count = questionPraiseDao.countUserPraises(qp);
		if (count > 0) {
			throw new ValidateException("que.questionPraise.exist");
		}
		questionPraiseDao.insertQuestionPraise(qp);

		questionService.updateIncPraiseCount(qp.getQuestionId());
	}

}
