package cn.strong.leke.question.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionCheckLogDao;
import cn.strong.leke.question.model.question.SchoolQuestionCheckLog;
import cn.strong.leke.question.service.ISchoolQuestionCheckLogService;

@Service
public class SchoolPaperCheckLogServiceImpl implements ISchoolQuestionCheckLogService {

	@Resource
	private ISchoolQuestionCheckLogDao schoolQuestionCheckLogDao;

	@Override
	public void add(SchoolQuestionCheckLog checkLog) {
		if (checkLog == null || checkLog.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		schoolQuestionCheckLogDao.add(checkLog);
	}

	@Override
	public SchoolQuestionCheckLog getCheckLog(Long checkLogId) {
		return schoolQuestionCheckLogDao.getCheckLog(checkLogId);
	}

}
