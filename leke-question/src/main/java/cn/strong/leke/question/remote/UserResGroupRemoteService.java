package cn.strong.leke.question.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.UserResGroup;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.IUserResGroupService;
import cn.strong.leke.remote.service.question.IUserResGroupRemoteService;

@Service
public class UserResGroupRemoteService implements IUserResGroupRemoteService {

	@Resource
	IUserResGroupService userResGroupService;

	@Override
	public void insertUserResGroup(UserResGroup userResGroup, User user) {
		userResGroupService.insertUserResGroup(userResGroup, user);

	}

	@Override
	public void deleteUserResGroup(Long userResGroupId, User user) {
		userResGroupService.deleteUserResGroup(userResGroupId, user);

	}

	@Override
	public void updateURGroupName(UserResGroup userResGroup, User user) {
		userResGroupService.updateURGroupName(userResGroup, user);

	}

	@Override
	public List<UserResGroup> findURGroupByUserIdAndResType(Long userId, Integer resType) {
		return userResGroupService.findURGroupByUserIdAndResType(userId, resType);
	}

}
