package cn.strong.leke.diag.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.diag.model.WorkStats;
import cn.strong.leke.diag.service.AnalyzeService;
import cn.strong.leke.framework.web.JsonResult;

@RestController
@RequestMapping("/api/w/*")
public class APIWController {

	private static final Logger logger = LoggerFactory.getLogger(APIWController.class);
	
	@Resource
	private AnalyzeService analyzeService;
	
	@RequestMapping("/generateHwAnalyze")
	public JsonResult generateHwAnalyze(String  ticket, String data){
		logger.info("Invoke api: {}, {}.", "generateHwAnalyze", data);
		Homework hw = JsonUtils.fromJSON(data, Homework.class);
		WorkStats workStats = this.analyzeService.generateHomeworkAnalyze(hw.getHomeworkId());
		return new JsonResult().addDatas("sums", workStats.getSums()).addDatas("homeworkId", hw.getHomeworkId());
	}

}
