package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

public class ClassSubject extends Clazz {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SubjectRemote> subjectList;

	public List<SubjectRemote> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectRemote> subjectList) {
		this.subjectList = subjectList;
	}
	
	
}
