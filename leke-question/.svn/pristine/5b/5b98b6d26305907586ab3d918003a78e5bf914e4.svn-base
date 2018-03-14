/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.common.utils.Booleans;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.user.User;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.question.core.question.cmd.store.ILekeBoutiqueQuestionHandler;
import cn.strong.leke.question.dao.mybatis.IQuestionFeedbackDao;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.QuestionKnowledgeDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionCheckPendingDao;
import cn.strong.leke.question.model.question.QuestionCheckPending;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.repository.common.utils.RepoUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class LekeBoutiqueQuestionHandler implements ILekeBoutiqueQuestionHandler {
	private static final String ERR_NOT_EXIST = "que.question.not.exist";
	@Resource
	private QuestionDao questionDao;
	@Resource
	private QuestionKnowledgeDao questionKnowledgeDao;
	@Resource
	private IQuestionFeedbackDao questionFeedbackDao;
	@Resource
	private IQuestionCheckPendingDao questionCheckPendingDao;
	@Resource
	private IUserRemoteService userRemoteService;

	@Override
	@Transactional
	public void add(Long questionId, int status, String editLog, User user) {
		QuestionDTO question = questionDao.getQuestion(questionId);
		Validation.notNull(question, ERR_NOT_EXIST);
		Long roleId = user.getCurrentRole().getId();
		if (RoleCst.RESEARCHER.equals(roleId) && !question.getSchoolId().equals(SchoolCst.LEKE)) {
			return;
		}
		Validation.isTrue(question.getSchoolId().equals(SchoolCst.LEKE), "该资源无法添加到乐课精品库");
		before(question, status);
		questionDao.addToLekeBoutique(questionId, user.getId());
		if (CheckStatus.INIT != status) {
			List<QuestionKnowledge> knowledges = questionKnowledgeDao.findQuestionKnowledgeByQuestionId(questionId);
			if (CollectionUtils.isEmpty(knowledges)) {
				status = CheckStatus.INIT;
			}
		}
		if (CheckStatus.INIT == status) {
			addQuestionCheckPending(question);
		}
		updateStatus(questionId, status, null, user);
		QuestionContext.deleteCache(questionId);
	}


	/**
	 *
	 * 描述: 审核通过判断知识点和难易度是否存在
	 *
	 * @author raolei
	 * @return void
	 */
	private void before(QuestionDTO question, int status) {
		if (CheckStatus.CHECKED == status) {
			if (question.getDifficulty() == null) {
				throw new ValidateException("que.question.no.difficulty");
			}

		}
	}

	@Override
	@Transactional
	public int remove(Long questionId, User user) {
		delQuestionCheckPending(questionId);
		return questionDao.removeFromLekeBoutique(questionId, user.getId());
	}

	@Override
	@Transactional
	public void override(Long oldId, Long newId, User user) {
		remove(oldId, user);
		String editLog = RepoUtils.getEditLog(user);
		Long roleId = user.getCurrentRole().getId();
		if (RoleCst.RESEARCHER.equals(roleId)) {
			add(newId, CheckStatus.CHECKED, editLog, user);
		} else {
			add(newId, CheckStatus.INIT, editLog, user);
		}

	}

	@Override
	@Transactional
	public void checkEdit(Long oldId, Long newId, User user) {
		QuestionDTO old = questionDao.getQuestion(oldId);
		Validation.notNull(old, ERR_NOT_EXIST);

		String editLog = RepoUtils.getEditLog(user);
		// 习题审核编辑不会生成新的题
		add(oldId, CheckStatus.CHECKED, editLog, user);
	}

	private void updateStatus(Long questionId, int status, String editLog, User user) {
		int qs = Question.QUE_STATUS_INPUT;
		switch (status) {
		case CheckStatus.CHECKED:
			qs = Question.QUE_STATUS_CHECKED;
			break;
		case CheckStatus.EDITED:
			qs = Question.QUE_STATUS_CHECKED_EDITED;
			break;
		case CheckStatus.REJECTED:
			qs = Question.QUE_STATUS_REJECT_INPUT;
			break;
		default:
			qs = Question.QUE_STATUS_INPUT;
			break;
		}

		Question update = new Question();
		update.setQuestionId(questionId);
		update.setQuestionStatus(qs);
		update.setModifiedBy(user.getId());
		questionDao.updateQuestionStatus(update);
	}

	private void addCheckLog(Long questionId, int fromStatus, int toStatus, String checkNote,
			User user) {
	}

	@Override
	@Transactional
	public int checkPass(Long resId, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional
	public int checkReject(Long resId, String checkNote, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doCorrect(Long resId, User user) {
		Long userId = user.getId();
		sendAward(resId);
		questionFeedbackDao.doCorrect(resId, userId);
	}

	private void sendAward(Long questionId) {
		List<Long> userIds = questionFeedbackDao.findCorrectUserIdsFeedback(questionId);
		if (CollectionUtils.isNotEmpty(userIds)) {
			List<UserRemote> userRemotes = userRemoteService.findUserListByUserIds(userIds);
			List<DynamicInfo> dynamicInfos = ListUtils.newArrayList();
			userRemotes.forEach(n -> {
				DynamicInfo dynamicInfo = new DynamicInfo();
				dynamicInfo.setTypeId(IncentiveTypes.TEACHER.REP_LOOKERR);
				dynamicInfo.setUserId(n.getId());
				dynamicInfo.setUserName(n.getUserName());
				dynamicInfo.setRoleId(RoleCst.TEACHER);
				dynamicInfos.add(dynamicInfo);
				
				LetterMessage message = new LetterMessage(String.valueOf(n.getId()), "纠错处理提醒");
				message.setContent(ParametersContext.getString("FEEDBACK_TEMPLATE"));
				message.addVariable("name", "习题");
				NoticeHelper.send(message);
			});
			
			DynamicHelper.publish(dynamicInfos);
		}
	}

	@Override
	public boolean contains(Long resId) {
		if (resId == null) {
			return false;
		}
		QuestionDTO old = questionDao.getQuestion(resId);
		return old != null && Booleans.isTrue(old.getSharePlatform())
				&& old.getSchoolId().equals(SchoolCst.LEKE);
	}

	@Override
	public void up(Long resId, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public int down(Long resId, User user) {
		return 0;
	}

	/**
	 *
	 * 描述: 习题冗余到待审核的表
	 *
	 * @author raolei
	 * @created 2016年12月4日 上午10:18:51
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	private void addQuestionCheckPending(QuestionDTO question) {
		QuestionCheckPending assoc = new QuestionCheckPending();
		assoc.setQuestionId(question.getQuestionId());
		assoc.setSchoolStageId(question.getSchoolStageId());
		assoc.setSubjectId(question.getSubjectId());
		questionCheckPendingDao.add(assoc);
	}

	/**
	 *
	 * 描述: 删除习题冗余的待审核的表的记录
	 *
	 * @author raolei
	 * @created 2016年12月4日 上午10:21:22
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	private void delQuestionCheckPending(Long questionId) {
		questionCheckPendingDao.del(questionId);
	}

}
