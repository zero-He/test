package cn.strong.leke.homework.service;

import java.util.Date;
import java.util.List;

import cn.strong.leke.beike.model.LessonBeikePkgMQ;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.LayerAssign;
import cn.strong.leke.homework.model.SubmitInfo;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.homework.VodHwAssignRemote;
import cn.strong.leke.remote.model.homework.VodHwAssignRemote.ResInfo;

/**
 * 布置作业服务。
 * @author  andy
 * @since   v1.0.0
 */
public interface HomeworkAssignService {

	/**
	 * 分层作业布置
	 * @param assign
	 */
	public List<Long> saveAssign(LayerAssign assign);

	/**
	 * 快速作业布置
	 * @param classIds 班级ID
	 * @param paperIds 试卷ID
	 * @param closeTime 结束时间，默认后三天
	 * @param user 布置老师
	 * @return
	 */
	public List<Long> saveFastAssign(List<Long> classIds, List<Long> paperIds, Date closeTime, User user);

	/**
	 * 同步学生备课作业，并返回作业的完成情况
	 * @param lessonId
	 * @param userId
	 */
	public List<SubmitInfo> syncStudentBeikeHwDtlWithTx(Long lessonId, Long userId);

	/**
	 * 备课作业布置
	 * @param beikePkgMQ
	 */
	public void executeLessonBeikePkgWithTx(LessonBeikePkgMQ beikePkgMQ);

	/**
	 * 点播作业布置
	 * @caller resource
	 * @param assignRemote
	 * @return
	 */
	public List<ResInfo> saveAssignHomework(VodHwAssignRemote assignRemote);

	/**
	 * 创建学生点播作业
	 * @param homeworkId
	 * @param user
	 * @return
	 */
	public HomeworkDtl resolveStuVodHwWithTx(Long homeworkId, User user);

	/**
	 * 批量创建学生点播作业
	 * @param homeworkIds
	 * @param user
	 */
	public void createStuVodHwWithTx(List<Long> homeworkIds, User user);
}
