package cn.strong.leke.question.core.workbook.query.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.query.IFavoriteWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.workbook.ITeacherWorkbookDao;

@Service
public class FavoriteWorkbookQueryService implements IFavoriteWorkbookQueryService {

	@Resource
	private ITeacherWorkbookDao teacherWorkbookDao;

	@Override
	public List<Long> filterFavoriteWorkbooks(List<Long> workbookIds, User user) {
		if (CollectionUtils.isEmpty(workbookIds)) {
			return Collections.emptyList();
		}
		Long userId = user.getId();
		return teacherWorkbookDao.filterFavoriteWorkbooks(workbookIds, userId);
	}

}
