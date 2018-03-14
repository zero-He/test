/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookUserResGroupHandler;
import cn.strong.leke.question.dao.mybatis.workbook.IWorkbookUserResGroupDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookUserResGroupHandler implements IWorkbookUserResGroupHandler {

	private static final String ERR_NO_GRP = "没有指定分组";
	private static final String ERR_NO_WBS = "没有指定待分组的试题册";

	@Resource
	private IWorkbookUserResGroupDao workbookUserResGroupDao;

	@Override
	@Transactional
	public void batchAdd(Long userResGroupId, Set<Long> workbookIds, User user) {
		Validation.notNull(userResGroupId, ERR_NO_GRP);
		Validation.notEmpty(workbookIds, ERR_NO_WBS);
		workbookUserResGroupDao.addBatchWorkbookUserResGroup(userResGroupId, workbookIds,
				user.getId());
	}

	@Override
	@Transactional
	public void batchRemove(Long userResGroupId, Set<Long> workbookIds, User user) {
		Validation.notNull(userResGroupId, ERR_NO_GRP);
		Validation.notEmpty(workbookIds, ERR_NO_WBS);
		workbookUserResGroupDao.deleteBatchWorkbookUserResGroup(userResGroupId, workbookIds,
				user.getId());
	}

	@Override
	@Transactional
	public void batchMove(Long oldGrpId, Long newGrpId, Set<Long> workbookIds, User user) {
		Validation.isTrue(oldGrpId != null && newGrpId != null, ERR_NO_GRP);
		if (oldGrpId.equals(newGrpId)) {
			return;
		}
		Validation.notEmpty(workbookIds, ERR_NO_WBS);
		Long userId = user.getId();
		Set<Long> moveSet = filterNotExist(newGrpId, workbookIds);
		Set<Long> removeSet = Sets.difference(workbookIds, moveSet);
		if (!removeSet.isEmpty()) {
			workbookUserResGroupDao.deleteBatchWorkbookUserResGroup(oldGrpId, removeSet, userId);
		}
		if (!moveSet.isEmpty()) {
			workbookUserResGroupDao.moveBatchWorkbookUserResGroup(oldGrpId, newGrpId, moveSet,
					userId);
		}
	}

	private Set<Long> filterNotExist(Long userResGroupId, Set<Long> workbookIds) {
		Set<Long> result = new HashSet<>();
		// 批量移动的资源不会超过10条，暂时遍历处理
		for (Long workbookId : workbookIds) {
			int count = workbookUserResGroupDao.queryCount(userResGroupId, workbookId);
			if (count == 0) {
				result.add(workbookId);
			}
		}
		return result;
	}

}
