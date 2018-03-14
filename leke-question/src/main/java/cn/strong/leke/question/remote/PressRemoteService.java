/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.Press;
import cn.strong.leke.question.service.IPressService;
import cn.strong.leke.remote.model.question.PressRemote;
import cn.strong.leke.remote.service.question.IPressRemoteService;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-5-8 下午10:19:22
 * @since   v1.0.0
 */
@Service
public class PressRemoteService implements IPressRemoteService {
	
	@Resource
	private IPressService pressService;

	@Override
	public List<PressRemote> findPressList() {
		return toPressRemotes(pressService.findPresses());
	}

	static List<PressRemote> toPressRemotes(List<Press> sources) {
		if (sources == null || sources.isEmpty()) {
			return Collections.emptyList();
		}
		List<PressRemote> result = new ArrayList<>();
		for (Press press : sources) {
			PressRemote pr = new PressRemote();
			pr.setPressId(press.getPressId());
			pr.setPressName(press.getPressName());
			pr.setNickName(press.getNickName());
			pr.setPressCode(press.getPressCode());
			result.add(pr);
		}
		return result;
	}

	@Override
	public List<PressRemote> findPresses(Long schoolStageId, Long subjectId) {
		return toPressRemotes(pressService.findPresses(schoolStageId, subjectId));
	}

}
