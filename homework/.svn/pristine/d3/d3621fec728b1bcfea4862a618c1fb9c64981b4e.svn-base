package cn.strong.leke.homework.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.SubmitInfo;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.model.user.User;
import cn.strong.leke.notice.model.todo.HwAssignEvent;
import cn.strong.leke.remote.model.homework.DoubtRemote;
import cn.strong.leke.remote.model.homework.VodHwAssignRemote;
import cn.strong.leke.remote.model.homework.VodHwAssignRemote.ResInfo;
import cn.strong.leke.remote.service.homework.IHomeworkRemoteService;

@Component
public class HomeworkRemoteService implements IHomeworkRemoteService {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private DoubtService doubtService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkAssignService homeworkAssignService;
	
	@Override
	public List<Long> saveFastAssignHomework(List<Long> classIds, List<Long> paperIds, Date closeTime, User user) {
		return this.homeworkAssignService.saveFastAssign(classIds, paperIds, closeTime, user);
	}

	@Override
	public List<ResInfo> saveAssignHomework(VodHwAssignRemote assignRemote){
		return this.homeworkAssignService.saveAssignHomework(assignRemote);
	}

	@Override
	public void saveStuHomework(User user, List<Long> homeworkIds) {
		this.homeworkAssignService.createStuVodHwWithTx(homeworkIds, user);
	}

	@Override
	public void updateHomeworkTimeByCourseSingle(Long courseSingleId, Date startTime, Date endTime) {
		this.homeworkService.updateHomeworkTimeByCourseSingle(courseSingleId, startTime, endTime);
	}

	@Override
	public List<HwAssignEvent> findHwAssignEventsByCourseSingleId(Long courseSingleId) {
		return this.homeworkService.findHwAssignEventsByCourseSingleId(courseSingleId);
	}

	@Override
	public void saveDoubt(DoubtRemote doubtRemote) {
		Doubt doubt = BeanUtils.copyProperties(Doubt.class, doubtRemote);
		doubt.setSource(Doubt.SOURCE_MINE);
		this.doubtService.saveDoubt(doubt);
	}
	
	@Override
	public Map<String, Integer> findStuSubjResByStudentId(Long studentId, Long subjectId) {
		Map<String, Integer> map = homeworkService.findStuHomeworkInfoTotal(studentId, subjectId);
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		map1.put("bugFixNum", Integer.parseInt(""+(map.get("doingTotal")!=null?map.get("doingTotal"):0)));
		map1.put("submitNum", Integer.parseInt(""+(map.get("bugfixTotal")!=null?map.get("bugfixTotal"):0)));
		return map1;
	}
	
	/**
	 * 统计老师作业数，待批改和待复批数
	 * @param teacherId
	 * @return
	 */
	public Map<String, Long> findTeacherHomeworkInfoTotal(Long teacherId){
		return homeworkService.findTeacherHomeworkInfoTotal(teacherId);
	}
	
	public List<Long> findStuHwDtlId(List<Long> hwIds, Long studentId) {
		return this.homeworkDtlService.findStuHwDtlId(hwIds, studentId);
	}

	@Override
	public List<Long> findSubjectIdFromHomework(Long teacherId, Long schoolId) {
		return this.homeworkService.findSubjectIdFromHomework(teacherId, schoolId);
	}
	
	@Override
	public int getCorrectNumByUserId(Long userId) {
		return this.homeworkService.getCorrectNumByUserId(userId);
	}

	@Override
	public List<SubmitInfo> findStudentSubmitInfoByLessonId(Long lessonId, Long userId) {
		return this.homeworkAssignService.syncStudentBeikeHwDtlWithTx(lessonId, userId);
	}

	@Override
	public Long getPaperIdByHomeworkDtlId(Long homeworkDtlId) {
		return this.homeworkService.getPaperIdByHomeworkDtlId(homeworkDtlId);
	}
}
