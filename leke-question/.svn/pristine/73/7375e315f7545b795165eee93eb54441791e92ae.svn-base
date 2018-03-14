/**
 * 
 */
package cn.strong.leke.question.util;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.user.User;

/**
 * @author liulongbiao
 *
 */
public class WorkbookHelper {

	/**
	 * 是否具有习题册写入权限
	 * 
	 * @param workbook
	 * @param user
	 * @return
	 */
	public static boolean hasWorkbookWritePermission(Workbook workbook, User user) {
		Long roleId = user.getCurrentRole().getId();
		if (roleId.equals(RoleCst.RESEARCHER) || roleId.equals(RoleCst.QUESTION_ADMIN)) {
			return true;
		}
		if (user.getId().equals(workbook.getCreatedBy())) {
			return true;
		}
		if (workbook.getShareSchool().equals(true)
				&& workbook.getSchoolId().equals(user.getCurrentSchool().getId())) {
			return true;
		}
		return false;
	}
}
