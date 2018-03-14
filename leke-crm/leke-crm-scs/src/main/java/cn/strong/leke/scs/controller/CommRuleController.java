package cn.strong.leke.scs.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.scs.model.CommRule;
import cn.strong.leke.scs.model.rule.PointCommRule;
import cn.strong.leke.scs.service.CommRuleService;
import cn.strong.leke.scs.util.CommRuleUtils;
import cn.strong.leke.scs.util.CrmCsts;

/**
 * 佣金规则管理控制器。
 * @author  andy
 * @created 2015年6月15日 下午2:51:31
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/commrule")
public class CommRuleController {

	@Resource
	private CommRuleService commRuleService;

	/**
	 * 点充值佣金规则设置页面。
	 * @param model
	 */
	@RequestMapping("/pointedit")
	public void pointedit(Model model) {
		List<CommRule> commRuleList = this.commRuleService.findNearestTwoCommRules(CrmCsts.COMM_TYPE_POINT);
		CommRule commRule = commRuleList.get(0);
		if (commRule.getValidTime().before(new Date())) {
			// 最新规则已生效
			model.addAttribute("currentCommRule", commRuleList.get(0));
			model.addAttribute("currentDetail", CommRuleUtils.parsePointCommRuleDetail(commRuleList.get(0).getDetail()));
		} else {
			// 最新规则未生效
			model.addAttribute("futureCommRule", commRuleList.get(0));
			model.addAttribute("futureDetail", CommRuleUtils.parsePointCommRuleDetail(commRuleList.get(0).getDetail()));
			model.addAttribute("currentCommRule", commRuleList.get(1));
			model.addAttribute("currentDetail", CommRuleUtils.parsePointCommRuleDetail(commRuleList.get(1).getDetail()));
		}
	}

	/**
	 * 点充值佣金规则保存功能。
	 * @param rates 规则明细
	 * @param validTime 过期时间
	 * @return
	 */
	@RequestMapping("/pointsave")
	public String pointsave(@RequestParam("rates") List<BigDecimal> rates, @RequestParam("validTime") Date validTime,
			RedirectAttributes redirectAttributes) {
		Long userId = UserContext.user.getUserId();

		// 生成点充值佣金规则明细
		PointCommRule pointCommRule = new PointCommRule();
		pointCommRule.setRates(rates);

		// 生成变更信息
		CommRule commRule = new CommRule();
		commRule.setCommType(CrmCsts.COMM_TYPE_POINT);
		commRule.setDetail(JsonUtils.toJSON(pointCommRule));
		commRule.setValidTime(validTime);
		commRule.setCreatedBy(userId);
		commRule.setModifiedBy(userId);

		// 提交变更
		this.commRuleService.saveChangeCommRule(commRule);
		redirectAttributes.addFlashAttribute("message", "变更规则成功");

		return "redirect:pointedit.htm";
	}

}
