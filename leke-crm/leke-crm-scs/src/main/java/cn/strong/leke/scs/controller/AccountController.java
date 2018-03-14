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
@RequestMapping("/auth/scs/account")
public class AccountController {

	@Resource
	private AccountService accountService;
	@Resource
	private IParametersRemoteService parametersRemoteService;

	/**
	 * 点账户管理列表。
	 * @param model
	 */
	@RequestMapping(value = "/pointlist", method = RequestMethod.GET)
	public void pointlist(Model model) {

	}

	/**
	 * 点账户管理列表数据。
	 * @param query 条件
	 * @param page 分页
	 * @param model
	 */
	@ResponseBody
	@RequestMapping(value = "/pointlist", method = RequestMethod.POST)
	public JsonResult pointlist(PointAccountQuery query, Page page) {
		List<PointAccount> list = this.accountService.findPointAccountByQuery(query, page);
		page.setDataList(list);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 赠送点设置页面。
	 * @param model
	 */
	@RequestMapping(value = "/bestowal", method = RequestMethod.GET)
	public void bestowal(Model model) {
		BestowalSetting bestowalSetting = ParametersContext.getObject(ParameterCsts.ACCOUNT_POINT_BESTOWAL,
				BestowalSetting.class);
		model.addAttribute("bestowalSetting", bestowalSetting);
	}

	/**
	 * 保存赠送点设置
	 * @param value 设置值
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/bestowal", method = RequestMethod.POST)
	public String bestowal(@ModelAttribute BestowalSetting bestowalSetting, RedirectAttributes attributes) {
		this.parametersRemoteService.setObject(ParameterCsts.ACCOUNT_POINT_BESTOWAL, bestowalSetting);
		attributes.addFlashAttribute("message", "赠送点设置成功");
		return "redirect:pointlist.htm";
	}
}
