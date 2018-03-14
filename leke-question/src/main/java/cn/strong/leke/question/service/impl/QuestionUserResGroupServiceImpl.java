package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.common.UserResGroupDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.question.IQuestionUserResGroupDao;
import cn.strong.leke.question.model.question.QuestionUserResGroup;
import cn.strong.leke.question.service.IQuestionUserResGroupService;
import cn.strong.leke.remote.service.question.IUserResGroupRemoteService;

@Service
public class QuestionUserResGroupServiceImpl implements IQuestionUserResGroupService {

	@Resource
	private IUserResGroupRemoteService userResGroupRemoteService;
	@Resource
	private IQuestionUserResGroupDao questionUserResGroupDao;

	@Override
	public void addBatchQuestionUserResGroup(Long userResGroupId, Set<Long> questionIds, User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		Validation.notEmpty(questionIds, "没有指定要分组的习题");
		Long userId = user.getId();
		Set<Long> set = filterNotExist(userResGroupId, questionIds);
		if (!set.isEmpty()) {
			questionUserResGroupDao.addBatchQuestionUserResGroup(userResGroupId, set, userId);
		}
	}

	private Set<Long> filterNotExist(Long userResGroupId, Set<Long> questionIds) {
		Set<Long> result = new HashSet<>();
		for (Long questionId : questionIds) {
			int count = questionUserResGroupDao.queryCount(userResGroupId, questionId);
			if (count == 0) {
				result.add(questionId);
			}
		}
		return result;
	}

	@Override
	public void saveMoveBatchQUserResGroup(UserResGroupDTO dto, Set<Long> questionIds,
			User user) {
		if (dto == null || dto.getNewUserResGroupId() == null || dto.getOldUserResGroupId() == null) {
			throw new ValidateException("que.question.userResGroup.info.incomplete");
		}
		Long oldGrpId = dto.getOldUserResGroupId();
		Long newGrpId = dto.getNewUserResGroupId();
		if (oldGrpId.equals(newGrpId)) {
			return;
		}
		Validation.notEmpty(questionIds, "没有指定要移除分组的习题");
		Long userId = user.getId();
		Set<Long> moveSet = filterNotExist(newGrpId, questionIds);
		Set<Long> removeSet = Sets.difference(questionIds, moveSet);
		if (!removeSet.isEmpty()) {
			questionUserResGroupDao.deleteBatchQuestionUserResGroup(oldGrpId, removeSet, userId);
		}
		if (!moveSet.isEmpty()) {
			questionUserResGroupDao.moveBatchQuestionUserResGroup(oldGrpId, newGrpId, moveSet,
					userId);
		}
	}

	@Override
	public void deleteBatchQuestionUserResGroup(Long userResGroupId, Set<Long> questionIds,
			User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		Validation.notEmpty(questionIds, "没有指定要移除分组的习题");
		Long userId = user.getId();
		questionUserResGroupDao
				.deleteBatchQuestionUserResGroup(userResGroupId, questionIds, userId);
	}

	@Override
	public void deleteQURGroupByUserResGroupId(Long userResGroupId, User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		userResGroupRemoteService.deleteUserResGroup(userResGroupId, user);
		Long userId = user.getId();
		questionUserResGroupDao.deleteQURGroupByUserResGroupId(userResGroupId, userId);
	}

	@Override
	public List<QuestionUserResGroup> queryQURGroupByUserResGroupId(Long userResGroupId, Page page) {
		if (userResGroupId == null) {
			return Collections.emptyList();
		}
		return questionUserResGroupDao.queryQURGroupByUserResGroupId(userResGroupId, page);
	}

}
