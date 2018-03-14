/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.question.ITeacherQuestionDao;
import cn.strong.leke.question.model.question.TeacherQuestion;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.question.service.TeacherQuestionService;

/**
 *
 * 描述:
 *
 * @author liulb
 * @created 2014年6月23日 下午2:16:17
 * @since v1.0.0
 */
@Service
public class TeacherQuestionServiceImpl implements TeacherQuestionService {

	@Autowired
	private ITeacherQuestionDao teacherQuestionDao;
	@Autowired
	private QuestionService questionService;

	@Override
	public Award addTeacherQuestion(TeacherQuestion tq, User user) {
		if (tq == null || tq.getTeacherId() == null
				|| tq.getQuestionId() == null) {
			throw new ValidateException("que.teaQue.info.incomplete");
		}
		// 判断习题是否自己创建
		QuestionDTO question = questionService.getQuestion(tq.getQuestionId());
		if (question == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (question.getCreatedBy().equals(tq.getCreatedBy())) {
			throw new ValidateException("que.teaQue.info.own");
		}
		int count = teacherQuestionDao.countFavorite(tq.getQuestionId(), tq.getTeacherId());
		if (count > 0) {
			throw new ValidateException("que.teaQue.already.exist");
		}
		teacherQuestionDao.addFavorite(tq.getQuestionId(), tq.getTeacherId());
		questionService.updateIncFavCount(question.getQuestionId());
		Long roleId = user.getCurrentRole().getId();
		if (!roleId.equals(RoleCst.TEACHER)) {
			return Award.ignore();
		}
		DynamicInfo info = createDynamicForAddFavQue(user, question);
		return DynamicHelper.publish(info);
	}

	@Override
	public Award addBatchTeacherQuestion(Long[] questionIds, User user) {
		if (CollectionUtils.isEmpty(questionIds)) {
			throw new ValidateException("que.teaQue.info.incomplete");
		}
		List<TeacherQuestion> addQuestions = new ArrayList<TeacherQuestion>();
		List<DynamicInfo> infos = new ArrayList<DynamicInfo>();
		List<Long> addQuestionIds = new ArrayList<Long>();
		for (Long questionId : questionIds) {
			Long teacherId = user.getId();
			TeacherQuestion fav = new TeacherQuestion();
			fav.setQuestionId(questionId);
			fav.setTeacherId(teacherId);
			fav.setCreatedBy(teacherId);
			fav.setModifiedBy(teacherId);
			QuestionDTO question = questionService.getQuestion(questionId);
			if (question == null) {
				continue;
			}
			if (question.getCreatedBy().equals(teacherId)) {
				continue;
			}
			int count = teacherQuestionDao.countFavorite(questionId, teacherId);
			if (count > 0) {
				continue;
			}
			infos.add(createDynamicForAddFavQue(user, question));
			addQuestions.add(fav);
			addQuestionIds.add(questionId);
		}
		if (CollectionUtils.isNotEmpty(addQuestions)) {
			addQuestions.forEach(tq -> {
				teacherQuestionDao.addFavorite(tq.getQuestionId(), tq.getTeacherId());
			});
			questionService.updateBatchIncFavCount(addQuestionIds);
		}
		Long roleId = user.getCurrentRole().getId();
		if (!roleId.equals(RoleCst.TEACHER)) {
			return Award.ignore();
		}
		return DynamicHelper.publish(infos);
	}


	private DynamicInfo createDynamicForAddFavQue(User user, QuestionDTO question) {
		DynamicInfo info = new DynamicInfo();
		info.setTypeId(IncentiveTypes.TEACHER.REP_FAVORITE);
		info.setTitle("题号: " + question.getQuestionId());
		info.setUserId(user.getId());
		info.setUserName(user.getUserName());
		info.setRoleId(RoleCst.TEACHER);
		info.setSchoolId(user.getCurrentSchool().getId());
		return info;
	}

	@Override
	public void deleteTeacherQuestion(TeacherQuestion tq) {
		if (tq == null || tq.getTeacherId() == null
				|| tq.getQuestionId() == null) {
			throw new ValidateException("que.teaQue.info.incomplete");
		}
		teacherQuestionDao.deleteFavorites(new Long[] { tq.getQuestionId() }, tq.getTeacherId());
	}

	@Override
	public void deleteBatchTeacherQuestion(Long[] questionIds, Long teacherId) {
		if (CollectionUtils.isEmpty(questionIds)) {
			throw new ValidateException("que.teaQue.info.incomplete");
		}
		teacherQuestionDao.deleteFavorites(questionIds, teacherId);
	}
}
