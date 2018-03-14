package cn.strong.leke.question.model.material;

import cn.strong.leke.model.BaseModel;

public class TeacherMaterialRecord extends BaseModel {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 7185622761520824493L;

	private Long teacherMaterialRecordId;
	private Long materialFileId;// 电子教材附件ID
	private Long teacherId;
	private Long curPage;// 当前浏览页数

	public Long getTeacherMaterialRecordId() {
		return teacherMaterialRecordId;
	}

	public void setTeacherMaterialRecordId(Long teacherMaterialRecordId) {
		this.teacherMaterialRecordId = teacherMaterialRecordId;
	}

	public Long getMaterialFileId() {
		return materialFileId;
	}

	public void setMaterialFileId(Long materialFileId) {
		this.materialFileId = materialFileId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getCurPage() {
		return curPage;
	}

	public void setCurPage(Long curPage) {
		this.curPage = curPage;
	}

}
