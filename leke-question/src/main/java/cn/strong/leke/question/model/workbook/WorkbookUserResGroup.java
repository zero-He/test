package cn.strong.leke.question.model.workbook;

import cn.strong.leke.model.BaseModel;

/**
 *
 * 习题册和用户资源分组关联表
 *
 * @author liulongbiao
 */
public class WorkbookUserResGroup extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8704730288794324299L;
	private Long assocId; // 关联主键
	private Long workbookId; // 习题册ID
	private Long userResGroupId; // 用户资源分组ID

	public Long getAssocId() {
		return assocId;
	}

	public void setAssocId(Long assocId) {
		this.assocId = assocId;
	}

	public Long getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(Long workbookId) {
		this.workbookId = workbookId;
	}

	public Long getUserResGroupId() {
		return userResGroupId;
	}

	public void setUserResGroupId(Long userResGroupId) {
		this.userResGroupId = userResGroupId;
	}

}
