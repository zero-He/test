package cn.strong.leke.homework.dao.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 作业快照的相关操作
 * @author Zhang Fujun
 * @date 2017年3月27日
 */
public interface HomeworkSnapshotDao {

	/**
	 * 更新作业为镜像
	 * @param homeworkId
	 * @param userId
	 */
	public void updateHomeworkIsSnapshot(@Param("homeworkId") Long homeworkId, @Param("userId") Long userId);

	/**
	 * 满分作业处理，拷贝基本信息+成绩及提交信息
	 * @param newHomeworkId
	 * @param oldHomeworkId
	 * @param closeTime
	 */
	public void insertFullScoreHomeworkDtl(@Param("newHomeworkId") Long newHomeworkId,
			@Param("oldHomeworkId") Long oldHomeworkId, @Param("closeTime") Date closeTime);

	/**
	 * 非满分作业处理（包括未提交），只处理基本信息，得分及提交等情况都不处理。
	 * @param newHomeworkId
	 * @param oldHomeworkId
	 * @param closeTime
	 */
	public void insertNoFullScoreHomeworkDtl(@Param("newHomeworkId") Long newHomeworkId,
			@Param("oldHomeworkId") Long oldHomeworkId, @Param("closeTime") Date closeTime);

	/**
	 * 插入作业并清除成绩。
	 * @param newHomeworkId
	 * @param oldHomeworkId
	 * @param closeTime
	 */
	public void insertClearScoreHomeworkDtl(@Param("newHomeworkId") Long newHomeworkId,
			@Param("oldHomeworkId") Long oldHomeworkId, @Param("closeTime") Date closeTime);

	/**
	 * 更新课件作业为已提交
	 * @param homeworkId 作业ID
	 * @param startTime 开始时间
	 * @param submitTime 提交时间
	 */
	public void updateHomeworkDtlIsFinished(@Param("homeworkId") Long homeworkId, @Param("startTime") Date startTime,
			@Param("submitTime") Date submitTime);

	/**
	 * 查询未满分作业的学生
	 * @param homeworkId
	 * @return
	 */
	public List<Long> findNoFullScoreUserId(@Param("homeworkId")Long homeworkId);

	/**
	 * 查询需要流转到课后的作业ID。
	 * 说明：查询单课下的预习和随堂作业，根据BeikeGuid分组，取ID最大的作业
	 * @param lessonId
	 * @return
	 */
	public List<Long> findFlowAfterClassHomeworkIdsByLessonId(Long lessonId);
}
