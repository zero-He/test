package cn.strong.leke.scs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.scs.model.SpecialDate;
import cn.strong.leke.scs.service.SpecialDateService;


/**
 *
 * 描述:法定节假日设置
 *
 * @author  zhengyiyin
 * @created 2015年11月28日 下午2:09:36
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/specialDate/*")
public class SpecialDateController {

	@Resource
	private SpecialDateService specialDateService;

	
	@RequestMapping("specialDateSetting")
	public void specialDateSetting(Model model) {
	}
	
	@ResponseBody
	@RequestMapping("specialDateSettingData")
	public JsonResult specialDateSettingData() {
		JsonResult json = new JsonResult();
		List<String> specialDayOff = specialDateService.queryList(1);
		List<String> specialDayWork = specialDateService.queryList(2);
		
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		map.put("specialDayOff", specialDayOff);
		map.put("specialDayWork", specialDayWork);
		
		json.addDatas("specialDateList", map);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("addSpecialDate")
	public boolean addSpecialDate(String specialDayOff,String specialDayWork) {
		List<SpecialDate> list = new ArrayList<SpecialDate>();
		if(StringUtils.isNotEmpty(specialDayOff)){
			String[] off = specialDayOff.split(",");
			for(int i=0;i< off.length;i++){
				SpecialDate date = new SpecialDate();
				date.setDate(off[i]);
				date.setModifiedBy(UserContext.user.getUserId());
				date.setType(1);
				list.add(date);
			}
		}
		if(StringUtils.isNotEmpty(specialDayWork)){
			String[] work = specialDayWork.split(",");
			for(int i=0;i < work.length; i++){
				SpecialDate date = new SpecialDate();
				date.setDate(work[i]);
				date.setModifiedBy(UserContext.user.getUserId());
				date.setType(2);
				list.add(date);
			}
		}
		specialDateService.delete();
		boolean success = specialDateService.insert(list);
		if(success){
			return true;
		}
		return false;
	}
	
	
	
}
