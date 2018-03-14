/**
 * 
 */
package cn.strong.leke.monitor.core.utils;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.core.cas.SessionCst;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.model.user.Role;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.monitor.mongo.online.model.DeviceCst;

/**
 * 会话帮助类
 * 
 * @author liulongbiao
 *
 */
public class SessionHelper {

	/**
	 * 根据用户 ID 获取该用户当前登录角色ID
	 * 
	 * @param userId
	 * @param d
	 *            设备类型
	 * @return
	 */
	public static Long getCurrentRoleId(String userId, int d) {
		if (d == DeviceCst.D_DESKTOP) {
			return getRoleIdFromUserBase(userId);
		} else {
			User u = getUserInSession(userId, d);
			return getRoleId(u);
		}
	}

	private static Long getRoleIdFromUserBase(String userId) {
		UserBase user = UserBaseContext.getUserBaseByUserId(Long.parseLong(userId));
		if (user == null) {
			return null;
		}
		RoleSchool rs = CollectionUtils.getFirst(user.getRoleSchoolList());
		return rs == null ? null : rs.getRoleId();
	}

	private static Long getRoleId(User user) {
		if (user == null) {
			return null;
		}
		Role role = user.getCurrentRole();
		return role == null ? null : role.getId();
	}

	/**
	 * 获取用户会话信息
	 * 
	 * @param userId
	 * @param d
	 * @return
	 */
	private static User getUserInSession(String userId, int d) {
		User u = null;
		switch (d) {
		case DeviceCst.D_PHONE:
			u = getCacheUser(SessionCst.SESSION_PREFIX_M, userId);
			break;
		case DeviceCst.D_PAD:
			u = getCacheUser(SessionCst.SESSION_PREFIX_HD, userId);
			break;
		}
		if (u == null) {
			u = getCacheUser(SessionCst.SESSION_PREFIX, userId);
		}
		return u;
	}

	/**
	 * 获取缓存中的用户信息
	 * 
	 * @param prefix
	 * @param userId
	 * @return
	 */
	private static User getCacheUser(String prefix, String userId) {
		return (User) CacheUtils.hget(prefix + userId, SessionCst.SESSION_ATTRIBUTE_USER_POJO);
	}
}
