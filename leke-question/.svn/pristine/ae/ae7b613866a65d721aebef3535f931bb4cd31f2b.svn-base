/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionFeedbackService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-6-16
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedback;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionFeedbackType;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.IQuestionFeedbackDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionFeedbackTypeDao;
import cn.strong.leke.question.service.IQuestionFeedbackService;
import cn.strong.leke.question.service.QuestionManager;

/**
 * 题目反馈服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionFeedbackService implements IQuestionFeedbackService {

	@Resource
	private IQuestionFeedbackDao questionFeedbackDao;
	@Resource
	private QuestionManager questionManager;
	@Resource
	private IQuestionFeedbackTypeDao questionFeedbackTypeDao;
	
	@Override
	public void addQuestionFeedback(QuestionFeedbackDTO qf, User user) {
		if (qf == null || qf.getQuestionId() == null || CollectionUtils.isEmpty(qf.getQuestionFeedbackType())) {
			throw new ValidateException("que.questionFeedback.info.incomplete");
		}
		if (qf.getFeedbackContent().length() > 200) {
			throw new ValidateException("que.questionFeedback.info.overlong", 200);
		}
		Long userId = user.getId();
		qf.setCreatedBy(userId);
		qf.setModifiedBy(userId);
		qf.setUserId(userId);
		qf.setUserName(user.getUserName());
		School school = user.getCurrentSchool();
		if (school != null) {
			qf.setSchoolId(school.getId());
			qf.setSchoolName(school.getName());
		}
		questionFeedbackDao.saveQuestionFeedback(qf);
		Long questionFeedbackId = qf.getQuestionFeedbackId();
		if (CollectionUtils.isNotEmpty(qf.getQuestionFeedbackType())) {
			for (Integer type : qf.getQuestionFeedbackType()) {
				QuestionFeedbackType qft = new QuestionFeedbackType();
				qft.setType(type);
				qft.setCreatedBy(userId);
				qft.setModifiedBy(userId);
				qft.setQuestionFeedbackId(questionFeedbackId);
				questionFeedbackTypeDao.saveQuestionFeedbackType(qft);
			}
		}
	}

	@Override
	public int processQuestionFeedback(QuestionFeedback qf) {
		if(null != qf) {
			//更新题目反馈表设置为已处理
			int result = questionFeedbackDao.processQuestionFeedback(qf);
			//更新题目表中题目状态设置为5纠错，日志表中添加纠错状态日志
			QuestionDTO dto = new QuestionDTO();
			dto.setCreatedBy(qf.getUserId());
			dto.setModifiedBy(qf.getUserId());
			dto.setOperatorName(qf.getUserName());
			dto.setQuestionId(qf.getQuestionId());
			questionManager.updateCorrect(dto);
						
			return result;
		}
		return 0;
	}

	@Override
	public List<QuestionFeedbackDTO> findQuestionFeedback(Long questionId) {
		return questionFeedbackDao.queryQueFeedback(questionId);
	}
	
	@Override
	public List<QuestionFeedbackDTO> findAllQuestionFeedback(Long questionId) {
		return questionFeedbackDao.queryAllQueFeedback(questionId);
	}
	
	@Override
	public List<Integer> findQuestionFeedbackType(Long questionFeedbackId) {
		return questionFeedbackTypeDao.queryQueFeedbackType(questionFeedbackId);
	}

	@Override
	public int ignoreQuestionFeedback(QuestionFeedback qf) {
		if (qf == null || qf.getQuestionId() == null) {
			throw new ValidateException("que.questionFeedback.info.incomplete");
		}
		
		//更新题目反馈表设置为已处理
		int result = questionFeedbackDao.processQuestionFeedback(qf);
		return result;
	}

}
