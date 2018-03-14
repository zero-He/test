package cn.strong.leke.question.controller.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.question.QuestionCartContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;

@Controller
@RequestMapping("/auth/common/question/")
public class CommonCacheQuestionController {

	@ResponseBody
	@RequestMapping("cart/find")
	public JsonResult find() {
		Long userId = UserContext.user.getUserId();
		return new JsonResult().addDatas("qids", QuestionCartContext.find(userId));
	}

	@ResponseBody
	@RequestMapping("cart/add")
	public JsonResult add(Long qid) {
		if (qid == null || QuestionContext.getQuestionDTO(qid) == null) {
			throw new ValidateException("que.question.not.exist");
		}
		Long userId = UserContext.user.getUserId();
		QuestionCartContext.push(userId, qid);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("cart/del")
	public JsonResult del(Long qid) {
		if (qid == null || QuestionContext.getQuestionDTO(qid) == null) {
			throw new ValidateException("que.question.not.exist");
		}
		Long userId = UserContext.user.getUserId();
		QuestionCartContext.del(userId, qid);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("cart/clear")
	public JsonResult clear() {
		Long userId = UserContext.user.getUserId();
		QuestionCartContext.deleteCache(userId);
		return new JsonResult();
	}
}
