package cn.strong.leke.question.controller.question;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.query.IQuestionLogQueryService;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.question.query.AmountQuestionQuery;

/**
 *
 * 描述: 教研员习题统计
 *
 * @author raolei
 * @created 2016年8月4日 上午10:07:16
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/researcher/question/amount")
public class ResearcherAmountQuestionController {

	@Resource
	private IQuestionQueryService questionQueryService;
	@Resource
	private IQuestionLogQueryService questionLogQueryService;

	/**
	 *
	 * 描述: 录入量统计
	 *
	 * @author raolei
	 * @created 2016年8月4日 上午10:08:16
	 * @since v1.0.0
	 * @return void
	 */
	@RequestMapping("input")
	public void input() {
	}

	@ResponseBody
	@RequestMapping("inputAmount")
	public JsonResult inputAmount(AmountQuestionQuery query) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		Long userId = user.getId();
		query.setUserId(userId);
		// long count = questionQueryService.countInputQuestion(query);
		jResult.addDatas("count", 0);
		return jResult;
	}

	/**
	 *
	 * 描述: 审核量统计
	 *
	 * @author raolei
	 * @created 2016年8月4日 上午10:08:25
	 * @since v1.0.0
	 * @return void
	 */
	@RequestMapping("check")
	public void check() {
	}

	@ResponseBody
	@RequestMapping("checkAmount")
	public JsonResult checkAmount(String dataJson) {
		AmountQuestionQuery query = JsonUtils.fromJSON(dataJson, AmountQuestionQuery.class);
		JsonResult jResult = new JsonResult();
		InputStatisQuery isQuery = new InputStatisQuery();
		Long userId = UserContext.user.getUserId();
		isQuery.setUserId(userId);
		isQuery.setStartDate(query.getMinCreatedOn());
		isQuery.setEndDate(query.getMaxCreatedOn());
		long unchecked = questionQueryService.countUnCheckedQuestionOfLekeBoutique(query);
		long checked = questionLogQueryService.countCheckAmount(isQuery);
		jResult.addDatas("unchecked", unchecked);
		jResult.addDatas("checked", checked);
		return jResult;
	}

}
