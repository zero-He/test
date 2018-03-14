/**
 * 
 */
package cn.strong.leke.question.model.workbook;

import cn.strong.leke.repository.common.model.BaseFamousTeacherRecord;

/**
 * 名师习题册
 * 
 * @author liulongbiao
 *
 */
public class FamousTeacherWorkbook extends BaseFamousTeacherRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3341768066747177977L;
	private Long famousTeacherWorkbookId; // 关联主键
	private Long workbookId; // 习题册ID

	public Long getFamousTeacherWorkbookId() {
		return famousTeacherWorkbookId;
	}

	public void setFamousTeacherWorkbookId(Long famousTeacherWorkbookId) {
		this.famousTeacherWorkbookId = famousTeacherWorkbookId;
	}

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

}
