/**
 * 
 */
package cn.strong.leke.question.model.workbook;

import cn.strong.leke.repository.common.model.BaseCheckLog;

/**
 * 名师习题册审核日志
 * 
 * @author liulongbiao
 *
 */
public class FamousTeacherWorkbookCheckLog extends BaseCheckLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3160136195670242385L;
	private Long workbookId; // 习题册ID
	private Long teacherId; // 名师ID

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

}
