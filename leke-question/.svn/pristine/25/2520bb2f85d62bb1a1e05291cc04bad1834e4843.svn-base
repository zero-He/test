/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionRejectionService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-7-29
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.QuestionRejection;
import cn.strong.leke.question.dao.mybatis.IQuestionRejectionDao;
import cn.strong.leke.question.service.IQuestionRejectionService;

/**
 * 习题退回服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionRejectionService implements IQuestionRejectionService {

	@Resource
	private IQuestionRejectionDao questionRejectionDao;
	
	@Override
	public List<QuestionRejection> findQuestionRejection(Long questionId) {
		if(questionId == null || questionId <= 0){
			throw new ValidateException("que.questionRejection.info.incomplete");
		} else {
			return questionRejectionDao.queryQueRejection(questionId);
		}
	}

	@Override
	public int ignoreQuestionRejection(QuestionRejection qr) {
		if(null == qr || qr.getQuestionId() == null ) {
			throw new ValidateException("que.questionRejection.info.incomplete");
		}
		return questionRejectionDao.processQuestionRejection(qr);
	}

	@Override
	public void addQuestionRejection(QuestionRejection rejection) {
		questionRejectionDao.insertQuestionRejection(rejection);
	}

}
