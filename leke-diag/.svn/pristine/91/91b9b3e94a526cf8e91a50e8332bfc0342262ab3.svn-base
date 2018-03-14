package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.util.Objects;

import cn.strong.leke.diag.dao.diag.model.ExamReportDtl;

public class SubjectBean implements Serializable{
	
	private static final long serialVersionUID = -9063657964642601504L;

	private Long subjectId;
	
	private String subjectName;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof SubjectBean))
			return false;
		if(this.getSubjectId().equals(((SubjectBean)obj).getSubjectId()) &&
		   this.getSubjectName().equals(((SubjectBean)obj).getSubjectName())) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.getSubjectName(), this.getSubjectId());
	}
	
}
