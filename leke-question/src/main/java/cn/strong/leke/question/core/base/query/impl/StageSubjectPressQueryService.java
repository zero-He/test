package cn.strong.leke.question.core.base.query.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.question.PressContext;
import cn.strong.leke.question.core.base.query.IStageSubjectPressQueryService;
import cn.strong.leke.question.dao.mybatis.base.IStageSubjectPressDao;
import cn.strong.leke.question.model.base.StageSubjectPress;
import cn.strong.leke.remote.model.question.PressRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

@Service
public class StageSubjectPressQueryService implements IStageSubjectPressQueryService {

	@Resource
	private IStageSubjectPressDao stageSubjectPressDao;

	public List<StageSubjectPress> findAll() {
		List<StageSubjectPress> assocs = stageSubjectPressDao.findAll();
		return ListUtils.map(assocs, assoc->{
			setting(assoc);
			return assoc;
		});
	}

	private void setting(StageSubjectPress assoc) {
		Long schoolStageId = assoc.getSchoolStageId();
		Long subjectId = assoc.getSubjectId();
		Long pressId = assoc.getPressId();
		SchoolStageRemote stage = SchoolStageContext.getSchoolStage(schoolStageId);
		if (stage != null) {
			assoc.setSchoolStageName(stage.getSchoolStageName());
		}
		SubjectRemote subject = SubjectContext.getSubject(subjectId);
		if (subject != null) {
			assoc.setSubjectName(subject.getSubjectName());
		}
		PressRemote press = PressContext.getPress(pressId);
		if (press != null) {
			assoc.setPressName(press.getPressName());
		}

	}

}
