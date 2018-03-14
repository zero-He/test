/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.controller.school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.tutor.MultiQuery;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.area.AreaMerge;
import cn.strong.leke.model.user.school.SchoolStatistics;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.remote.service.user.IEduBureauRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;
import cn.strong.leke.remote.service.user.IUserBaseRemoteService;

/**
 *
 * 描述: 学校统计控制器
 *
 * @author  basil
 * @created 2014-12-9 下午2:17:35
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/*")
public class SchoolStatisController {
	
	@Resource
	private ISchoolRemoteService iSchoolRemoteService;
	
	@Resource
	private IUserRemoteService iUserRemoteService;
	
	@Resource
	private IUserBaseRemoteService iUserBaseRemoteService;
	
	@Resource
	private IEduBureauRemoteService eduBureauRemoteService;
	
	@RequestMapping("platformAdmin/school/schoolStatisForChart")
	public void schoolStatisForChart(Model model) {
		Integer schoolCount = iSchoolRemoteService.findSchoolCount();
		Integer tchCount = iUserRemoteService.findUserCount(RoleCst.TEACHER);
		Integer stuCount = iUserRemoteService.findUserCount(RoleCst.STUDENT);
		SchoolStatistics ss = new SchoolStatistics();
		ss.setSchoolCount(schoolCount);
		ss.setTchCount(tchCount);
		ss.setStuCount(stuCount);
		model.addAttribute("ss", ss);
		Calendar cal = Calendar.getInstance();
		model.addAttribute("endTime", cal.getTime());
		cal.add(Calendar.MONTH, -11);
		model.addAttribute("startTime", cal.getTime());
	}
	
	@RequestMapping("educationDirector/school/schoolStatisForEduChart")
	public void schoolStatisForEduChart(Model model) {
		AreaMerge areas = eduBureauRemoteService.findAreaMergeByUserId(UserContext.user.getUserId());
		areas.setType(AreaMerge.TYPE_REGION);
		model.addAttribute("areas", JsonUtils.toJSON(areas));
		List<School> schoolList = iSchoolRemoteService.findByAreas(areas);
		String schoolIds = "";
		SchoolStatistics ss = new SchoolStatistics();
		if(CollectionUtils.isNotEmpty(schoolList)) {
			List<School> schools = new ArrayList<School>();
			for (School s : schoolList) {
				if(s.getCode() >=1000) {
					schools.add(s);
					schoolIds = schoolIds + "," + s.getId();
				}
			}
			if(StringUtils.isNotEmpty(schoolIds)) {
				schoolIds = schoolIds.substring(1);
			}
			
			if(CollectionUtils.isNotEmpty(schools)) {
				ss.setSchoolCount(schools.size());
				Map<String, Object> teaMap = new HashMap<String, Object>();
				teaMap.put("roleId", RoleCst.TEACHER);
				teaMap.put("schoolList", schools);
				Integer tchCount = iUserRemoteService.findUserCountBySchIds(teaMap);
				Map<String, Object> stuMap = new HashMap<String, Object>();
				stuMap.put("roleId", RoleCst.STUDENT);
				stuMap.put("schoolList", schools);
				Integer stuCount = iUserRemoteService.findUserCountBySchIds(stuMap);
				ss.setTchCount(tchCount);
				ss.setStuCount(stuCount);
			} else {
				ss.setSchoolCount(0);
				ss.setTchCount(0);
				ss.setStuCount(0);
			}
		} else {
			ss.setSchoolCount(0);
			ss.setTchCount(0);
			ss.setStuCount(0);
		}
		
		model.addAttribute("ss", ss);
		model.addAttribute("schoolIds", schoolIds);
		Calendar cal = Calendar.getInstance();
		model.addAttribute("endTime", cal.getTime());
		cal.add(Calendar.MONTH, -11);
		model.addAttribute("startTime", cal.getTime());
	}
	
	
	@RequestMapping(value={"platformAdmin/school/loadSchoolStatisForTimeChart", "educationDirector/school/loadSchoolStatisForTimeChart"})
	@ResponseBody
	public JsonResult loadSchoolStatisForTimeChart(MultiQuery query, String schoolIdsStr, String startStr, String endStr) {
		JsonResult jsonResult = new JsonResult();
		query.setStartTime(DateUtils.parseDate(startStr + "-01"));
		query.setEndTime(DateUtils.parseDate(endStr + "-01"));
		if ((schoolIdsStr != null) && (!"".equals(schoolIdsStr))) {
			String[] idsStrArr = schoolIdsStr.split(",");
			List<Long> schoolIds = new ArrayList<Long>();
			for (String id : idsStrArr) {
				schoolIds.add(Long.parseLong(id));
			}
			query.setSchoolIds(schoolIds);
		}
		Map<Integer, Integer[]> map = iSchoolRemoteService.findSchoolStatisticsByTime(query);
		int minMonth = monthOfYear(query.getStartTime());
		int maxMonth = monthOfYear(query.getEndTime());
		if (minMonth > maxMonth) {
			maxMonth = maxMonth +12;
		}
		Integer length = map.size();
		String monArr[] = new String[length];
		Integer schArr[] = new Integer[length];
		Integer tchArr[] = new Integer[length];
		Integer stuArr[] = new Integer[length];
		int j = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(query.getStartTime());
		String fmt = "yyyy-MM";
		monArr[0] = DateUtils.format(cal.getTime(), fmt);
		for (int i = minMonth; i <= maxMonth; i++) {
			Integer[] dataArr = new Integer[3];
			if (i > 12) {
				dataArr = map.get(i-12);
			} else {
				dataArr = map.get(i);
			}
			if (j > 0) {
				cal.add(Calendar.MONTH, 1);
				monArr[j] = DateUtils.format(cal.getTime(), fmt);
			}
			schArr[j] = dataArr[0];
			tchArr[j] = dataArr[1];
			stuArr[j] = dataArr[2];
			j++;
		}
	
		jsonResult.addDatas("monArr", monArr);
		jsonResult.addDatas("schArr", schArr);
		jsonResult.addDatas("tchArr", tchArr);
		jsonResult.addDatas("stuArr", stuArr);
		return jsonResult;
	}
	
	private static int monthOfYear(Date date) {
		return new DateTime(date).getMonthOfYear();
	}

	@RequestMapping(value={"platformAdmin/school/loadSchoolStatisForAreaChart", "educationDirector/school/loadSchoolStatisForAreaChart"})
	@ResponseBody
	public JsonResult loadSchoolStatisForAreaChart(MultiQuery query, String areasStr) {
		JsonResult jsonResult = new JsonResult();
		AreaMerge areaMerge = new AreaMerge();
		if (StringUtils.isEmpty(areasStr)) {
			areaMerge.setType(AreaMerge.TYPE_REGION);
			areaMerge.setProvinceId(0L);
		} else {
			areaMerge = JsonUtils.fromJSON(areasStr, AreaMerge.class);
		}
		query.setAreas(areaMerge);
		Map<Long, Object[]> map = iSchoolRemoteService.findSchoolStatisticsByArea(query);
		Integer length = map.size();
		Long areaIdArr[] = new Long[length];
		String areaNameArr[] = new String[length];
		Integer schArr[] = new Integer[length];
		Integer tchArr[] = new Integer[length];
		Integer stuArr[] = new Integer[length];
		int i = 0;
		for (Long key : map.keySet()) {
			Object[] datas = map.get(key);
			areaIdArr[i] = key;
			areaNameArr[i] = datas[0].toString();
			schArr[i] = Integer.parseInt(datas[1].toString());
			tchArr[i] = Integer.parseInt(datas[2].toString());
			stuArr[i] = Integer.parseInt(datas[3].toString());
			i++;
		}
		jsonResult.addDatas("areaIdArr", areaIdArr);
		jsonResult.addDatas("areaNameArr", areaNameArr);
		jsonResult.addDatas("schArr", schArr);
		jsonResult.addDatas("tchArr", tchArr);
		jsonResult.addDatas("stuArr", stuArr);
		return jsonResult;
	}
	
	/**
	 *	
	 * 描述:入驻学校统计列表
	 *
	 * @author  C.C
	 * @created 2014年12月22日 上午9:48:39
	 * @since   v1.0.0 
	 * @return  void
	 */
	@RequestMapping("platformAdmin/school/schoolStatisList")
	public void schoolStatisList() {
	}
	
	/**
	 *	
	 * 描述:加载入驻学校统计列表信息
	 *
	 * @author  C.C
	 * @created 2014年12月22日 上午9:49:05
	 * @since   v1.0.0 
	 * @param query
	 * @param areasStr
	 * @param nSort
	 * @param page
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping("platformAdmin/school/loadSchoolStatisList")
	@ResponseBody
	public JsonResult loadSchoolStatisList(MultiQuery query, String areasStr, Integer nSort, PageRemote<SchoolStatistics> page) {
		JsonResult jsonResult = new JsonResult();
		AreaMerge areaMerge = new AreaMerge();
		if (StringUtils.isEmpty(areasStr)) {
			areaMerge.setType(AreaMerge.TYPE_REGION);
		} else {
			areaMerge = JsonUtils.fromJSON(areasStr, AreaMerge.class);
		}
		query.setAreas(areaMerge);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != nSort) {
			map.put("sort", nSort);
		} else {
			map.put("sort", 5);//按学校创建时间倒序
		}
		query.setSort(map);
		page = iSchoolRemoteService.findStatisList(query, page);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	/**
	 *	
	 * 描述:
	 *
	 * @author  shendan
	 * @created 2015-1-5 下午2:03:15
	 * @since   v1.0.0 
	 * @param model
	 * @return  void
	 */
	@RequestMapping("educationDirector/school/schoolStatisList")
	public void schoolStatisListForEdu(Model model) {
		AreaMerge areas = eduBureauRemoteService.findAreaMergeByUserId(UserContext.user.getUserId());
		areas.setType(AreaMerge.TYPE_REGION);
		model.addAttribute("areas", JsonUtils.toJSON(areas));
		List<School> schoolList = iSchoolRemoteService.findByAreas(areas);
		model.addAttribute("schoolList", schoolList);
	}
	
	/**
	 *	
	 * 描述:某个月份学校列表
	 *
	 * @author  C.C
	 * @created 2015年3月18日 下午5:11:20
	 * @since   v1.0.0 
	 * @param modelMap
	 * @param timeStr
	 * @param schoolIdsStr
	 * @return  void
	 */
	@RequestMapping(value={"platformAdmin/school/schoolListByTime","educationDirector/school/schoolListByTime"})
	public void schoolListByTime(ModelMap modelMap, String timeStr, String schoolIdsStr) {
		Date startTime = DateUtils.parseDate(timeStr + "-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MONTH, 1);
		modelMap.addAttribute("startTime", DateUtils.formatDate(startTime));
		modelMap.addAttribute("endTime", DateUtils.formatDate(cal.getTime()));
		modelMap.addAttribute("schoolIdsStr", schoolIdsStr);
		modelMap.addAttribute("timeStr", timeStr);
	}
	
	/**
	 *	
	 * 描述:加载某个月份学校列表信息
	 *
	 * @author  C.C
	 * @created 2015年3月18日 下午5:12:07
	 * @since   v1.0.0 
	 * @param query
	 * @param schoolIdsStr
	 * @param page
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping(value={"platformAdmin/school/loadSchoolListByTime", "educationDirector/school/loadSchoolListByTime"})
	@ResponseBody
	public JsonResult loadSchoolListByTime(MultiQuery query, String schoolIdsStr, PageRemote<School> page) {
		JsonResult jsonResult = new JsonResult();
		List<Long> schoolIdList =  parseStrToList(schoolIdsStr);
		query.setSchoolIds(schoolIdList);
		page = iSchoolRemoteService.findSchoolListByTime(query, page);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	/**
	 *	
	 * 描述:某个月份用户（教师，学生）列表
	 *
	 * @author  C.C
	 * @created 2015年3月18日 下午5:12:44
	 * @since   v1.0.0 
	 * @param modelMap
	 * @param timeStr
	 * @param schoolIdsStr
	 * @param roleId
	 * @return  void
	 */
	@RequestMapping(value={"platformAdmin/school/schoolUserListByTime","educationDirector/school/schoolUserListByTime"})
	public void schoolUserListByTime(ModelMap modelMap, String timeStr, String schoolIdsStr, Long roleId) {
		Date startTime = DateUtils.parseDate(timeStr + "-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MONTH, 1);
		modelMap.addAttribute("startTime", DateUtils.formatDate(startTime));
		modelMap.addAttribute("endTime", DateUtils.formatDate(cal.getTime()));
		modelMap.addAttribute("schoolIdsStr", schoolIdsStr);
		modelMap.addAttribute("timeStr", timeStr);
		modelMap.addAttribute("roleId", roleId);
	}
	
	/**
	 *	
	 * 描述:加载某个月份用户（教师，学生）列表信息
	 *
	 * @author  C.C
	 * @created 2015年3月18日 下午5:13:22
	 * @since   v1.0.0 
	 * @param query
	 * @param schoolIdsStr
	 * @param page
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping(value={"platformAdmin/school/loadSchoolUserListByTime", "educationDirector/school/loadSchoolUserListByTime"})
	@ResponseBody
	public JsonResult loadSchoolUserListByTime(MultiQuery query, String schoolIdsStr, PageRemote<User> page) {
		JsonResult jsonResult = new JsonResult();
		List<Long> schoolIdList =  parseStrToList(schoolIdsStr);
		query.setSchoolIds(schoolIdList);
		page = iUserBaseRemoteService.findSchoolUserListByTime(query, page);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@RequestMapping(value={"platformAdmin/school/schoolListByArea","educationDirector/school/schoolListByArea"})
	public void schoolListByArea(ModelMap modelMap, Long areaId, String areaName, String areasStr) {
		modelMap.addAttribute("areaId", areaId);
		modelMap.addAttribute("areaName", areaName);
		modelMap.addAttribute("areasStr", areasStr);
	}
	
	@RequestMapping(value={"platformAdmin/school/loadSchoolListByArea", "educationDirector/school/loadSchoolListByArea"})
	@ResponseBody
	public JsonResult loadSchoolListByArea(MultiQuery query, String areasStr, PageRemote<School> page) {
		JsonResult jsonResult = new JsonResult();
		AreaMerge areaMerge = new AreaMerge();
		if (StringUtils.isEmpty(areasStr)) {
			areaMerge.setType(AreaMerge.TYPE_REGION);
		} else {
			areaMerge = JsonUtils.fromJSON(areasStr, AreaMerge.class);
		}
		query.setAreas(areaMerge);
		page = iSchoolRemoteService.findSchoolListByArea(query, page);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@RequestMapping(value={"platformAdmin/school/schoolUserListByArea","educationDirector/school/schoolUserListByArea"})
	public void schoolUserListByArea(ModelMap modelMap, Long areaId, String areaName, String areasStr, Long roleId) {
		modelMap.addAttribute("areaId", areaId);
		modelMap.addAttribute("areaName", areaName);
		modelMap.addAttribute("areasStr", areasStr);
		modelMap.addAttribute("roleId", roleId);
	}
	
	
	@RequestMapping(value={"platformAdmin/school/loadSchoolUserListByArea", "educationDirector/school/loadSchoolUserListByArea"})
	@ResponseBody
	public JsonResult loadSchoolUserListByArea(MultiQuery query, String areasStr, PageRemote<User> page) {
		JsonResult jsonResult = new JsonResult();
		AreaMerge areaMerge = new AreaMerge();
		if (StringUtils.isEmpty(areasStr)) {
			areaMerge.setType(AreaMerge.TYPE_REGION);
		} else {
			areaMerge = JsonUtils.fromJSON(areasStr, AreaMerge.class);
		}
		query.setAreas(areaMerge);
		page = iUserBaseRemoteService.findSchoolListByArea(query, page);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	private List<Long> parseStrToList(String schoolIdsStr) {
		if (StringUtils.isNotEmpty(schoolIdsStr)) {//教育局长管辖学校
			String[] idsStrArr = schoolIdsStr.split(",");
			List<Long> schoolIds = new ArrayList<Long>();
			for (String id : idsStrArr) {
				schoolIds.add(Long.parseLong(id));
			}
			return schoolIds;
		} else {
			return null;
		}
	}
	
}
