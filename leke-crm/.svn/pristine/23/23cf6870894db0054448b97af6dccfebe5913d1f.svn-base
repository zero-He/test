package cn.strong.leke.scs.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.cas.utils.UrlUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.user.MarketManagerRemote;
import cn.strong.leke.remote.service.user.IMarketRemoteService;
import cn.strong.leke.scs.constant.CustomerCst;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.Seller;
import cn.strong.leke.scs.model.query.MonthCommDtlQuery;
import cn.strong.leke.scs.model.view.MonthCommDtl;
import cn.strong.leke.scs.service.CommissionService;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.scs.service.SellerService;

/**
 * 客户经理管理。
 * 
 * @author andy
 * @created 2015年6月15日 下午2:39:28
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/*")
public class SellerIndexController
{

	@Resource
	private SellerService sellerService;
	@Resource
	private CustomerService customerService;
	@Resource
	private CommissionService commissionService;

	@Resource
	private IMarketRemoteService marketManagerService;

	@InitBinder
	public void initBind(DataBinder binder)
	{
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM"), true, 7));
	}

	/**
	 * 客户经理首页。
	 */
	@RequestMapping("scs/seller/index")
	public String index(Model model, HttpServletRequest request)
	{
		Long sellerId = UserContext.user.getUserId();

		//验证是否绑定市场
		List<MarketManagerRemote> managerList = marketManagerService.findMarketManagerByUserRoleId(UserContext.user.getUserId(),
				UserContext.user.getCurrentRoleId());
		if (managerList == null || managerList.size() <= 0 || StringUtils.isEmpty(managerList.get(0).getSecondDptIds()))
		{
			return "redirect:" + UrlUtils.getServerName(request, "tutorServerName") + "/auth/seller/security/bind.htm";
		}
		Seller seller = this.sellerService.getSellerBySellerId(sellerId);
		model.addAttribute("seller", seller);
		model.addAttribute("month", DateUtils.format("yyyy-MM"));
		return "auth/scs/seller/index";
	}

	/**
	 * 客户经理佣金管理列表数据。
	 * 
	 * @param query
	 *            查询条件
	 * @param page
	 *            分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "scs/seller/commlist", method = RequestMethod.POST)
	public JsonResult commlist(MonthCommDtlQuery query, Page page)
	{
		if (query.getMonth() == null)
		{
			query.setMonth(org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.MONTH));
		}
		query.setSellerId(UserContext.user.getUserId());
		List<MonthCommDtl> monthCommDtlList = this.commissionService.findMonthCommDtlByQuery(query, page);
		BigDecimal totalAmount = this.commissionService.getTotalMonthCommDtlByQuery(query);
		page.setDataList(monthCommDtlList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		json.addDatas("total", totalAmount);
		return json;
	}

	/**
	 * 根据客户的信息，获取销售姓名
	 * 
	 * @param lekeId
	 *            用户ID或者学校ID
	 * @param customerType
	 *            客户类型
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("scs/common/seller")
	public Object seller(Long lekeId, Integer customerType, String jsoncallback)
	{
		Customer customer = this.customerService.getCustomerByLekeIdAndCustomerTypeAll(lekeId, customerType);
		JsonResult json = new JsonResult();
		if (customer != null && customer.getSellerId() != null)
		{
			UserBase userBase = UserBaseContext.getUserBaseByUserId(customer.getSellerId());
			json.addDatas("isBind", true);
			json.addDatas("sellerId", customer.getSellerId());
			json.addDatas("sellerName", customer.getSellerName());
			json.addDatas("phone", userBase.getPhone());
			json.addDatas("status", customer.getStatus());
			json.addDatas("statusStr", customer.getStatusStr());
		} else
		{
			json.addDatas("isBind", false);
		}
		return new JSONPObject(jsoncallback, json);
	}

	/**
	 *
	 * 描述:解除关联
	 *
	 * @author zhengyiyin
	 * @created 2016年1月29日 上午10:27:06
	 * @since v1.0.0
	 * @param lekeId
	 * @param modifiedBy
	 * @param jsoncallback
	 * @return
	 * @return JSONPObject
	 */
	@ResponseBody
	@RequestMapping("technicalSupport/removeCustomerRelation")
	public JSONPObject removeCustomerRelation(Long lekeId, Long modifiedBy, String jsoncallback)
	{
		JsonResult json = new JsonResult();
		Customer customer = customerService.getCustomerByLekeIdAndCustomerType(lekeId, null);
		if (customer == null)
		{
			throw new LekeRuntimeException("该客户不存在或正在审核");
		}
		boolean isSuccess = customerService.deleteSchoolCustomer(customer.getCustomerId(), modifiedBy);
		if (!isSuccess)
		{
			throw new LekeRuntimeException("取消关联失败");
		}
		json.setSuccess(isSuccess);
		return new JSONPObject(jsoncallback, json);
	}
}
