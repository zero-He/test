/**
 * 
 */
package cn.strong.leke.monitor.db.tutor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 用户角色学校关联表 Mapper
 * 
 * @author liulongbiao
 *
 */
public interface IUserRoleSchoolMapper {
	/**
	 * 获取学校注册用户数量
	 * 
	 * @param schoolId
	 * @return
	 */
	int countUsersBySchool(Long schoolId);

	/**
	 * 获取学校注册某角色的用户数量
	 * 
	 * @param schoolId
	 * @param roleId
	 * @return
	 */
	int countUsersByRole(@Param("schoolId") Long schoolId, @Param("roleId") Long roleId);

	/**
	 * 获取某个时间后存在更新的学校ID
	 * 
	 * @param since
	 * @return
	 */
	List<Long> findUpdatedSchoolIds(@Param("since") Date since);

	/**
	 * 获取所有学校ID
	 * 
	 * @return
	 */
	List<Long> findAllSchoolIds();
}
