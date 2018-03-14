package cn.strong.leke.diag.dao.homework.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.diag.model.UserCount;

public interface DoubtExplainDao {

	/**
	 * 获取学生一段时间内的提问数量。
	 * @param studentId 学生
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public int getStudentDoubtNumByTime(@Param("studentId") Long studentId, @Param("start") Date start,
			@Param("end") Date end);

	/**
	 * 查询学生一段时间内的提问数量
	 * @param userIds
	 * @param start
	 * @param subjectId
	 * @param end
	 * @return
	 */
	public List<UserCount> findUserDoubtNumByTime(@Param("userIds") List<Long> userIds,
			@Param("subjectId") Long subjectId, @Param("start") Date start, @Param("end") Date end);

}
