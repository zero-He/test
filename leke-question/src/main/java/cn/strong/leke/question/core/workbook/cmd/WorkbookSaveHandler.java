/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd;

import cn.strong.leke.context.repository.RepoRoles;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.repository.RepoCsts.SaveActions;
import cn.strong.leke.model.user.User;
import cn.strong.leke.repository.common.cmd.BaseRepoSaveHandler;
import cn.strong.leke.repository.common.model.RepoSaveContext;

/**
 * 习题册保存处理器
 * 
 * @author liulongbiao
 *
 */
public class WorkbookSaveHandler extends BaseRepoSaveHandler<Workbook> {

	private static final String ERR_INVALID = "que.workbook.info.incomplete";
	private static final String ERR_NOT_EXIST = "que.workbook.not.exist";

	@Override
	protected void validate(Workbook wb) {
		Validation.notNull(wb, ERR_INVALID);
		Validation.notNull(wb.getSubjectId(), ERR_INVALID);
		Validation.hasText(wb.getWorkbookName(), ERR_INVALID);
	}

	@Override
	protected Workbook setCreator(Workbook data, User user) {
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
	protected Workbook copyCreator(Workbook oldData, Workbook newData) {
		Validation.notNull(oldData, ERR_NOT_EXIST);
		newData.setCreatedBy(oldData.getCreatedBy());
		newData.setCreatorName(oldData.getCreatorName());
		newData.setSchoolId(oldData.getSchoolId());
		newData.setSchoolName(oldData.getSchoolName());
		return newData;
	}

	@Override
	protected void dispathSave(RepoSaveContext<Workbook> ctx) {
		String action = ctx.getAction();
		if (action == null || action.equals(SaveActions.CREATE)) {
			ctx.setAction(SaveActions.CREATE);
			super.dispathSave(ctx);
		} else {
			throw new UnsupportedOperationException("习题册不支持创建以外的操作");
		}
	}

	@Override
	protected void doCreateLekeBoutique(RepoSaveContext<Workbook> ctx) {
		Long roleId = ctx.getCurrentRoleId();
		if (!roleId.equals(RoleCst.RESEARCHER)) {
			Validation.isForbidden(!RepoRoles.isDefaultSharePlatform(roleId));
		}
		createAsNewCreator(ctx);
		int status = roleId.equals(RoleCst.RESEARCHER) ? CheckStatus.CHECKED : CheckStatus.INIT;
		addLekeBoutiqueRecord(ctx, status);
	}

	@Override
	protected void saveInPersonal(RepoSaveContext<Workbook> ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doSaveAsPersonal(RepoSaveContext<Workbook> ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void afterSave(RepoSaveContext<Workbook> ctx) {
		// TODO Auto-generated method stub
		super.afterSave(ctx);
	}
}
