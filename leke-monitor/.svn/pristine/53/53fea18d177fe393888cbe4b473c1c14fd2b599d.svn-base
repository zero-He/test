package cn.strong.leke.monitor.controller.pad;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.monitor.core.service.appcenter.IAppCenterService;
import cn.strong.leke.monitor.core.service.appcenter.impl.AppCenterService.PadSystemExtend;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.mongo.appcenter.model.UserImei;
import cn.strong.leke.monitor.util.FileUtils;

@Controller
@RequestMapping("/auth/common/padcon/**")
public class PadSystemController {
	
	@Resource
	private IAppCenterService appCenterService;
	
	@RequestMapping(value = "list" ,method = RequestMethod.GET)
	public void list(Long schoolId,Model model){
		Long cRoleId = UserContext.user.getCurrentRoleId();
		if(!cRoleId.equals(RoleCst.TECHNICALSUPPORT) && !cRoleId.equals(RoleCst.PLATFORM_ADMIN)) {
			throw new ValidateException("当前角色暂未开放此功能，若有需要，请联系客户经理!");
		}
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		model.addAttribute("cSchoolName",school.getName());
		model.addAttribute("schoolId", schoolId);
	}
	
	@RequestMapping(value = "listPage" ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult listPage(Page page,String loginName,String userName,Boolean root,String version,String mac,String imei,Long schoolId){
		Long cRoleId = UserContext.user.getCurrentRoleId();
		if(!cRoleId.equals(RoleCst.TECHNICALSUPPORT) && !cRoleId.equals(RoleCst.PLATFORM_ADMIN)) {
			throw new ValidateException("当前角色暂未开放此功能，若有需要，请联系客户经理!");
		}
		JsonResult result = new JsonResult();
		List<PadSystemExtend> list = appCenterService.findPadSystem(page, loginName, userName, root, version, mac,imei, schoolId);
		page.setDataList(list);
		result.addDatas("page", page);
		return result;
	}
	
	@RequestMapping(value = "userList" ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult userList(String imei){
		Long cRoleId = UserContext.user.getCurrentRoleId();
		if(!cRoleId.equals(RoleCst.TECHNICALSUPPORT) && !cRoleId.equals(RoleCst.PLATFORM_ADMIN)) {
			throw new ValidateException("当前角色暂未开放此功能，若有需要，请联系客户经理!");
		}
		JsonResult result = new JsonResult();
		List<UserImei> list = appCenterService.findUserList(imei);
		result.addDatas("list", list);
		return result;
	}
	
	@RequestMapping("detail")
	public void detail(String loginName,String imei,Long schoolId,Model model){
		PadSystemExtend extend = appCenterService.getPadSystemExtend(imei, schoolId,loginName);
		model.addAttribute("p", extend);
	}
	
	@RequestMapping(value = "export" ,method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response,Long schoolId) throws IOException{
		Long cRoleId = UserContext.user.getCurrentRoleId();
		if(!cRoleId.equals(RoleCst.TECHNICALSUPPORT) && !cRoleId.equals(RoleCst.PLATFORM_ADMIN)) {
			throw new ValidateException("当前角色暂未开放此功能，若有需要，请联系客户经理!");
		}
		String[] headers = new String[]{"乐号","姓名","系统版本","班级","ROOT状态","设备型号及IMEI号","MAC地址"};
		String[] fieldNames = new String[]{"loginName","userName","version","className","rooted","imei","mac"};
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		List<PadSystemExtend> list = appCenterService.findPadSystem(null, null, null, null, null, null,null, schoolId);
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append(school.getName()+"_"+DateUtils.format(new Date(), "yyyy年MM月dd日HH时mm分")+"_设备清单");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<PadSystemExtend>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	
}
