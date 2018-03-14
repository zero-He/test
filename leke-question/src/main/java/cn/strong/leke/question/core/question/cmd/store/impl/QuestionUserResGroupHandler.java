/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionUserResGroupHandler;
import cn.strong.leke.question.dao.mybatis.question.IQuestionUserResGroupDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionUserResGroupHandler implements IQuestionUserResGroupHandler {

	private static final String ERR_NO_GRP = "没有指定分组";
	private static final String ERR_NO_PAPS = "没有指定待分组的试题";

	@Resource
	private IQuestionUserResGroupDao questionUserResGroupDao;

	@Override
	@Transactional
	public void batchAdd(Long userResGroupId, Set<Long> questionIds, User user) {
		Validation.notNull(userResGroupId, ERR_NO_GRP);
		Validation.notEmpty(questionIds, ERR_NO_PAPS);
		questionUserResGroupDao.addBatchQuestionUserResGroup(userResGroupId, questionIds,
				user.getId());
	}

	@Override
	@Transactional
	public void batchRemove(Long userResGroupId, Set<Long> questionIds, User user) {
		Validation.notNull(userResGroupId, ERR_NO_GRP);
		Validation.notEmpty(questionIds, ERR_NO_PAPS);
		questionUserResGroupDao.deleteBatchQuestionUserResGroup(userResGroupId, questionIds,
				user.getId());
	}

	@Override
	@Transactional
	public void batchMove(Long oldGrpId, Long newGrpId, Set<Long> questionIds, User user) {
		Validation.isTrue(oldGrpId != null && newGrpId != null, ERR_NO_GRP);
		if (oldGrpId.equals(newGrpId)) {
			return;
		}
		Validation.notEmpty(questionIds, ERR_NO_PAPS);
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

	private Set<Long> filterNotExist(Long userResGroupId, Set<Long> questionIds) {
		Set<Long> result = new HashSet<>();
		// 批量移动的资源不会超过10条，暂时遍历处理
		for (Long questionId : questionIds) {
			int count = questionUserResGroupDao.queryCount(userResGroupId, questionId);
			if (count == 0) {
				result.add(questionId);
			}
		}
		return result;
	}

}
