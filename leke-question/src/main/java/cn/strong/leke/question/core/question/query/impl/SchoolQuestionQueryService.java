/**
 * 
 */
package cn.strong.leke.question.core.question.query.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.question.OutlineTreeContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.core.question.query.ISchoolQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionDao;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.model.question.query.AgencySchoolQuestionQuery;
import cn.strong.leke.question.model.question.query.SchoolQuestionCheckQuery;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolQuestionQueryService implements ISchoolQuestionQueryService {
	@Resource
	private ISchoolQuestionDao schoolQuestionDao;
	@Resource
	private QuestionDao questionDao;

	@Override
	public List<RepositoryRecord> querySchoolQuestions(RepositoryQuestionQuery query, Page page) {
		Long outlineNodeId = query.getOutlineNodeId();
		if (outlineNodeId != null) {
			Set<Long> outlineNodeIds = OutlineTreeContext.findSubTreeNodeIds(outlineNodeId);
			if (CollectionUtils.isEmpty(outlineNodeIds)) {
				return Collections.emptyList();
			}
			query.setOutlineNodeIds(outlineNodeIds);
		}
		List<Long> questionIds = questionDao.querySchoolQuestions(query, page);
		return toSchoolQuestionRecords(questionIds, query.getSchoolId());
	}

	@Override
	public List<RepositoryRecord> queryMyAgencySchoolQuestions(AgencySchoolQuestionQuery query,
			Page page) {
		if (query.getUserId() == null || query.getSchoolId() == null) {
			return Collections.emptyList();
		}
		List<Long> questionIds = schoolQuestionDao.queryMyAgencySchoolQuestions(query, page);
		return toSchoolQuestionRecords(questionIds, query.getSchoolId());
	}

	private List<RepositoryRecord> toSchoolQuestionRecords(List<Long> questionIds, Long schoolId) {
		if (questionIds.isEmpty() || schoolId == null) {
			return Collections.emptyList();
		}

		Map<Long, SchoolQuestion> m1 = ListUtils.toMap(
				schoolQuestionDao.findByQuestionIds(questionIds, schoolId),
				SchoolQuestion::getQuestionId);
		
		List<RepositoryRecord> records = RepositoryContext.ofQuestions(questionIds);
		records.forEach(record -> {
			Long id = Long.valueOf(record.getResId());
			record.attr("assoc", m1.get(id));
		});

		return records;
	}

	@Override
	public List<RepositoryRecord> querySchoolQuestionForCheck(SchoolQuestionCheckQuery query,
			Page page) {
		if (query.getSchoolId() == null) {
			return Collections.emptyList();
		}
		List<Long> questionIds = schoolQuestionDao.querySchoolQuestionForCheck(query, page);
		return toSchoolQuestionRecords(questionIds, query.getSchoolId());
	}
}
