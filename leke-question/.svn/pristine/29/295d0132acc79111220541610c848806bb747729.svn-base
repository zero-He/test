/**
 * 
 */
package cn.strong.leke.question.core.question.query.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.core.question.query.IFamousTeacherQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IFamousTeacherQuestionDao;
import cn.strong.leke.question.model.question.FamousTeacherQuestion;
import cn.strong.leke.question.model.question.query.AgencyFmsTchQuestionQuery;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousTeacherQuestionQueryService implements IFamousTeacherQuestionQueryService {
	@Resource
	private QuestionDao questionDao;
	@Resource
	private IFamousTeacherQuestionDao famousTeacherQuestionDao;

	@Override
	public List<RepositoryRecord> queryFmsTchQuestions(RepositoryQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao.queryFamousTeacherQuestions(query, page);
		return toFamousTeacherQuestionRecords(questionIds, query.getFamousTeacherId());
	}

	private List<RepositoryRecord> toFamousTeacherQuestionRecords(
			List<Long> questionIds, Long teacherId) {
		if (questionIds.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, FamousTeacherQuestion> m1 = ListUtils.toMap(
				famousTeacherQuestionDao.findByQuestionIds(questionIds, teacherId),
				FamousTeacherQuestion::getQuestionId);

		List<RepositoryRecord> records = RepositoryContext.ofQuestions(questionIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

	@Override
	public List<RepositoryRecord> queryMyAgencyFmsTchQuestions(AgencyFmsTchQuestionQuery query,
			Page page) {
		List<Long> questionIds = famousTeacherQuestionDao.queryMyAgencyFmsTchQuestions(query, page);
		return toFamousTeacherQuestionRecords(questionIds, query.getFamousTeacherId());
	}
}
