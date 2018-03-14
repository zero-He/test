package cn.strong.leke.question.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.question.model.QueSubjectQuestionTypeDTO;
import cn.strong.leke.question.model.QuestionTypeSub;
import cn.strong.leke.question.service.IQueSubjectQuestionTypeService;
import cn.strong.leke.question.service.IQuestionTypeSubService;
import cn.strong.leke.question.service.QuestionTypeService;

/**
 *
 * 描述:学科和题型的关联
 *
 * @author raolei
 * @created 2015年8月18日 上午9:30:59
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/admin/questionType/*")
public class QuestionTypeController {
	@Resource
	private QuestionTypeService questionTypeService;
	@Resource
	private IQueSubjectQuestionTypeService iQueSubjectQuestionTypeService;
	@Resource
	private IQuestionTypeSubService questionTypeSubService;

	@RequestMapping("setSubjectQuestion")
	public void setSubjectQuestion(Model model) {
		model.addAttribute("subjects", SubjectContext.findSubjects());
		model.addAttribute("types", questionTypeService.findAllQuestionTypes());
	}

	@ResponseBody
	@RequestMapping("findAllSubjectQuestionTypeMap")
	public JsonResult findAllSubjectQuestionTypeMap() {
		JsonResult jResult = new JsonResult();
		Map<String, List<QuestionType>> maps = questionTypeService
				.findAllSubjectQuestionTypeMap();
		List<String> questions = new ArrayList<String>();
		for (Map.Entry<String, List<QuestionType>> entry : maps.entrySet()) {
			String key = entry.getKey();
			for (QuestionType q : entry.getValue()) {
				questions.add(key + "_" + q.getQuestionTypeId());
			}
		}
		jResult.addDatas("list", questions);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("doSetSubjectQuestion")
	public JsonResult doSetSubjectQuestion(Long subjectId,
			String questionTypeIds) {
		JsonResult jResult = new JsonResult();
		String[] arr = questionTypeIds.split(",");
		List<QueSubjectQuestionTypeDTO> list = new ArrayList<QueSubjectQuestionTypeDTO>();
		for (int i = 0; i < arr.length; i++) {
			if (!StringUtils.isEmpty(arr[i])) {
				QueSubjectQuestionTypeDTO qstdDto = new QueSubjectQuestionTypeDTO();
				qstdDto.setSubjectId(subjectId);
				qstdDto.setQuestionTypeId(Long.valueOf(arr[i]));
				qstdDto.setOrd(i);
				list.add(qstdDto);
			}
		}
		iQueSubjectQuestionTypeService.saveSubjectQuestion(subjectId, list);
		return jResult;
	}

	@RequestMapping("list")
	public void list() {
	}

	@ResponseBody
	@RequestMapping("findAllQuestionTypes")
	public String findAllQuestionTypes() {
		List<QuestionType> questionTypes = questionTypeService
				.findQuestionTypesByHasSub(true);
		return JsonUtils.toJSON(questionTypes);
	}

	@RequestMapping("editQuestionTypeSub")
	public void editQuestionTypeSub(QuestionType queType, Model model) {
		if (queType == null || queType.getQuestionTypeId() == null) {
			throw new ValidateException("题型参数错误！");
		}
		String questionTypeName = queType.getQuestionTypeName();
		try {
			questionTypeName = URLDecoder.decode(questionTypeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		queType.setQuestionTypeName(questionTypeName);
		Long questionTypeId = queType.getQuestionTypeId();
		List<QuestionType> queTypes = questionTypeService
				.findQuestionTypesByHasSub(false);
		List<QuestionType> queTypeSubs = questionTypeService
				.findSubTypesByQuestionTypeId(questionTypeId);
		model.addAttribute("queType", JsonUtils.toJSON(queType));
		model.addAttribute("queTypes", JsonUtils.toJSON(queTypes));
		model.addAttribute("queTypeSubs", JsonUtils.toJSON(queTypeSubs));
	}
	
	@ResponseBody
	@RequestMapping("addQuestionTypeSub")
	public JsonResult addQuestionTypeSub(String dataJson) {
		QuestionTypeSub queTypeSub = JsonUtils.fromJSON(dataJson,
				QuestionTypeSub.class);
		questionTypeSubService.saveQueTypeSubs(queTypeSub);
		return new JsonResult();
	}
}
