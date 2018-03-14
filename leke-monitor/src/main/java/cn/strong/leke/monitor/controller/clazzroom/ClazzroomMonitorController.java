/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.controller.clazzroom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 描述:课堂监控控制器
 *
 * @author  basil
 * @created 2014-12-9 下午2:14:23
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/*")
public class ClazzroomMonitorController {
	
	@RequestMapping(value="platformAdmin/clazzroom/clazzroomView")
	public void clazzroomView(ModelMap map) {
	}
	
	@RequestMapping(value="educationDirector/clazzroom/clazzroomView")
	public void clazzroomViewForEdu(ModelMap map) {
	}
	
	@RequestMapping(value="president/clazzroom/clazzroomView")
	public void clazzroomViewForPre(ModelMap map) {
	}

}
