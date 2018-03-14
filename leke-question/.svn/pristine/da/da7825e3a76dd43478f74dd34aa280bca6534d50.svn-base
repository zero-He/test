package cn.strong.leke.question.core.material.query;

import java.util.List;

import cn.strong.leke.model.question.querys.MaterialFilter;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.question.model.material.MaterialFile;

public interface IMaterialFileQueryService {

	MaterialFile get(Long materialFileId);

	MaterialFile getByMaterialId(Long materialId);

	List<MaterialDTO> findTeacherMaterialFile(MaterialFilter filter, User user);

}
