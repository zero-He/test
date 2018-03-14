package cn.strong.leke.question.controller.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JavaType;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.APIModel;

/**
 * HTTP API 控制器基础类
 * 
 * @author liulongbiao
 * @created 2015年1月16日 下午1:59:53
 * @since v3.2.2
 */
public class APIBaseController {

	private static final Logger logger = LoggerFactory.getLogger(APIBaseController.class);

	private static final Pattern PAT_M = Pattern
			.compile("[c,m]_(\\w+)_request");

	// 接口请求分发。
	@RequestMapping("invoke")
	public String invoke(@RequestParam("data") String data, Model model) {
		logger.info("Invoke data: {}.", data);
		String m = parseM(data);
		model.addAttribute("m", m);
		model.addAttribute("data", data);
		return "forward:" + m + ".htm";
	}

	// 解析M值
	private String parseM(String data) {
		Matcher matcher = PAT_M.matcher(data);
		matcher.find();
		return matcher.group(1);
	}

	// 解析请求参数
	protected <T> T parseP(HttpServletRequest request, Class<T> clazz) {
		String data = (String) request.getAttribute("data");
		JavaType valueType = JsonUtils.getTypeFactory().constructParametrizedType(APIModel.class,
				APIModel.class, clazz);
		APIModel<T> model = JsonUtils.fromJSON(data, valueType);
		return model.getP();
	}

	// 构造返回数据
	protected APIModel<JsonResult> buildApiModel(JsonResult json, HttpServletRequest request) {
		String m = (String) request.getAttribute("m");
		APIModel<JsonResult> result = new APIModel<JsonResult>();
		result.setP(json);
		result.setM("c_" + m + "_replay");
		return result;
	}
}
