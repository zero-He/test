package cn.strong.leke.question.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.question.IQuestionShareLogDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionCheckLogDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionDao;
import cn.strong.leke.question.model.question.QuestionShareLog;
import cn.strong.leke.question.model.question.SchoolQuestionCheckLog;
import cn.strong.leke.question.model.question.query.QuestionShareLogQuery;
import cn.strong.leke.question.service.IQuestionShareLogService;

@Service
public class QuestionShareLogServiceImpl implements IQuestionShareLogService {
	@Resource
	private IQuestionShareLogDao questionShareLogDao;
	@Resource
	private ISchoolQuestionDao schoolQuestionDao;
	@Resource
	private ISchoolQuestionCheckLogDao schoolQuestionCheckLogDao;


	@Override
	public void shareQuestionToSchool(Long questionId, User user) {
		if (questionId == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		QuestionShareLog shareLog = defaultData(questionId, user);
		shareLog.setShareScope(ShareScopes.SCHOOL);
		questionShareLogDao.add(shareLog);
	}

	@Override
	public void shareQuestionToLeke(Long questionId, User user) {
		if (questionId == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		QuestionShareLog shareLog = defaultData(questionId, user);
		shareLog.setShareScope(ShareScopes.LEKE_SHARE);
		questionShareLogDao.add(shareLog);

	}



	@Override
	public List<QuestionRepositoryRecord> queryQuestionShareInfo(QuestionShareLogQuery query, Page page) {
		List<QuestionShareLog> shareLogs = questionShareLogDao.queryQuestionShareLog(query, page);
		return toRepositoryRecord(shareLogs);
	}

	private List<QuestionRepositoryRecord> toRepositoryRecord(List<QuestionShareLog> shareLogs) {
		return ListUtils.map(shareLogs, shareLog -> {
			Long questionId = shareLog.getQuestionId();
			List<RepositoryRecord> records = RepositoryContext.ofQuestions(Arrays.asList(questionId));
			QuestionRepositoryRecord record = (QuestionRepositoryRecord) records.get(0);
			record.attr("check", shareLog);
			if (shareLog.getShareScope().equals(ShareScopes.SCHOOL)) {
				Long checkLogId = shareLog.getCheckLogId();
				if (checkLogId != null) {
					SchoolQuestionCheckLog assoc = schoolQuestionCheckLogDao.getCheckLog(checkLogId);
					record.attr("assoc", assoc);
				}
			}
			return record;
		});
	}
	private QuestionShareLog defaultData(Long questionId, User user) {
		QuestionShareLog shareLog = new QuestionShareLog();
		Long userId = user.getId();
		Long schoolId = user.getCurrentSchool().getId();
		shareLog.setQuestionId(questionId);
		shareLog.setUserId(userId);
		shareLog.setSchoolId(schoolId);
		shareLog.setCreatedBy(userId);
		shareLog.setModifiedBy(userId);
		return shareLog;
	}

}
