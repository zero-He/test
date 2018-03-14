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

import cn.strong.leke.model.question.Material;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.remote.service.question.IMaterialRemoteService;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年6月10日 下午3:12:38
 * @since   v1.0.0
 */
@Service
public class MaterialRemoteService implements IMaterialRemoteService {
	
	private static final long SHIKE_SCHOOLSTAGEID = 4;
	
	@Autowired
	private MaterialService materialService;

	@Override
	public List<Material> findMaterials() {
		return materialService.queryMaterials(null, null);
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.remote.service.question.IMaterialRemoteService#findMaterialsForShike()
	 */
	@Override
	public List<Material> findMaterialsForShike(Long schoolStageId, Long gradeId, Long subjectId) {
		Material material = new Material();
		material.setSchoolStageId(schoolStageId);
		material.setGradeId(gradeId);
		material.setSubjectId(subjectId);
		return materialService.queryMaterials(material, null);
	}
	
	@Override
	public List<Material> findMaterialsForShike() {
		Material material = new Material();
		material.setSchoolStageId(SHIKE_SCHOOLSTAGEID);
		return materialService.queryMaterials(material, null);
	}

}
