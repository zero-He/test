/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 个人收藏习题
 * 
 * @author liulongbiao
 */
@Controller
@RequestMapping("/auth/teacher/question/favorite")
public class TeacherFavoriteQuestionController {

	@RequestMapping("list")
	public void list() {
		// nothing to do
	}
}
