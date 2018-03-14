package cn.strong.leke.question.core.material.cmd;

import cn.strong.leke.question.model.material.TeacherMaterialRecord;

public interface ITeacherMaterialRecordHandler {

	void updateCurPage(TeacherMaterialRecord assoc);

	/**
	 *
	 * 描述: 更新用户电子教材浏览记录
	 *
	 * @author raolei
	 * @created 2017年2月16日 上午11:35:54
	 * @since v1.0.0
	 * @param assoc
	 * @param user
	 * @return void
	 */
	void save(TeacherMaterialRecord assoc);
}
