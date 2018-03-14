package cn.strong.leke.question.model.workbook;

import cn.strong.leke.repository.common.model.BaseSchoolRecord;

/**
 *
 * 描述: 学校习题册
 *
 * @author raolei
 * @created 2016年6月13日 下午1:52:46
 * @since v1.0.0
 */
public class SchoolWorkbook extends BaseSchoolRecord {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -1737547920109216026L;
	private Long schoolWorkbookId;// 学校习题册主键
	private Long workbookId; // 习题册ID

	public Long getSchoolWorkbookId() {
		return schoolWorkbookId;
	}

	public void setSchoolWorkbookId(Long schoolWorkbookId) {
		this.schoolWorkbookId = schoolWorkbookId;
	}

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

}
