/**
 * 
 */
package cn.strong.leke.monitor.db.tutor.model;

/**
 * 按学校性质进行学校数量统计
 * 
 * @author liulongbiao
 *
 */
public class NatureSchoolCount {
	private Integer schoolNature;
	private Integer cnt;

	public Integer getSchoolNature() {
		return schoolNature;
	}

	public void setSchoolNature(Integer schoolNature) {
		this.schoolNature = schoolNature;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

}
