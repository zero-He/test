package cn.strong.leke.question.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.question.service.QuestionSectionService;
import cn.strong.leke.remote.service.question.IQuestionSectionRemoteService;


@Service
public class QuestionSectionRemoteService implements IQuestionSectionRemoteService {

	@Resource
	private QuestionSectionService questionSectionService;

	@Override
	public void addQuestionSection(List<Long> questionIds, QuestionSection section) {
		questionIds.forEach(qid -> {
			section.setQuestionId(qid);
			questionSectionService.addQuestionSection(section);
			QuestionContext.deleteCache(qid);
		});

	}

}
