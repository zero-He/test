/**
 * 
 */
package cn.strong.leke.question.core.question.cmd;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.repository.RepoRoles;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.repository.RepoCsts.SaveActions;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionFeedbackAwardHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionWbnodeHandler;
import cn.strong.leke.question.util.QuestionIndexContext;
import cn.strong.leke.repository.common.cmd.BaseRepoSaveHandler;
import cn.strong.leke.repository.common.model.RepoSaveContext;

/**
 * 试题保存处理器
 * 
 * @author liulongbiao
 *
 */
public class QuestionSaveHandler extends BaseRepoSaveHandler<QuestionDTO> {

	private static final String ERR_INVALID = "que.question.info.incomplete";
	private static final String ERR_NOT_EXIST = "que.question.not.exist";

	private IQuestionWbnodeHandler questionWbnodeHandler;
	private IQuestionFeedbackAwardHandler questionFeedbackAwardHandler;

	public void setQuestionWbnodeHandler(IQuestionWbnodeHandler questionWbnodeHandler) {
		this.questionWbnodeHandler = questionWbnodeHandler;
	}

	public void setQuestionFeedbackAwardHandler(IQuestionFeedbackAwardHandler questionFeedbackAwardHandler) {
		this.questionFeedbackAwardHandler = questionFeedbackAwardHandler;
	}



	@Override
	protected void dispathSave(RepoSaveContext<QuestionDTO> ctx) {
		super.dispathSave(ctx);
		Long roleId = ctx.getCurrentRoleId();
		Long newId = ctx.getNewId();
		Long oldId = ctx.getOldId();
		if (newId != null && !newId.equals(oldId)) {
			if (RoleCst.RESEARCHER.equals(roleId)) {
				int status = roleId.equals(RoleCst.RESEARCHER) ? CheckStatus.CHECKED : CheckStatus.INIT;
				super.addLekeBoutiqueRecord(ctx, status);
			}
			// 如果在习题册下，额外维护习题册章节关联
			Long curWbnodeId = ctx.getScope().getCurWbnodeId();
			if (curWbnodeId != null) {
				User user = ctx.getUser();
				questionWbnodeHandler.add(newId, curWbnodeId, user);
				if (oldId != null) {
					questionWbnodeHandler.remove(oldId, curWbnodeId, user);
				}
			}
		}

	}

	@Override
	protected void validate(QuestionDTO data) {
		Validation.notNull(data, ERR_INVALID);
		Validation.notNull(data.getSubjectId(), ERR_INVALID);
	}

	@Override
	protected QuestionDTO setCreator(QuestionDTO data, User user) {
		data.setCreatedBy(user.getId());
		data.setCreatorName(user.getUserName());
		School school = user.getCurrentSchool();
		if (school != null) {
			data.setSchoolId(school.getId());
			data.setSchoolName(school.getName());
		}
		return data;
	}

	@Override
	protected void doOverrideLekeBoutique(RepoSaveContext<QuestionDTO> ctx) {
		Long newId = createAsOldCreator(ctx);
		Long oldId = ctx.getOldId();
		User user = ctx.getUser();
		Long roleId = ctx.getCurrentRoleId();
		Validation.isForbidden(!RepoRoles.isDefaultSharePlatform(roleId) && !RoleCst.RESEARCHER.equals(roleId));
		QuestionDTO question = ctx.getData();
		if (question != null && SchoolCst.LEKE == question.getSchoolId()) {
			// 因为纠错 学校库也有纠错了
			lekeBoutiqueRepoHandler.checkEdit(oldId, null, user);
		}
	}
	
	@Override
	protected void doCheckEditLekeBoutique(RepoSaveContext<QuestionDTO> ctx) {
		Long roleId = ctx.getCurrentRoleId();
		Validation.isForbidden(!RepoRoles.isDefaultSharePlatform(roleId) && !RoleCst.RESEARCHER.equals(roleId));
		Long newId = createAsOldCreator(ctx);
		Long oldId = ctx.getOldId();
		User user = ctx.getUser();
		lekeBoutiqueRepoHandler.checkEdit(oldId, newId, user);
	}

	@Override
	protected QuestionDTO copyCreator(QuestionDTO oldData, QuestionDTO newData) {
		Validation.notNull(oldData, ERR_NOT_EXIST);
		newData.setCreatedBy(oldData.getCreatedBy());
		newData.setCreatorName(oldData.getCreatorName());
		newData.setSchoolId(oldData.getSchoolId());
		newData.setSchoolName(oldData.getSchoolName());
		return newData;
	}

	@Override
	protected void afterSave(RepoSaveContext<QuestionDTO> ctx) {
		Long oldId = ctx.getOldId();
		User user = ctx.getUser();
		Long userId = user.getId();
		Long roleId = user.getCurrentRole().getId();
		String action = ctx.getAction();
		if (StringUtils.isNotEmpty(action)) {
			switch (ctx.getAction()) {
			case SaveActions.CORRECT:
				questionFeedbackAwardHandler.sendAward(oldId, user);
				break;
			case SaveActions.CREATE:
				if ((RoleCst.RESEARCHER.equals(roleId) || RoleCst.INPUTER.equals(roleId)) && oldId != null) {
					QuestionDTO backend = QuestionContext.getQuestionDTO(oldId);
					if (backend != null) {
						Long schoolId = backend.getSchoolId();
						if (schoolId != null && SchoolCst.LEKE == schoolId) {
							lekeBoutiqueRepoHandler.remove(oldId, user);
							personalRepoHandler.remove(oldId, userId, user);
						} else {
							lekeShareRepoHandler.remove(oldId, user);
						}
					}
				}
				break;
			default:
				break;
			}
		}
		QuestionIndexContext.sendUpdateIndex(new Long[] { oldId });
		super.afterSave(ctx);
	}

}
