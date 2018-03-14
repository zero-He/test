/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IPersonalWorkbookHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.workbook.IWorkbookUserResGroupDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class PersonalWorkbookHandler implements IPersonalWorkbookHandler {
	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";
	@Resource
	private IWorkbookDao workbookDao;
	@Resource
	private IWorkbookUserResGroupDao workbookUserResGroupDao;

	@Override
	@Transactional
	public void add(Long workbookId, Long userId, User user) {
		Validation.notNull(workbookId, "que.workbook.info.incomplete");
		Workbook workbook = workbookDao.getWorkbook(workbookId);
		Validation.notNull(workbook, ERR_NOT_EXIST);
		Validation.isTrue(workbook.getCreatedBy().equals(userId), "不是自己的资源，不能加入到个人库");
		workbookDao.addToPersonal(workbookId, userId, user.getId());
	}

	@Override
	@Transactional
	public int remove(Long workbookId, Long userId, User user) {
		return workbookDao.removeFromPersonal(workbookId, userId, user.getId());
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, Long userId, User user) {
		Validation.isTrue(contains(oldId, userId), "该资源不是自己的资源");
		remove(oldId, userId, user);
		add(newId, userId, user);

		// process userResGroupId
		Set<Long> oldIds = newHashSet(oldId);
		Set<Long> newIds = newHashSet(newId);
		workbookUserResGroupDao.findByWorkbookAndUser(oldId, userId).forEach(assoc -> {
			Long grpId = assoc.getUserResGroupId();
			workbookUserResGroupDao.deleteBatchWorkbookUserResGroup(grpId, oldIds, userId);
			workbookUserResGroupDao.addBatchWorkbookUserResGroup(grpId, newIds, userId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contains(Long workbookId, Long userId) {
		if (workbookId == null) {
			return false;
		}
		Workbook workbook = workbookDao.getWorkbook(workbookId);
		Validation.notNull(workbook, ERR_NOT_EXIST);
		Boolean sharePersonal = workbook.getSharePersonal();
		return workbook.getCreatedBy().equals(userId) && sharePersonal != null
				&& sharePersonal.equals(true);
	}

}
