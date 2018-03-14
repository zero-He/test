package cn.strong.leke.question.core.question.query.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.question.core.question.query.IQuestionLogQueryService;
import cn.strong.leke.question.dao.mybatis.IQuestionLogDao;
import cn.strong.leke.question.model.InputStatisQuery;

@Service
public class QuestionLogQueryService implements IQuestionLogQueryService {

	@Resource
	private IQuestionLogDao questionLogDao;

	@Override
	public long countCheckAmount(InputStatisQuery query) {
		return questionLogDao.countCheckAmount(query);
	}

}
