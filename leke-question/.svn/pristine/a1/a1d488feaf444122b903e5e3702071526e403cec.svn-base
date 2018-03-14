/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller.question;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.question.core.question.query.ISchoolQuestionQueryService;

/**
 *
 *
 * @author  liulongbiao
 */
@Controller
@RequestMapping("/auth/provost/question/school")
public class ProvostSchoolQuestionController {
	@Resource
	private ISchoolQuestionQueryService schoolQuestionQueryService;

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}

	@RequestMapping("details")
	public void details(RepositoryQuestionQuery query, Page page, Model model) {
		query.setShareScope(ShareScopes.SCHOOL);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<RepositoryRecord> records = schoolQuestionQueryService.querySchoolQuestions(
				query, page);
		model.addAttribute("records", records);
		model.addAttribute("page", page);
	}
}
