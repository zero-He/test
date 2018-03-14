package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.UserResGroup;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.IUserResGroupDao;
import cn.strong.leke.question.service.IUserResGroupService;

/**
 *
 * 描述:
 *
 * @author raolei
 * @created 2016年4月13日 上午9:22:40
 * @since v1.0.0
 */
@Service
public class UserResGroupServiceImpl implements IUserResGroupService {
	@Resource
	private IUserResGroupDao userResGroupDao;

	@Override
	public void insertUserResGroup(UserResGroup userResGroup, User user) {
		if (userResGroup == null || StringUtils.isEmpty(userResGroup.getGroupName())) {
			throw new ValidateException("que.userResGroup.info.incomplete");
		}
		Long userId = user.getId();
		userResGroup.setUserId(userId);
		userResGroup.setCreatedBy(userId);
		userResGroup.setModifiedBy(userId);
		userResGroupDao.insertUserResGroup(userResGroup);
	}

	@Override
	public void deleteUserResGroup(Long userResGroupId, User user) {
		if (userResGroupId == null) {
			throw new ValidateException("que.userResGroup.info.incomplete");
		}
		UserResGroup userResGroup=new UserResGroup();
		Long userId = user.getId();
		userResGroup.setModifiedBy(userId);
		userResGroup.setCreatedBy(userId);
		userResGroup.setUserResGroupId(userResGroupId);
		userResGroupDao.deleteUserResGroup(userResGroup);
	}

	@Override
	public void updateURGroupName(UserResGroup userResGroup, User user) {
		if (userResGroup == null || userResGroup.getUserResGroupId() == null
				|| StringUtils.isEmpty(userResGroup.getGroupName())) {
			throw new ValidateException("que.userResGroup.info.incomplete");
		}
		Long userId = user.getId();
		userResGroup.setModifiedBy(userId);
		userResGroup.setUserId(userId);
		userResGroupDao.updateURGroupName(userResGroup);
	}

	@Override
	public List<UserResGroup> findURGroupByUserIdAndResType(Long userId, Integer resType) {
		if (userId == null || resType == null) {
			return Collections.emptyList();
		}
		return userResGroupDao.findURGroupByUserIdAndResType(userId, resType);
	}

}
