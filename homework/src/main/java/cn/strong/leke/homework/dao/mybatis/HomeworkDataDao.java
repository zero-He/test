package cn.strong.leke.homework.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.homework.model.HomeworkData;

public interface HomeworkDataDao {
	
	/**
	 * 描述: 保存作业数据
	 * @param homeworkData 作业数据
	 */
	public void insertHomeworkData(HomeworkData homeworkData);
	
	/**
	 * 更新目标作业ID
	 * @param dataId
	 * @param targetHomeworkId
	 */
	public void updateTargetHomeworkId(@Param("dataId") Long dataId, @Param("targetHomeworkId") Long targetHomeworkId);

	/**
	 * 描述: 根据数据标识删除作业数据
	 * @param dataId 数据ID
	 */
	public void deleteHomeworkData(Long dataId);

	/**
	 * 根据数据标识，查询作业数据
	 * @param dataId 数据ID
	 * @return
	 */
	public HomeworkData getHomeworkDataByDataId(Long dataId);
	
	public Long getHomeworkDataTotal(@Param("homeworkId") Long homeworkId, @Param("version") Long version);

}
