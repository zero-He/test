package cn.strong.leke.scs.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.strong.leke.common.utils.StringUtils;

import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.crm.SaleRelationMQ;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.user.MarketManagerRemote;
import cn.strong.leke.remote.service.pay.IVirtualCurrencyAccuntRemoteService;
import cn.strong.leke.remote.service.user.IMarketRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;
import cn.strong.leke.scs.constant.CustomerCst;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.CustomerDTO;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.query.CustomerQuery;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.user.model.parameters.BestowalSetting;
import cn.strong.leke.user.model.parameters.ParameterCsts;

/**
 * 客户管理控制器。
 * @author andy
 * @created 2015年11月28日 上午10:43:34
 * @since v1.9.0
 */
@Controller
@RequestMapping("/auth/scs/customer")
public class CustomerController
{

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Resource
	private CustomerService customerService;
	@Resource
	private ISchoolRemoteService schoolRemoteService;
	@Resource
	private IVirtualCurrencyAccuntRemoteService iVirtualCurrencyAccuntRemoteService;

	@Resource
	private IMarketRemoteService marketManagerService;

	/**
	 * 个人学校 客户列表页面。
	 * 
	 * @param model
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model)
	{

	}

	/**
	 * 个人 客户列表查询。
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JsonResult list(CustomerQuery query, Page page)
	{
		query.setSellerId(UserContext.user.getUserId());
		query.setCustomerType(CustomerCst.CUSTOMERTYPE_PERSON);
		List<CustomerVO> customerList = this.customerService.findCustomerByQuery(query, page);
		page.setDataList(customerList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	

	/**
	 * 待审核页面
	 * 
	 * @param model
	 */
	@RequestMapping(value = "/approveList", method = RequestMethod.GET)
	public void approveList(Model model)
	{

	}

	
	/**
	 * 待审核列表
	 * @param query
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getApproveList", method = RequestMethod.POST)
	public JsonResult getApproveList(CustomerQuery query, Page page)
	{
		List<CustomerDTO> customerList = this.customerService.getApproveList(UserContext.user.getUserId(), 1, 0, page);
		customerList.forEach(t -> {
			List<School> schList = schoolRemoteService.findSchoolListByUserRoleId(t.getLekeId(), RoleCst.TEACHER);
			if (schList != null && schList.size() > 0)
			{
				for (School school : schList)
				{
					if (school.getSchoolNature().equals(3))
					{
						t.setSchoolName(school.getName());
					}
				}
			}
		});
		page.setDataList(customerList);
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 验证是否首次绑定
	 * 
	 * @param lekeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkHasBind", method = RequestMethod.POST)
	public JsonResult checkHasBind(Long lekeId)
	{
		JsonResult json = new JsonResult();
		BestowalSetting bestowalSetting = ParametersContext.getObject(ParameterCsts.ACCOUNT_POINT_BESTOWAL,BestowalSetting.class);
		boolean f = this.customerService.getNearlyEffectCustomer(lekeId).size() > 0;
		json.addDatas("f", f);
		json.addDatas("relationSale", bestowalSetting.getRelationSale());//最大点数
		return json;
	}
	
	/**
	 * 保存绑定关系
	 * @param customerId
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doBind", method = RequestMethod.POST)
	public JsonResult doBind(Long customerId, Integer status,Integer relationSale)
	{
		JsonResult json = new JsonResult();
		try
		{
			this.customerService.saveBind(customerId, status,relationSale);
		} catch (Exception ex)
		{
			json.setSuccess(false);
			json.setErr(ex.getMessage());
		}
		return json;
	}
	
	/**	
	 * 描述: 机构学校 客户列表页面。
	 * 
	 * @author zhengyiyin
	 * @created 2015年11月28日 下午3:05:38
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/schoolList", method = RequestMethod.GET)
	public void schoolList(Model model)
	{

	}

	// 需根据学校id .查询学校相关数据
	@ResponseBody
	@RequestMapping(value = "/schoolListDate", method = RequestMethod.POST)
	public JsonResult schoolListDate(CustomerQuery query, Page page)
	{
		JsonResult json = new JsonResult();
		query.setSellerId(UserContext.user.getUserId());
		query.setCustomerType(CustomerCst.CUSTOMERTYPE_SCHOOL);
		List<CustomerVO> customerList = this.customerService.findCustomerByQuery(query, page);

		for (CustomerVO customerVO : customerList)
		{
			School school = SchoolContext.getSchoolBySchoolId(customerVO.getLekeId());
			customerVO.setSchoolEmail(school.getEmail());
			customerVO.setIsWeb(school.getIsWeb());
			// 学校阶段 id[] 转换成 阶段名称
			String stageName = "";
			for (int i = 0; i < school.getSchoolStageIds().length; i++)
			{
				Long stageId = school.getSchoolStageIds()[i];
				SchoolStageRemote schoolStageRemote = SchoolStageContext.getSchoolStage(stageId);
				if (schoolStageRemote != null)
				{
					stageName = stageName + schoolStageRemote.getSchoolStageName() + ",";
				}
			}
			if (!stageName.isEmpty())
			{
				// 删除最后一个逗号
				customerVO.setSchoolStageNames(stageName.substring(0, stageName.length() - 1));
			}

		}
		page.setDataList(customerList);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 描述:客户经理 关联 学校客户
	 * 
	 * @author zhengyiyin
	 * @created 2015年11月30日 下午8:43:29
	 * @since v1.0.0
	 * @param model
	 * @return void
	 */
	@RequestMapping(value = "/addSchoolCustomer", method = RequestMethod.GET)
	public void addSchoolCustomer(Model model)
	{

	}

	@ResponseBody
	@RequestMapping(value = "/doAddSchoolCustomer", method = RequestMethod.POST)
	public JsonResult doAddSchoolCustomer(Long schoolId, String schoolName)
	{
		JsonResult json = new JsonResult();
		SaleRelationMQ saleRelationMQ = new SaleRelationMQ();
		saleRelationMQ.setLekeId(schoolId);
		saleRelationMQ.setCustomerName(schoolName);
		saleRelationMQ.setCustomerType(CustomerCst.CUSTOMERTYPE_SCHOOL);
		saleRelationMQ.setLekeLoginName(SchoolContext.getSchoolBySchoolId(schoolId).getCode().toString());// LekeLoginName
																											// 暂存
																											// 学校编码
		saleRelationMQ.setSellerId(UserContext.user.getUserId());
		saleRelationMQ.setSellerName(UserContext.user.getUserName());
		saleRelationMQ.setStatus(1);
		saleRelationMQ.setBindTime(new Date());
		try
		{
			customerService.bindSaleRelationWithTx(saleRelationMQ);
			json.setMessage("关联成功~");
		} catch (Exception e)
		{
			Customer customer = customerService.getCustomerByLekeIdAndCustomerType(schoolId, CustomerCst.CUSTOMERTYPE_SCHOOL);
			if (customer != null)
			{
				UserBase seller = UserBaseContext.getUserBaseByUserId(customer.getSellerId());
				json.setErr(schoolName + " 已关联客户经理 " + customer.getSellerName() + " 电话（" + seller.getPhone() + "），请线下自行协调。");
			}
			if (customer.getSellerId().equals(UserContext.user.getUserId()))
			{
				json.setErr("您已经关联过该学校！");
			}
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("/delSchoolCustomer")
	public JsonResult delSchoolCustomer(Long customerId)
	{
		JsonResult json = new JsonResult();
		// 默认删除人 只能是 自己，若业务改变 需重新考虑
		boolean isSuccess = customerService.deleteSchoolCustomer(customerId, UserContext.user.getUserId());
		json.setSuccess(isSuccess);
		return json;
	}

	/**
	 * 更新客户备注信息。
	 * 
	 * @param customerId
	 *            客户ID
	 * @param remark
	 *            备注信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
	public JsonResult updateRemark(Long customerId, String remark)
	{
		this.customerService.updateRemarkByCustomerId(customerId, remark, UserContext.user.getUserId());
		return new JsonResult();
	}

	/**
	 * 客户详情
	 * 
	 * @param customerId
	 *            客户ID
	 * @param model
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public void info(Long customerId, Model model)
	{
		Customer customer = this.customerService.getCustomerByCustomerId(customerId);
		model.addAttribute("customer", customer);
	}

	/**
	 * 个人 客户列表查询。
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOnlineApply", method = RequestMethod.POST)
	public JsonResult addOnlineApply(Long schoolId, String schoolName, Float amount)
	{

		Boolean f = iVirtualCurrencyAccuntRemoteService.addOnlineApply(schoolId, amount, schoolName, UserContext.user.get());
		JsonResult json = new JsonResult();
		json.addDatas("f", f);
		return json;
	}

	/**
	 * 验证客户经理是否关联市场
	 * 
	 * @author Deo
	 * @date 170316
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMarket", method = RequestMethod.POST)
	public JsonResult checkMarket()
	{
		Boolean state = false;
		List<MarketManagerRemote> managerList = marketManagerService.findMarketManagerByUserRoleId(UserContext.user.getUserId(),
				UserContext.user.getCurrentRoleId());
		if (managerList != null && managerList.size() > 0)
		{
			if (StringUtils.isNotEmpty(managerList.get(0).getSecondDptIds()))
			{
				state = true;
			}
		}
		JsonResult json = new JsonResult();
		json.addDatas("state", state);
		return json;
	}
}
