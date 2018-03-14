package cn.strong.leke.homework.service.impl;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mybatis.AssignLogDao;
import cn.strong.leke.homework.model.AssignLogDO;
import cn.strong.leke.homework.model.AssignLogDTO;
import cn.strong.leke.homework.model.AssignLogTeaHw;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.LayerAssign;
import cn.strong.leke.homework.model.LayerAssign.Section;
import cn.strong.leke.homework.model.LayerClazz;
import cn.strong.leke.homework.model.query.AssignLogQueryDTO;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.IAssignLogService;

/**
 * 代人布置作业
 * @author Zhang Fujun
 * @date 2017年3月24日
 */
@Service
public class AssignLogServiceImpl implements IAssignLogService {

	@Resource
	private AssignLogDao assignLogDao;
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkAssignService homeworkAssignService;

	public void addAssignLog(AssignLogDTO assignLogDTO) {
		// 生成布置记录基本数据
		AssignLogDO assignLog = new AssignLogDO();
		assignLog.setCreatedBy(assignLogDTO.getTeacherId());
		assignLog.setCloseTime(assignLogDTO.getCloseTime());
		assignLog.setStartTime(assignLogDTO.getStartTime());
		String classInfo = assignLogDTO.getAssignLogTeaHw().stream().map(AssignLogTeaHw::getTeacherClassName)
				.collect(joining("；"));
		assignLog.setClassInfo(classInfo);
		// 生成布置作业数据结构
		List<Section> sections = assignLogDTO.getAssignLogTeaHw().stream().map(teaHw -> {
			Section section = new Section();
			section.setTeacherId(teaHw.getTeacherId());
			section.setTeacherName(teaHw.getTeacherName());
			section.setResources(assignLogDTO.getResources());
			section.setClasses(JsonUtils.readList(teaHw.getStudentGroupsJson(), LayerClazz.class));
			return section;
		}).collect(toList());
		LayerAssign assign = new LayerAssign();
		assign.setTeacherId(assignLogDTO.getTeacherId());
		assign.setTeacherName(assignLogDTO.getTeacherName());
		assign.setSchoolId(assignLogDTO.getSchoolId());
		assign.setStartTime(assignLogDTO.getStartTime());
		assign.setCloseTime(assignLogDTO.getCloseTime());
		assign.setOpenAnswerTime(assignLogDTO.getOpenAnswerTime());
		assign.setIsExam(false);
		assign.setSections(sections);
		assign.setAssignLog(assignLog);
		// 调用接口布置作业
		this.homeworkAssignService.saveAssign(assign);
	}

	@Override
	public void modifyCloseTime(AssignLogDO assignLog) {
		assignLogDao.modifyCloseTime(assignLog);
		String homeworkIds = assignLogDao.getAssignLog(assignLog.getAssignId()).getHomeworkIdJson();
		String[] homeworkIdArray = homeworkIds.split(",");
		for (String hwId : homeworkIdArray) {
			homeworkService.updateHomeworkDate(Long.parseLong(hwId), assignLog.getStartTime(),
					assignLog.getCloseTime());
		}
	}

	@Override
	public void invalid(AssignLogDO assignLog) {
		assignLogDao.updateStatus(assignLog);
		String homeworkIds = assignLogDao.getAssignLog(assignLog.getAssignId()).getHomeworkIdJson();
		String[] homeworkIdArray = homeworkIds.split(",");
		for (String hwId : homeworkIdArray) {
			Homework hw = homeworkService.getHomeworkById(new Long(hwId));
			if (!hw.getStatus().equals(2)) {
				homeworkService.updateHomeworkInvalid(Long.parseLong(hwId), null);
			}
		}
	}

	@Override
	public List<AssignLogDO> findAssignLogList(AssignLogQueryDTO query, Page page) {
		return assignLogDao.findAssignLogList(query, page);
	}

	/**获取assignLog
	 * @param assignId
	 * @return
	 */
	@Override
	public AssignLogDO getAssignLog(Long assignId) {
		return assignLogDao.getAssignLog(assignId);
	}
}
