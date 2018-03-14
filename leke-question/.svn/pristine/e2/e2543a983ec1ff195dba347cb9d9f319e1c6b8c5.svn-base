/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.OfficialTag;
import cn.strong.leke.question.service.IOfficialTagService;
import cn.strong.leke.remote.service.question.IOfficialTagRemoteService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月10日 下午3:27:48
 * @since   v1.0.0
 */
@Service
public class OfficialTagRemoteService implements IOfficialTagRemoteService {
	@Autowired
	private IOfficialTagService officialTagService;

	@Override
	public List<OfficialTag> findOfficialTags() {
		return officialTagService.findOfficialTagList();
	}

}
