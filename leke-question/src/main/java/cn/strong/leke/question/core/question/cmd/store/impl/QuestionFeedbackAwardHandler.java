package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionFeedbackAwardHandler;
import cn.strong.leke.question.dao.mybatis.IQuestionFeedbackDao;

@Service
public class QuestionFeedbackAwardHandler implements IQuestionFeedbackAwardHandler {
	@Resource
	private IQuestionFeedbackDao questionFeedbackDao;

	@Override
	public void sendAward(Long questionId, User user) {
		// List<Long> userIds =
		// questionFeedbackDao.findCorrectUserIds(questionId);
		// DynamicInfo dynamicInfo = new DynamicInfo();
		// dynamicInfo.setTypeId(IncentiveTypes.TEACHER.BK_CW_BEFORE);
		// dynamicInfo.setDynamicType(DynamicTypes.BK_FAVORITE_CW);
		// dynamicInfo.setTitle(courseware.getName());
		// dynamicInfo.setUserId(user.getId());
		// dynamicInfo.setUserName(user.getUserName());
		// dynamicInfo.setRoleId(RoleCst.TEACHER);
		// dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
		// Award award = DynamicHelper.publish(dynamicInfo);
	}

}
