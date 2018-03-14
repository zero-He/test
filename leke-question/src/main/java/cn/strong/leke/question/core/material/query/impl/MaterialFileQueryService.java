package cn.strong.leke.question.core.material.query.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.querys.MaterialFilter;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.material.query.IMaterialFileQueryService;
import cn.strong.leke.question.core.material.query.ITeacherMaterialRecordQueryService;
import cn.strong.leke.question.dao.mybatis.material.IMaterialFileDao;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.question.model.material.MaterialFile;
import cn.strong.leke.question.model.material.TeacherMaterialRecord;

@Service
public class MaterialFileQueryService implements IMaterialFileQueryService {

	@Resource
	private IMaterialFileDao materialFileDao;
	@Resource
	private ITeacherMaterialRecordQueryService teacherMaterialRecordQueryService;

	@Override
	public MaterialFile get(Long materialFileId) {
		return materialFileDao.get(materialFileId);
	}

	@Override
	public MaterialFile getByMaterialId(Long materialId) {
		return materialFileDao.getByMaterialId(materialId);
	}

	@Override
	public List<MaterialDTO> findTeacherMaterialFile(MaterialFilter filter, User user) {
		Long userId = user.getId();
		List<Material> materials = MaterialContext.findMaterials(filter);
		List<MaterialDTO> dtos = ListUtils.newArrayList();
		for (Material m : materials) {
			Long materialId = m.getMaterialId();
			MaterialFile file = materialFileDao.getByMaterialId(materialId);
			if (file != null) {
				MaterialDTO dto = new MaterialDTO();
				BeanUtils.copyProperties(dto, m);
				dto.setMaterialFileId(file.getMaterialFileId());
				dto.setFileId(file.getFileId());
				dto.setPageCount(file.getPageCount());
				TeacherMaterialRecord record = teacherMaterialRecordQueryService.get(userId, file.getMaterialFileId());
				if (record != null) {
					dto.setCurPage(record.getCurPage());
				} else {
					dto.setCurPage(0L);
				}
				dtos.add(dto);
			}
		}
		Collections.sort(dtos, Ord.DESC);
		return dtos;
	}

	private static enum Ord implements Comparator<MaterialDTO> {
		DESC;
		@Override
		public int compare(MaterialDTO o1, MaterialDTO o2) {
			if (o1.getCurPage() == null) {
				o1.setCurPage(0L);
			}
			if (o2.getCurPage() == null) {
				o2.setCurPage(0L);
			}
			return o2.getCurPage().compareTo(o1.getCurPage());
		}
	};

}
