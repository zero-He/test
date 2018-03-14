/**
 * 
 */
package cn.strong.leke.question.controller;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.question.analysis.core.service.IMatNodeKlgService;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgs;

/**
 * 教材章节关联知识点控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/common/jsonp")
public class JsonpMatNodeKlgController {
	@Resource
	private IMatNodeKlgService matNodeKlgService;

	@RequestMapping("getMatNodeKlgs")
	@ResponseBody
	public JSONPObject getMatNodeKlgs(final Long materialNodeId, String callback) {
		return jsonp(callback, (json) -> {
			MatNodeKlgs assocs = matNodeKlgService.getMatNodeKlgs(materialNodeId);
			json.addDatas("knowledges", assocs.getKnowledges());
		});
	}
}
