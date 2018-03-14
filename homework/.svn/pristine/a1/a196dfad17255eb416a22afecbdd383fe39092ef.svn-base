package cn.strong.leke.homework.service;

import java.util.Date;

import cn.strong.leke.homework.model.Homework;

public interface HomeworkSnapshotService {

	/**
	 * 根据单课ID，将单课作业流转到课后阶段。
	 * @param lessonId
	 */
	public void saveFlowAfterClassByLessonId(Long lessonId);

	/**
	 * 保存作业快照，生成下一阶段作业
	 * @param homeworkId 作业ID
	 * @param nextPhase 下一阶段
	 * @param startTime 开始时间
	 * @param closeTime 截止时间
	 * @param retainAnswer 保留答案
	 * @return
	 */
	public Homework saveSnapshotAndNextPhase(Long homeworkId, Integer nextPhase, Date startTime, Date closeTime,
			boolean retainAnswer);
}
