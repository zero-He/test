package cn.strong.leke.homework.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.homework.manage.FileDescService;
import cn.strong.leke.homework.model.mobile.FileDesc;

/**
 * 手机上资源预览。
 */
@Controller
@RequestMapping("/auth/m/*")
public class MPreviewController {

	@Resource
	private FileDescService fileDescService;

	/**
	 * 课件预览
	 */
	@RequestMapping("/common/courseware/preview")
	public String coursewarePreview(Long coursewareId, Model model) {
		CoursewareDTO courseware = CoursewareContext.getCourseware(coursewareId);
		Map<String, Object> workModel = Maps.newHashMap();
		workModel.put("fileId", courseware.getFileId());
		model.addAttribute("title", courseware.getName());
		model.addAttribute("Csts", JsonUtils.toJSON(workModel));
		return "/auth/m/common/file-preview";
	}

	/**
	 * 微课预览
	 */
	@RequestMapping("/common/microcourse/preview")
	public String microcoursePreview(Long microcourseId, Model model) {
		MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(microcourseId);
		FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
		if (microcourse.getMicrocourseType() == 1 ) {
			throw new ValidateException("录制微课暂不支持播放");
		}
		Map<String, Object> workModel = Maps.newHashMap();
		workModel.put("isNewVod", microcourse.getMicrocourseType() == 3);
		workModel.put("micId", microcourse.getMicrocourseId());
		workModel.put("micName", microcourse.getMicrocourseName());
		workModel.put("file", fileDesc);
		model.addAttribute("title", microcourse.getMicrocourseName());
		model.addAttribute("Csts", JsonUtils.toJSON(workModel));
		return "/auth/m/common/file-preview";
	}

	/**
	 * 试卷预览（暂未实现）
	 */
	@RequestMapping("/common/paper/preview")
	public String paperPreview(Long paperId, Model model) {
		// wait ...
		return null;
	}
}
