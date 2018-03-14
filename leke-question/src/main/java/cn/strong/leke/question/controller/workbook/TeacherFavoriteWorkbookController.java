package cn.strong.leke.question.controller.workbook;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.question.service.IWorkbookService;

/**
 *
 * 描述:老师习题册收藏
 *
 * @author raolei
 * @created 2015年11月30日 上午10:29:37
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/teacher/workbook/favorite")
public class TeacherFavoriteWorkbookController {

	@Resource
	private IWorkbookService workbookService;

	@RequestMapping("list")
	public void list() {

	}

}
