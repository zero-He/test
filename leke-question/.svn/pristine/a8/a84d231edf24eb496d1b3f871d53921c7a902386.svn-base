package cn.strong.leke.question.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.querys.QuestionSelectQuery;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.model.RandSelectQuestionQuery;
import cn.strong.leke.remote.service.question.IQuestionSelectRemoteService;

@Service
public class QuestionSelectRemoteService implements IQuestionSelectRemoteService {

	@Resource
	private IQuestionQueryService questionQueryService;

	@Override
	public List<Long> queryRandomQuestionIds(QuestionSelectQuery qsQuery) {
		RandSelectQuestionQuery query = new RandSelectQuestionQuery();
		query.setSchoolStageId(qsQuery.getSchoolStageId());
		query.setSubjectId(qsQuery.getSubjectId());
		query.setQuestionTypeId(qsQuery.getQuestionTypeId());
		query.setKnowledgeIds(qsQuery.getKnowledgeIds());
		query.setMaterialNodeIds(qsQuery.getMaterialNodeIds());
		query.setDiffLevel(qsQuery.getDiffLevel());
		query.setSubjective(qsQuery.getIncludeSubjective());
		query.setQuestionNum(qsQuery.getQuestionNum());
		query.setHasSub(qsQuery.getHasSub());
		query.setInQuestionTypeIds(qsQuery.getInQuestionTypeIds());
		query.setShareScope(qsQuery.getShareScope());
		query.setExcludeQueIds(qsQuery.getExcludeQueIds());
		if (query.getShareScope() == null) {
			query.setShareScope(ShareScopes.PLATFORM);
		}
		return questionQueryService.queryRandSelectQuestions(query);
	}

	@Override
	public List<Long> queryHomeworkQuestionIds(QuestionSelectQuery query) {
		return questionQueryService.queryHomeworkSelectQuestions(query);
	}

}
