/**
 * 
 */
package cn.strong.leke.question.controller.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.QuestionType;

/**
 * 题库外部接口
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/api/w/*")
public class APIWController {

	/**
	 * 根据学科ID获取关联题型
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping("findQuestionTypesBySubjectId")
	@ResponseBody
	public JsonResult findQuestionTypesBySubjectId(String data) {
		QueTypeQuery query = JsonUtils.fromJSON(data, QueTypeQuery.class);
		Validation.notNull(query, "查询数据 data 不能为空");
		Validation.notNull(query.getSubjectId(), "学科ID不能为空");
		List<QuestionType> types = QuestionTypeContext.findQuestionTypesBySubjectId(query
				.getSubjectId());
		JsonResult json = new JsonResult();
		json.addDatas("types", types);
		return json;
	}
}
