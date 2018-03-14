package cn.strong.leke.question.model.workbook;

import cn.strong.leke.model.repository.RepositoryOperation;

public class WorkbookOperation extends RepositoryOperation {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 6038449176384692459L;

	private Long workbookId;

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

}
