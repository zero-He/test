package cn.strong.leke.question.core.base.query.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.MaterialNodeJoin;
import cn.strong.leke.question.core.base.query.IMaterialNodeJoinQueryService;
import cn.strong.leke.question.dao.mybatis.base.IMaterialNodeJoinDao;

@Service
public class MaterialNodeJoinQueryService implements IMaterialNodeJoinQueryService {

	@Resource
	private IMaterialNodeJoinDao materialNodeJoinDao;
	@Override
	public List<MaterialNodeJoin> findMaterialNodeJoins(Long origMaterialNodeId) {
		if (origMaterialNodeId == null) {
			return Collections.emptyList();
		}
		return materialNodeJoinDao.findMaterialNodeJoins(origMaterialNodeId);
	}

}
