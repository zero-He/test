package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.UserResGroup;

/**
 *
 * 描述: 个人资源分组表
 *
 * @author raolei
 * @created 2016年4月12日 下午5:13:23
 * @since v1.0.0
 */
public interface IUserResGroupDao {

	int insertUserResGroup(UserResGroup userResGroup);

	int deleteUserResGroup(UserResGroup userResGroup);

	int updateURGroupName(UserResGroup userResGroup);

	List<UserResGroup> findURGroupByUserIdAndResType(@Param("userId") Long userId,
			@Param("resType") Integer resType);

}
