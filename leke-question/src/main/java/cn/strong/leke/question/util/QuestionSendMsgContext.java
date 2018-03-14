package cn.strong.leke.question.util;

import java.util.Arrays;
import java.util.List;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.constant.RepoUsedCst;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.model.monitor.ResourceUsedDetail;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.repository.ResourceTypes;
import cn.strong.leke.model.user.User;

public class QuestionSendMsgContext {
	private final static MessageSender resourceUsedSender = SpringContextHolder.getBean("resourceUsedSender");

	public static void usedQuestion(List<Long> questionIds, User user) {
		List<ResourceUsedDetail> details = ListUtils.newArrayList();
		for (Long questionId : questionIds) {
			ResourceUsedDetail detail = newResourceUsedDetail(questionId, user);
			detail.setUsedType(RepoUsedCst.USED);
			details.add(detail);
		}
		if (CollectionUtils.isNotEmpty(details)) {
			resourceUsedSender.send(details);
		}
	}

	public static void downQuestion(Long questionId, User user) {
		ResourceUsedDetail detail = newResourceUsedDetail(questionId, user);
		detail.setUsedType(RepoUsedCst.DOWN);
		resourceUsedSender.send(Arrays.asList(detail));
	}

	public static void editQuestion(Long questionId, User user) {
		ResourceUsedDetail detail = newResourceUsedDetail(questionId, user);
		detail.setUsedType(RepoUsedCst.EDIT);
		resourceUsedSender.send(Arrays.asList(detail));
	}

	private static ResourceUsedDetail newResourceUsedDetail(Long questionId, User user) {
		ResourceUsedDetail detail = new ResourceUsedDetail();
		Long userId = user.getId();
		Long schoolId = user.getCurrentSchool().getId();
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		detail.setSchoolStageId(question.getSchoolStageId());
		detail.setSubjectId(question.getSubjectId());
		detail.setSchoolId(question.getSchoolId());
		detail.setResId(questionId);
		detail.setResCreateBy(question.getCreatedBy());
		detail.setResType(ResourceTypes.QUESTION);
		detail.setShareSchool(question.getShareSchool());
		detail.setSharePlatform(question.getSharePlatform());
		detail.setCreatedSchoolId(schoolId);
		detail.setCreatedBy(userId);
		return detail;
	}
}
