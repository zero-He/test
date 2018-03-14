package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.util.Objects;

import cn.strong.leke.model.base.Clazz;

public class ClazzBean implements Serializable {
	
	private static final long serialVersionUID = 6465490635211726799L;

	private Long classId; // 班级ID
	
	private String className; // 班级名称

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof ClazzBean))
			return false;
		if(this.getClassId().equals(((ClazzBean)obj).getClassId()) &&
		   this.getClassName().equals(((ClazzBean)obj).getClassName())) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.getClassId(), this.getClassName());
	}
	
}
