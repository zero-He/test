package cn.strong.leke.question.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionOutlineNodeDao;
import cn.strong.leke.question.service.ISchQueOutlineNodeService;

@Service
public class SchQueOutlineNodeServiceImpl implements ISchQueOutlineNodeService {

	@Resource
	private ISchoolQuestionOutlineNodeDao schoolQuestionOutlineNodeDao;

	@Override
	public void add(SchoolQuestionOutlineNode assoc) {
		if (assoc == null || assoc.getQuestionId() == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		schoolQuestionOutlineNodeDao.add(assoc);
	}

	@Override
	public void delete(Long questionId, Long userId) {
		if (questionId == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		schoolQuestionOutlineNodeDao.delete(questionId, userId);
	}

	@Override
	public SchoolQuestionOutlineNode findOutlineNodeByQuestionId(Long questionId) {
		return schoolQuestionOutlineNodeDao.findOutlineNodeByQuestionId(questionId);
	}

}
