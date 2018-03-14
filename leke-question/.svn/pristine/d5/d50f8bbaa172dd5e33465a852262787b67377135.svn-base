package cn.strong.leke.question.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.model.wrong.HomeworkForWrong;
import cn.strong.leke.model.wrong.HomeworkForWrongMQ;
import cn.strong.leke.question.dao.mybatis.wrongquestion.IWrongQuestionDao;
import cn.strong.leke.question.model.wrongquestion.WrongQuestion;
import cn.strong.leke.question.model.wrongquestion.WrongQuestionQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubjKnowQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubject;
import cn.strong.leke.question.model.wrongquestion.WrongSubjectKnowledge;
import cn.strong.leke.question.service.IWrongQuestionService;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.question.service.QuestionKnowledgeService;

/**
 * 老师错题本 service
 * @author Zhang Fujun
 * @date 2017年1月12日
 */
@Service
public class WrongQuestionService implements IWrongQuestionService {

	@Resource
	private IWrongQuestionDao iwrongQuestionDao;
	@Resource
	private QuestionKnowledgeService questionKnowledgeService;
	@Resource
	private KnowledgeService knowledgeService;
	
	@Override
	public WrongSubject getWrongSubject(Long teacherId, Long classId, Long subjectId,Long schoolId) {
		return iwrongQuestionDao.getWrongSubject(teacherId, classId, subjectId,schoolId);
	}

	@Override
	public void saveWrongSubjRate(WrongSubject wrongSubject) {
		Integer result = iwrongQuestionDao.updateWrongSubjRate(wrongSubject);
		if(result == 0){
			Date curDate = new Date();
			wrongSubject.setCreatedBy(wrongSubject.getUserId());
			wrongSubject.setModifiedBy(wrongSubject.getUserId());
			wrongSubject.setCreatedOn(curDate);
			wrongSubject.setModifiedOn(curDate);
			iwrongQuestionDao.insertWrongSubject(wrongSubject);
		}
	}

	@Override
	public WrongQuestion getWrongQuestion(Long id) {
		return iwrongQuestionDao.getWrongQuestion(id);
	}

	@Override
	public void delWrongQuestion(WrongQuestion wrongQuestion) {
		iwrongQuestionDao.delWrongQuestion(wrongQuestion);
	}

	@Override
	public List<WrongSubjectKnowledge> findWrongSubjectKnowledges(WrongSubjKnowQuery query) {
		if (query == null || query.getClassId() == null || query.getSubjectId() == null) {
			return Collections.emptyList();
		}
		List<Long> questionIds = iwrongQuestionDao.findWrongQuestionId(query);
		if (CollectionUtils.isEmpty(questionIds)) {
			return Collections.emptyList();
		}
		List<Map<Long, Long>> knowledgeQuestionTotal = questionKnowledgeService.findKnowledgeQuestionTotal(questionIds);
		List<WrongSubjectKnowledge> list = new ArrayList<WrongSubjectKnowledge>();
	
		List<Long> knowledgeIds = knowledgeQuestionTotal.stream().map(v->v.get("id")).collect(Collectors.toList());
		for (Long kid : knowledgeIds)  {
			WrongSubjectKnowledge item = new WrongSubjectKnowledge();
			item.setKnowledgeId(kid);
			Knowledge klg = knowledgeService.getKnowledge(kid);
			if (klg != null) {
				item.setKnowledgeName(klg.getKnowledgeName());
			}
			item.setQuestionTotal(knowledgeQuestionTotal.stream().filter(v->v.get("id").equals(kid)).findFirst().get().get("total"));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<WrongQuestion> findWrongQuestion(WrongQuestionQuery query, Page page) {
		if (query == null || query.getClassId() == null) {
			return Collections.emptyList();
		}
		return iwrongQuestionDao.findWrongQuestion(query, page);
	}

	@Override
	public void saveWrongQuestion(HomeworkForWrongMQ wrongMQ) {
		WrongQuestion wrongQuestion = new WrongQuestion();
		HomeworkForWrong  homework = wrongMQ.getHomework();
		wrongQuestion.setAssignDate(homework.getStartTime());
		wrongQuestion.setHomeworkId(homework.getHomeworkId());
		wrongQuestion.setWrongStuTotal(wrongMQ.getWrongStuTotal());
		wrongQuestion.setClassId(homework.getClassId());
		wrongQuestion.setCreatedBy(homework.getTeacherId());
		wrongQuestion.setCreatedOn(new Date());
		wrongQuestion.setErrorTotal(1L);
		wrongQuestion.setUserId(homework.getTeacherId());
		wrongQuestion.setSchoolId(homework.getSchoolId());
		wrongQuestion.setRate(wrongMQ.getSingleQueRate());
		wrongQuestion.setSourceName(homework.getHomeworkName());
		wrongQuestion.setSubjectId(homework.getSubjectId());
		wrongQuestion.setClassId(homework.getClassId());
		wrongQuestion.setQuestionId(wrongMQ.getQuestionId());
		Integer result = iwrongQuestionDao.updateWrongQuestion(wrongQuestion);
		if (result == 0) {
			iwrongQuestionDao.insertWrongQuestion(wrongQuestion);
		}
		//写入学科数据
		WrongSubject wrongSubject = iwrongQuestionDao.getWrongSubject(homework.getTeacherId(), homework.getClassId(), homework.getSubjectId(),homework.getSchoolId());
		if(wrongSubject == null){
			wrongSubject = new WrongSubject();
			wrongSubject.setClassId(homework.getClassId());
			wrongSubject.setCreatedBy(wrongQuestion.getCreatedBy());
			wrongSubject.setCreatedOn(wrongQuestion.getCreatedOn());
			wrongSubject.setSchoolId(wrongQuestion.getSchoolId());
			wrongSubject.setSubjectId(wrongQuestion.getSubjectId());
			wrongSubject.setUserId(wrongQuestion.getUserId());
			iwrongQuestionDao.insertWrongSubject(wrongSubject);
		}
	}

}
