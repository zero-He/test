package cn.strong.leke.question.core.question.query.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.query.IFavoriteQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.question.ITeacherQuestionDao;

@Service
public class FavoriteQuestionQueryService implements IFavoriteQuestionQueryService {
	@Resource
	private ITeacherQuestionDao teacherQuestionDao;

	@Override
	public boolean countFavorite(Long questionId,Long userId){
		return teacherQuestionDao.countFavorite(questionId, userId) > 0;
	}

	@Override
	public List<Long> filterFavoriteQuestions(List<Long> questionIds, User user) {
		if (CollectionUtils.isEmpty(questionIds)) {
			return Collections.emptyList();
		}
		Long userId = user.getId();
		return teacherQuestionDao.filterFavoriteQuestions(questionIds, userId);
	}

}
