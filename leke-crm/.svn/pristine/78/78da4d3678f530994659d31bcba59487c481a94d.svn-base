package cn.strong.leke.scs.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.scs.model.Commission;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.query.MonthCommDtlQuery;
import cn.strong.leke.scs.model.query.MonthCommQuery;
import cn.strong.leke.scs.model.view.MonthComm;
import cn.strong.leke.scs.model.view.MonthCommDtl;
import cn.strong.leke.scs.service.CommissionService;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.scs.service.SellerService;
import cn.strong.leke.scs.util.CommRuleUtils;

/**
 * 提成管理控制器。
 * @author  andy
 * @created 2015年6月17日 下午4:30:49
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/commission")
public class CommissionController {

	@Resource
	private SellerService sellerService;
	@Resource
	private CommissionService commissionService;
	@Resource
	private CustomerService customerService;

	@InitBinder
	public void initBind(DataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM"), true, 7));
	}

	/**
	 * 财务人员系统佣金管理页面。
	 * @param model
	 */
	@RequestMapping(value = "/commlist", method = RequestMethod.GET)
	public void commlist(Model model) {
		model.addAttribute("month", DateUtils.format("yyyy-MM"));
	}

	/**
	 * 财务人员系统佣金管理列表数据。
	 * @param query 查询条件
	 * @param page 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commlist", method = RequestMethod.POST)
	public JsonResult commlist(MonthCommQuery query, Page page) {
		if (query.getMonth() == null) {
			query.setMonth(org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.MONTH));
		}
		List<MonthComm> monthCommList = this.commissionService.findMonthCommByQuery(query, page);
		BigDecimal totalAmount = this.commissionService.getTotalMonthCommByQuery(query);
		page.setDataList(monthCommList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		json.addDatas("total", totalAmount);
		return json;
	}

	/**
	 * 财务人员客户经理佣金管理页面。
	 * @param sellerId 销售ID
	 * @param month 月份
	 * @param model
	 */
	@RequestMapping(value = "/commdtllist", method = RequestMethod.GET)
	public void commdtllist(Long sellerId, Date month, Model model) {
		UserBase userBase = UserBaseContext.getUserBaseByUserId(sellerId);
		model.addAttribute("sellerId", sellerId);
		model.addAttribute("sellerName", userBase.getUserName());
		model.addAttribute("month", DateUtils.format(month, "yyyy-MM"));
		model.addAttribute("monthView", DateUtils.format(month, "yyyy年MM月"));
	}

	/**
	 * 财务人员客户经理佣金管理列表数据。
	 * @param query 查询条件
	 * @param page 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commdtllist", method = RequestMethod.POST)
	public JsonResult commdtllist(MonthCommDtlQuery query, Page page) {
		if (query.getMonth() == null) {
			query.setMonth(org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.MONTH));
		}
		List<MonthCommDtl> monthCommDtlList = this.commissionService.findMonthCommDtlByQuery(query, page);
		BigDecimal totalAmount = this.commissionService.getTotalMonthCommDtlByQuery(query);
		page.setDataList(monthCommDtlList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		json.addDatas("total", totalAmount);
		return json;
	}

	/**
	 * 佣金明细页面。
	 * @param sellerId 销售ID
	 * @param customerId 客户ID
	 * @param month 月份
	 * @param model
	 */
	@RequestMapping(value = "/recordlist", method = RequestMethod.GET)
	public void recordlist(@RequestParam(required = false) Long sellerId, Long customerId, Date month, Model model) {
		if (sellerId != null) {
			UserBase userBase = UserBaseContext.getUserBaseByUserId(sellerId);
			model.addAttribute("sellerName", userBase.getUserName());
		}
		Customer customer = this.customerService.getCustomerByCustomerId(customerId);
		List<Commission> commissionList = this.commissionService.findCommissionByCondition(customerId, month);
		BigDecimal total = new BigDecimal(0);
		for (Commission commission : commissionList) {
			if (commission.getCommAmount() != null) {
				total = total.add(commission.getCommAmount());
			}
		}
		model.addAttribute("month", DateUtils.format(month, "yyyy年MM月"));
		model.addAttribute("customerName", customer.getCustomerName());
		model.addAttribute("bindYear", CommRuleUtils.getSeveralYear(new Date(), customer.getConsumeTime()));
		model.addAttribute("total", total);
		model.addAttribute("commissionList", commissionList);
	}
}
