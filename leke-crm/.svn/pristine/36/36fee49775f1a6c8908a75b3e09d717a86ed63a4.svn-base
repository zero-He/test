package cn.strong.leke.scs.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.remote.service.user.IParametersRemoteService;
import cn.strong.leke.scs.model.query.PointAccountQuery;
import cn.strong.leke.scs.model.view.PointAccount;
import cn.strong.leke.scs.service.AccountService;
import cn.strong.leke.user.model.parameters.BestowalSetting;
import cn.strong.leke.user.model.parameters.ParameterCsts;

/**
 * 账户管理
 * @author  andy
 * @created 2015年6月17日 下午5:11:11
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/customer")
public class VirtualAccountController {

	@Resource
	private AccountService accountService;
	@Resource
	private IParametersRemoteService parametersRemoteService;

	/**
	 * 点账户管理列表。
	 * @param model
	 */
	@RequestMapping(value = "/virtualAccount/order", method = RequestMethod.GET)
	public String toOrder(Model model ,Long schollId,Float amount,RedirectAttributes redirect) {
		redirect.addAttribute("schollId", schollId);
		redirect.addAttribute("amount", amount);
		return "redirect:www.pay.leke.cn/auth/platformFinance/virtualAccount/order.htm";
	}

	
}
