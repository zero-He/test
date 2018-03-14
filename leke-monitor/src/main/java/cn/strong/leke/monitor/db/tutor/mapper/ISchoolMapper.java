/**
 * 
 */
package cn.strong.leke.monitor.db.tutor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.monitor.db.tutor.model.NatureSchoolCount;

/**
 * 学校DB Mapper
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolMapper {

	/**
	 * 获取所有学校数量
	 * 
	 * @return
	 */
	int count();

	/**
	 * 按学校性质查询学校数量(排除测试学校)
	 * 
	 * @return
	 */
	List<NatureSchoolCount> countByNature();

	/**
	 * 获取时间段内新增学校数量
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	int countAdded(@Param("start") Date start, @Param("end") Date end);

	/**
	 * 按学校性质查询新值学校数量(排除测试学校)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	List<NatureSchoolCount> countAddedByNature(@Param("start") Date start, @Param("end") Date end);
}
