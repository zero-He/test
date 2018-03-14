package cn.strong.leke.homework.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.CorrectHomeworkDtlInfo;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.service.MutualCorrectionService;
import cn.strong.leke.notice.model.todo.HwChangeCorrectEvent;
import cn.strong.leke.notice.model.todo.HwMutualCorrectEvent;

@Service
public class MutualCorrectionServiceImpl implements MutualCorrectionService {

	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;

	@Override
	public void saveCorrectEachOther(Long homeworkId, Long teacherId) {
		validateMutualCorrection(homeworkId, teacherId);
		List<HomeworkDtl> correctionHwDtls = findCorrectionHomeworkDtls(homeworkId);
		setCorrectUserEach(correctionHwDtls);
		correctionHwDtls.forEach(v -> {
			this.homeworkDtlDao.updateHwDtlCorrectUser(v.getHomeworkDtlId(), v.getCorrectUserId(), teacherId);
		});
		
		sendMutualCorrectEvent(homeworkId, correctionHwDtls);
		
	}

	/**
	 * @param correctionHwDtls
	 */
	private void setCorrectUserEach(List<HomeworkDtl> correctionHwDtls) {
		List<Long> studentIds = correctionHwDtls.stream().map(v->v.getStudentId()).collect(toList());
		if(correctionHwDtls.size() == 1 || correctionHwDtls.size() == 0 || studentIds.size() == 0){
			return;
		}
		Long outUserId = null;
		List<Long> haveCorrects = new ArrayList<>();
		for (HomeworkDtl item : correctionHwDtls) {
			outUserId = item.getStudentId();
			studentIds.remove(outUserId);
			studentIds.removeAll(haveCorrects);
			if(CollectionUtils.isNotEmpty(studentIds)) {
				Collections.shuffle(studentIds);
				Long correctUserId = studentIds.get(0);
				item.setCorrectUserId(correctUserId);
				studentIds.remove(correctUserId);
				haveCorrects.add(correctUserId);
				studentIds.add(outUserId);
			}
		}
		HomeworkDtl last = correctionHwDtls.get(correctionHwDtls.size()-1);
		if(last.getCorrectUserId() == null){
			HomeworkDtl first = correctionHwDtls.get(0);
			last.setCorrectUserId(first.getCorrectUserId());
			first.setCorrectUserId(last.getStudentId());
		}
	}

	/**
	 * @param homeworkId
	 * @param correctionHwDtls
	 */
	private void sendMutualCorrectEvent(Long homeworkId, List<HomeworkDtl> correctionHwDtls) {
		List<HwMutualCorrectEvent.CorrectUserHwDtl> correctUserHwDtls = new ArrayList<HwMutualCorrectEvent.CorrectUserHwDtl>();
		for (HomeworkDtl homeworkDtl : correctionHwDtls) {
			HwMutualCorrectEvent.CorrectUserHwDtl item = new HwMutualCorrectEvent.CorrectUserHwDtl();
			item.setHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
			item.setCorrectUserId(homeworkDtl.getCorrectUserId());
			correctUserHwDtls.add(item);
		}
		HwMutualCorrectEvent event = new HwMutualCorrectEvent();
		Homework homework  = homeworkDao.getHomeworkById(homeworkId);
		event.setCloseTime(homework.getCloseTime());
		event.setHomeworkId(homeworkId);
		event.setHomeworkName(homework.getHomeworkName());
		event.setTeacherId(homework.getTeacherId());
		event.setCorrectUserHwDtls(correctUserHwDtls);
		NoticeHelper.todo(event);
	}

	@Override
	public void saveCorrectUsers(Long homeworkId, Long teacherId, List<Long> userIds) {
		validateMutualCorrection(homeworkId, teacherId);
		List<HomeworkDtl> correctionHwDtls = findCorrectionHomeworkDtls(homeworkId).stream().filter(v->v.getCorrectTime()==null).collect(toList());
		//指定批改人之外的人再平均分配
		setAvgCorrrectUser(userIds, correctionHwDtls);
		
		correctionHwDtls.forEach(v -> {
			this.homeworkDtlDao.updateHwDtlCorrectUser(v.getHomeworkDtlId(), v.getCorrectUserId(), teacherId);
		});
		
		sendMutualCorrectEvent(homeworkId, correctionHwDtls);
	}

	/**
	 * @param userIds
	 * @param correctionHwDtls
	 */
	private void setAvgCorrrectUser(List<Long> userIds, List<HomeworkDtl> correctionHwDtls) {
		correctionHwDtls.forEach(v->v.setCorrectUserId(null));
		int avgTotal = correctionHwDtls.size() / userIds.size();
		for (Long userId : userIds) {
			setCorrectUserId(correctionHwDtls, avgTotal, userId);
		}
		Long extraCount = correctionHwDtls.stream().filter(v->v.getCorrectUserId()==null).count();
		if(extraCount > 0){
			Collections.shuffle(userIds);
			for (Long userId : userIds) {
				setCorrectUserId(correctionHwDtls, 1, userId);
				if(!correctionHwDtls.stream().anyMatch(v->v.getCorrectUserId()==null)){
					break;
				}
			}
		}
	}

	/**
	 * @param correctionHwDtls
	 * @param avgTotal
	 * @param userId
	 */
	private void setCorrectUserId(List<HomeworkDtl> correctionHwDtls, int avgTotal, Long userId) {
		Collections.shuffle(correctionHwDtls);
		correctionHwDtls.stream().filter(v->v.getCorrectUserId() == null && !v.getStudentId().equals(userId)).limit(avgTotal).forEach(v->v.setCorrectUserId(userId));
	}

	@Override
	public void validateMutualCorrection(Long homeworkId, Long teacherId) {
		Homework hw = homeworkDao.getHomeworkById(homeworkId);
		if (hw == null) {
			throw new ValidateException("操作失败，作业已经被删除");
		} else if (!hw.getTeacherId().equals(teacherId)) {
			throw new ValidateException("非法操作");
		} else if (!hw.getSubjective()) {
			throw new ValidateException("homework.correction.subjective");
		} else if (hw.getIsSelfCheck()) {
			throw new ValidateException("homework.correction.stop");
		} else if (hw.getTotalNum() == hw.getCorrectNum()) {
			throw new ValidateException("homework.correction.empty");
		} else if (hw.getTotalNum() - hw.getCorrectNum() < 2) {
			throw new ValidateException("homework.correction.little.student");
		}
	}

	private List<HomeworkDtl> findCorrectionHomeworkDtls(Long homeworkId) {
		List<HomeworkDtl> homeworkDtls = homeworkDtlDao.findHomeworkDtlListByHomeworkId(homeworkId);
		homeworkDtls.removeIf(v -> {
			return v.getCorrectTime() != null;
		});
		return homeworkDtls;
	}
	
	@Override
	public void changeCorrectUserWithTx(Long homeworkDtlId, Long teacherId, Long correctUserId) {
		HomeworkDtl homeworkDtl = this.homeworkDtlDao.getHomeworkDtlById(homeworkDtlId);
		Long oldCorrectUserId = homeworkDtl.getCorrectUserId();
		validateSingleCorrection(teacherId, correctUserId, homeworkDtl);
		homeworkDtl.setCorrectUserId(correctUserId);
		homeworkDtlDao.updateHwDtlCorrectUser(homeworkDtlId, correctUserId, teacherId);
		//变更批改人，待办处理
		HwChangeCorrectEvent event = new HwChangeCorrectEvent();
		event.setCorrectUserId(correctUserId);
		event.setHomeworkDtlId(homeworkDtlId);
		event.setHomeworkId(homeworkDtl.getHomeworkId());
		event.setOldCorrectUserId(oldCorrectUserId);
		Homework homework = homeworkDao.getHomeworkById(homeworkDtl.getHomeworkId());
		event.setHomeworkName(homework.getHomeworkName());
		event.setCloseTime(homework.getCloseTime());
		NoticeHelper.todo(event);
	}

	/**
	 * @param teacherId
	 * @param userId
	 * @param homeworkDtl
	 */
	private void validateSingleCorrection(Long teacherId, Long userId, HomeworkDtl homeworkDtl) {
		Homework homework = this.homeworkDao.getHomeworkById(homeworkDtl.getHomeworkId());
		if (!homework.getTeacherId().equals(teacherId)) {
			throw new ValidateException("非法操作");
		}else if(homeworkDtl.getCorrectTime() !=null){
			throw new ValidateException("操作失败，该份作业已完成批改不能更换");
		}else if(homeworkDtl.getStudentId().equals(userId)){
			throw new ValidateException("操作失败，不能批改自己的作业");
		}
	}
	
	@Override
	public List<CorrectHomeworkDtlInfo> findHomeworkDtlByCorrectUser(Long correctUserId) {
		return this.homeworkDtlDao.findHomeworkDtlInfoByCorrectUser(correctUserId);
	}
	
	@Override
	public List<CorrectHomeworkDtlInfo> findSubmitByHwIds(Long studentId, List<Long> homeworkIds) {
		return this.homeworkDtlDao.findSubmitByHwIds(studentId, homeworkIds);
	}
	
	@Override
	public Long getCorrectTotal(Long correctUserId) {
		return this.homeworkDtlDao.getCorrectTotal(correctUserId);
	}
	
}
