/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.mongo.model.LekeDomain;
import cn.strong.leke.monitor.mongo.service.ILekeDomainService;

/**
 *
 *
 * @author  liulongbiao
 */
@Controller
@RequestMapping("/auth/operationStaff/domain")
public class LekeDomainController {
	@Autowired
	private ILekeDomainService lekeDomainService;

	@RequestMapping("index")
	public void index() {

	}

	@RequestMapping("datas")
	@ResponseBody
	public JsonResult datas() {
		JsonResult result = new JsonResult();
		List<LekeDomain> domains = lekeDomainService.findAllDomains();
		result.addDatas("domains", domains);
		return result;
	}

	@RequestMapping("save")
	@ResponseBody
	public JsonResult save(LekeDomain domain) {
		lekeDomainService.save(domain);
		return new JsonResult();
	}

	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String subdomain) {
		lekeDomainService.remove(subdomain);
		return new JsonResult();
	}
}
