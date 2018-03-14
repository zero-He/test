package cn.strong.leke.question.core.base.cmd.impl;

import static cn.strong.leke.model.BaseCache.PREFIX_STGSBJ_PRESS;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.question.PressContext;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.question.core.base.cmd.IStageSubjectPressHandler;
import cn.strong.leke.question.dao.mybatis.base.IStageSubjectPressDao;
import cn.strong.leke.question.model.base.StageSubjectPress;

@Service
public class StageSubjectPressHandler implements IStageSubjectPressHandler {

	@Resource
	private IStageSubjectPressDao stageSubjectPressDao;

	@Override
	public void insert(StageSubjectPress assocs) {
		// TODO Auto-generated method stub
		CacheUtils.delete(PREFIX_STGSBJ_PRESS);
	}

	@Override
	public void insertList(List<StageSubjectPress> assocs) {
		if (CollectionUtils.isEmpty(assocs)) {
			throw new ValidateException("请选择要关联的教材版本!");
		}
		Long schoolStageId = assocs.get(0).getSchoolStageId();
		Long subjectId = assocs.get(0).getSubjectId();
		if (schoolStageId == null || subjectId == null) {
			throw new ValidateException("学段学科错误！");
		}
		stageSubjectPressDao.deleteBySchoolStageIdSubjectId(schoolStageId, subjectId);
		assocs.forEach(assoc -> {
			setting(assoc);
		});
		stageSubjectPressDao.insertList(assocs);

		CacheUtils.delete(PREFIX_STGSBJ_PRESS);
	}

	private void setting(StageSubjectPress assoc) {
		Long schoolStageId = assoc.getSchoolStageId();
		Long subjectId = assoc.getSubjectId();
		Long pressId = assoc.getPressId();
		String schoolStageName = SchoolStageContext.getSchoolStage(schoolStageId).getSchoolStageName();
		assoc.setSchoolStageName(schoolStageName);
		String subjectName = SubjectContext.getSubject(subjectId).getSubjectName();
		assoc.setSubjectName(subjectName);
		String pressName = PressContext.getPress(pressId).getPressName();
		assoc.setPressName(pressName);
	}

}
