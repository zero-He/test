/**
 * 
 */
package cn.strong.leke.question.model.workbook;

import cn.strong.leke.repository.common.model.BaseFamousSchoolRecord;

/**
 * 名师习题册关联
 * 
 * @author liulongbiao
 *
 */
public class FamousSchoolWorkbook extends BaseFamousSchoolRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6237239359881591892L;
	private Long famousSchoolWorkbookId; // 关联主键
	private Long workbookId; // 习题册ID

	public Long getFamousSchoolWorkbookId() {
		return famousSchoolWorkbookId;
	}

	public void setFamousSchoolWorkbookId(Long famousSchoolWorkbookId) {
		this.famousSchoolWorkbookId = famousSchoolWorkbookId;
	}

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

}
