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
import cn.strong.leke.question.core.question.query.IFamousSchoolQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IFamousSchoolQuestionDao;
import cn.strong.leke.question.model.question.FamousSchoolQuestion;
import cn.strong.leke.question.model.question.query.AgencyFmsSchQuestionQuery;

/**
 * @author liulongbiao
 *
 */
@Service
public class FamousSchoolQuestionQueryService implements IFamousSchoolQuestionQueryService {

	@Resource
	private QuestionDao questionDao;
	@Resource
	private IFamousSchoolQuestionDao famousSchoolQuestionDao;

	@Override
	public List<RepositoryRecord> queryFmsSchQuestions(RepositoryQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao.queryFamousSchoolQuestions(query, page);
		return toFamousSchoolQuestionRecords(questionIds, query.getFamousSchoolId());
	}

	private List<RepositoryRecord> toFamousSchoolQuestionRecords(List<Long> questionIds,
			Long schoolId) {
		if (questionIds.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, FamousSchoolQuestion> m1 = ListUtils.toMap(
				famousSchoolQuestionDao.findByQuestionIds(questionIds, schoolId),
				FamousSchoolQuestion::getQuestionId);

		List<RepositoryRecord> records = RepositoryContext.ofQuestions(questionIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

	@Override
	public List<RepositoryRecord> queryMyAgencyFmsSchQuestions(AgencyFmsSchQuestionQuery query,
			Page page) {
		List<Long> questionIds = famousSchoolQuestionDao.queryMyAgencyFmsSchQuestions(query, page);
		return toFamousSchoolQuestionRecords(questionIds, query.getFamousSchoolId());
	}

}
