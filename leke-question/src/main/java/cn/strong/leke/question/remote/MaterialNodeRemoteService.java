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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.analysis.core.service.IMatNodeKlgService;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgs;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.remote.model.question.MatNodeKlg;
import cn.strong.leke.remote.model.question.MatPath;
import cn.strong.leke.remote.service.question.IMaterialNodeRemoteService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月10日 下午2:29:35
 * @since   v1.0.0
 */
@Service
public class MaterialNodeRemoteService implements IMaterialNodeRemoteService {
	@Resource
	private MaterialService materialService;
	@Resource
	private IMatNodeKlgService matNodeKlgService;

	@Override
	public List<MaterialNode> findMaterialTreeNodes(MaterialNode node) {
		if (node == null || node.getMaterialId() == null) {
			return Collections.emptyList();
		}
		return materialService.queryMaterialNodesByMaterialId(node
				.getMaterialId());
	}

	@Override
	public MaterialNode getMaterialNodeById(Long materialNodeId) {
		return materialService.getMaterialNodeById(materialNodeId);
	}

	@Override
	public List<MatNodeKlg> findMatNodeKlgs(Long materialNodeId) {
		MatNodeKlgs assocs = matNodeKlgService.getMatNodeKlgs(materialNodeId);
		if (assocs == null) {
			return Collections.emptyList();
		}
		return ListUtils.map(assocs.getKnowledges(), klg -> {
			MatNodeKlg result = new MatNodeKlg();
			result.setKnowledgeId(klg.getKnowledgeId());
			result.setKnowledgeName(klg.getKnowledgeName());
			result.setSchoolStageId(klg.getSchoolStageId());
			result.setSubjectId(klg.getSubjectId());
			result.setParentId(klg.getParentId());
			return result;
		});
	}

	@Override
	public MatPath getMatPath(Long materialNodeId) {
		return materialService.getMatPath(materialNodeId);
	}

}
