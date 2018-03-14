package cn.strong.leke.question.remote;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.model.question.MaterialNodeJoin;
import cn.strong.leke.question.core.base.query.IMaterialNodeJoinQueryService;
import cn.strong.leke.remote.service.question.IMaterialNodeJoinRemoteService;

@Service
public class MaterialNodeJoinRemoteService implements IMaterialNodeJoinRemoteService {
	@Resource
	private IMaterialNodeJoinQueryService materialNodeJoinQueryService;

	@Override
	public List<Long> findMaterialNodeJoins(Long materialNodeId) {
		if (materialNodeId == null) {
			return Collections.emptyList();
		}
		List<MaterialNodeJoin> mnjs = materialNodeJoinQueryService.findMaterialNodeJoins(materialNodeId);
		return ListUtils.map(mnjs, mnj -> {
			return mnj.getDestMaterialNodeId();
		});
	}

}
