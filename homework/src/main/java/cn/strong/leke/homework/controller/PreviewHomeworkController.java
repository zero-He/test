/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.question.KnowledgeTreeContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.FileDescService;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.model.HomeworkDTO;
import cn.strong.leke.homework.model.HomeworkQuery;
import cn.strong.leke.homework.model.mobile.FileDesc;
import cn.strong.leke.homework.model.mongo.HolidayHwMicro;
import cn.strong.leke.homework.model.mongo.HolidayMicrocourse;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.model.user.User;

import com.google.common.collect.Maps;

/**
 * 预习检查
 * @author  DuanYanming
 * @created 2014年11月6日 下午1:38:07
 * @since   v1.0.0
 */
@Controller()
@RequestMapping("/auth/*")
public class PreviewHomeworkController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private FileDescService fileDescService;
	@Resource
	private HolidayHwMicroService holidayHwMicroService;
	

	@RequestMapping("teacher/preview//previewList")
	public void homeworkList() {

	}

	/**
	 * 加载预习作业
	 */
	@RequestMapping("teacher/preview//loadHomeworkList")
	@ResponseBody
	public JsonResult loadHomeworkList(HomeworkQuery homework, Page page) {
		User user = UserContext.user.get();
		homework.setTeacherId(user.getId());
		homework.setSchoolId(user.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = homeworkService.findHomeworkList(homework, page);
		page.setDataList(homeworkList);

		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	
	@RequestMapping(value = { "teacher/preview/viewMicro","classTeacher/preview/viewMicro","parent/preview/viewMicro", "student/preview/viewMicro" })
	public String viewMicro(Model model, String id, Long microcourseId) throws UnsupportedEncodingException {
		MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(microcourseId);
		FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
		Map<String, Object> Csts = Maps.newHashMap();
		HolidayHwMicro holidayHwMicro = this.holidayHwMicroService.getSingleHolidayHomework(id);
		HolidayMicrocourse holidayMicro = holidayHwMicro.getMicrocourses().stream().filter(v->v.getMicroId().equals(microcourseId)).findFirst().get();
		Integer index = 0;
		for (int i = 0; i< holidayHwMicro.getMicrocourses().size(); i++) {
			if (holidayHwMicro.getMicrocourses().get(i).getMicroId().equals(holidayMicro.getMicroId())) {
				index = i;
				break;
			}
		}
		if(index + 1 < holidayHwMicro.getTotal()){
			HolidayMicrocourse nexHolidayMicro = holidayHwMicro.getMicrocourses().get(index + 1);
			Csts.put("nexMicroId", nexHolidayMicro.getMicroId());
		}
		Csts.put("file", fileDesc);
		Csts.put("position", holidayMicro.getPosition() == null ?0:holidayMicro.getPosition());
		Csts.put("isPlayEnd", holidayMicro.getMicroState()==1);
		Csts.put("exerciseId", holidayMicro.getExerciseId());
		Csts.put("subjectId", holidayHwMicro.getSubjectId());
		Csts.put("subjectName", holidayHwMicro.getSubjectName());
		Csts.put("knowledgeId", holidayMicro.getKnowledgId());
		Csts.put("knowledgeName", KnowledgeTreeContext.get(holidayMicro.getKnowledgId()).getKnowledgeName());
		Csts.put("holidayId", id);
		Csts.put("microId", microcourseId);
		model.addAttribute("Csts", JsonUtils.toJSON(Csts));
		return "/auth/common/microcourse/view";
	}
}
