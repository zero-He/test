package cn.strong.leke.homework.controller.work;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.model.user.User;

@Controller
@RequestMapping("/auth/common/homework")
public class RedirectController {

	protected static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@RequestMapping("/beikeWork")
	public String redirectWork(Long homeworkDtlId) {
		User user = UserContext.user.get();
		if (RoleCst.STUDENT.equals(user.getCurrentRole().getId())) {
			HomeworkDtl homeworkDtl = homeworkDtlService.getHomeworkDtlById(homeworkDtlId);
			if (homeworkDtl.getSubmitTime() == null) {
				return "redirect:/auth/student/homework/doWork.htm?homeworkDtlId=" + homeworkDtlId;
			}else if(homeworkDtl.getBugFixStage().equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX)){
				return "redirect:/auth/student/homework/doBugFix.htm?homeworkDtlId=" + homeworkDtlId;
			}
			else {
				return "redirect:/auth/student/homework/viewWork.htm?homeworkDtlId=" + homeworkDtlId;
			}
		}
		return "redirect:/auth/common/homework/viewWork.htm?homeworkDtlId=" + homeworkDtlId;
	}
}
