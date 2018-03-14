/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IFavoriteWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IPersonalWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.workbook.ITeacherWorkbookDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class FavoriteWorkbookHandler implements IFavoriteWorkbookHandler {

	private static final String ERR_FAVED = "习题册已收藏";
	private static final String ERR_OWN = "不能收藏自己的习题册";
	@Resource
	private ITeacherWorkbookDao teacherWorkbookDao;
	@Resource
	private IWorkbookDao workbookDao;
	@Resource
	private IPersonalWorkbookHandler personalWorkbookHandler;

	@Override
	@Transactional
	public void add(Long workbookId, Long userId, User user) {
		Validation.isFalse(personalWorkbookHandler.contains(workbookId, userId), ERR_OWN);
		Validation.isFalse(contains(workbookId, userId), ERR_FAVED);

		teacherWorkbookDao.addFavorite(workbookId, userId);

		workbookDao.incFavCount(workbookId);
	}

	@Override
	@Transactional
	public int remove(Long workbookId, Long userId, User user) {
		return teacherWorkbookDao.deleteFavorites(new Long[] { workbookId }, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contains(Long workbookId, Long userId) {
		if (workbookId == null) {
			return false;
		}
		int count = teacherWorkbookDao.countFavorite(workbookId, userId);
		return count > 0;
	}
}
