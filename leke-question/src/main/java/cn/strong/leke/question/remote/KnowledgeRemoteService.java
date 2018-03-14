/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.remote;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.remote.model.question.KlgPath;
import cn.strong.leke.remote.service.question.IKnowledgeRemoteService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月10日 下午2:31:20
 * @since   v1.0.0
 */
@Service
public class KnowledgeRemoteService implements IKnowledgeRemoteService {

	@Autowired
	private KnowledgeService knowledgeService;

	@Override
	public List<Knowledge> findKnowledgeTreeNodes(Knowledge knowledge) {
		if (knowledge == null || knowledge.getSchoolStageId() == null
				|| knowledge.getSubjectId() == null) {
			return Collections.emptyList();
		}
		Knowledge query = new Knowledge();
		query.setSchoolStageId(knowledge.getSchoolStageId());
		query.setSubjectId(knowledge.getSubjectId());
		return knowledgeService.queryKnowledges(query);
	}

	@Override
	public Knowledge getKnowledgeById(Long knowledgeId) {
		return knowledgeService.getKnowledgeById(knowledgeId);
	}

	@Override
	public KlgPath getKlgPath(Long knowledgeId) {
		return knowledgeService.getKlgPath(knowledgeId);
	}

}
