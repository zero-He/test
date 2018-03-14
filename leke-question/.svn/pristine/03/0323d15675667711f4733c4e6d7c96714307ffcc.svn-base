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
import cn.strong.leke.question.dao.mybatis.workbook.IWorkbookUserResGroupDao;
import cn.strong.leke.question.model.workbook.WorkbookUserResGroup;
import cn.strong.leke.question.service.IWorkbookUserResGroupService;
import cn.strong.leke.remote.service.question.IUserResGroupRemoteService;

@Service
public class WorkbookUserResGroupService implements IWorkbookUserResGroupService {

	@Resource
	private IUserResGroupRemoteService userResGroupRemoteService;
	@Resource
	private IWorkbookUserResGroupDao workbookUserResGroupDao;

	@Override
	public void addBatchWorkbookUserResGroup(Long userResGroupId, Set<Long> workbookIds, User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		Validation.notEmpty(workbookIds, "没有指定要分组的习题册");
		Long userId = user.getId();
		Set<Long> set = filterNotExist(userResGroupId, workbookIds);
		if (!set.isEmpty()) {
			workbookUserResGroupDao.addBatchWorkbookUserResGroup(userResGroupId, set, userId);
		}
	}

	private Set<Long> filterNotExist(Long userResGroupId, Set<Long> workbookIds) {
		Set<Long> result = new HashSet<>();
		for (Long workbookId : workbookIds) {
			int count = workbookUserResGroupDao.queryCount(userResGroupId, workbookId);
			if (count == 0) {
				result.add(workbookId);
			}
		}
		return result;
	}

	@Override
	public void saveMoveBatchWUserResGroup(UserResGroupDTO dto, Set<Long> workbookIds,
			User user) {
		if (dto == null || dto.getNewUserResGroupId() == null || dto.getOldUserResGroupId() == null) {
			throw new ValidateException("que.workbook.userResGroup.info.incomplete");
		}
		Long oldGrpId = dto.getOldUserResGroupId();
		Long newGrpId = dto.getNewUserResGroupId();
		if (oldGrpId.equals(newGrpId)) {
			return;
		}
		Validation.notEmpty(workbookIds, "没有指定要移除分组的习题");
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

	@Override
	public void deleteBatchWorkbookUserResGroup(Long userResGroupId, Set<Long> workbookIds,
			User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		Validation.notEmpty(workbookIds, "没有指定要移除分组的习题册");
		Long userId = user.getId();
		workbookUserResGroupDao
				.deleteBatchWorkbookUserResGroup(userResGroupId, workbookIds, userId);
	}

	@Override
	public void deleteWURGroupByUserResGroupId(Long userResGroupId, User user) {
		Validation.notNull(userResGroupId, "没有指定分组");
		userResGroupRemoteService.deleteUserResGroup(userResGroupId, user);
		Long userId = user.getId();
		workbookUserResGroupDao.deleteWURGroupByUserResGroupId(userResGroupId, userId);
	}

	@Override
	public List<WorkbookUserResGroup> queryWURGroupByUserResGroupId(Long userResGroupId, Page page) {
		if (userResGroupId == null) {
			return Collections.emptyList();
		}
		return workbookUserResGroupDao.queryWURGroupByUserResGroupId(userResGroupId, page);
	}

}
