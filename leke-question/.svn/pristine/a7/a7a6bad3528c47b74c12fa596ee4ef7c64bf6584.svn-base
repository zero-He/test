package cn.strong.leke.question.controller.outline;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.question.service.IOutlineNodeService;

/**
 *
 * 描述:
 *
 * @author raolei
 * @created 2016年6月15日 下午2:07:34
 * @since v1.0.0
 */
@Controller
@RequestMapping("/tag/tree/outlineTreeDataService")
public class TagTreeController {

	@Resource
	private IOutlineNodeService outlineNodeService;

	@RequestMapping("queryTreeNodes")
	@ResponseBody
	public List<OutlineNode> queryTreeNodes(Long outlineId, Long parentId) {
		return outlineNodeService.queryOutlineNodes(outlineId, parentId);
	}

}
