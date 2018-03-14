package cn.strong.leke.question.controller.question;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.question.core.question.cmd.store.IQuestionTask;

@Controller
@RequestMapping("/auth/platformAdmin/question")
public class PlatformAdminQuestionController {

	@Resource
	private IQuestionTask questionTask;

	@RequestMapping("task")
	public void task() {
		questionTask.updateStatusTask();
	}

}
