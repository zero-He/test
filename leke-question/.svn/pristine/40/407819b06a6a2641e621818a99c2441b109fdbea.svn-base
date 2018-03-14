package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.UserResGroup;
import cn.strong.leke.model.user.User;

/**
 *
 * 描述:
 *
 * @author raolei
 * @created 2016年4月13日 上午9:22:23
 * @since v1.0.0
 */
public interface IUserResGroupService {

	void insertUserResGroup(UserResGroup userResGroup, User user);

	void deleteUserResGroup(Long userResGroupId, User user);

	void updateURGroupName(UserResGroup userResGroup, User user);

	List<UserResGroup> findURGroupByUserIdAndResType(Long userId, Integer resType);
}
