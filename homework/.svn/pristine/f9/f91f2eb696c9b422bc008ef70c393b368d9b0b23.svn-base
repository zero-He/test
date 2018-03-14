package cn.strong.leke.homework.service;

import java.util.List;
import java.util.Map;

import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.query.ClassHomeworkQuery;

public interface HomeworkStatusExportService {
	
	public static final String STUDENT_NAME = "studentName";
	/**
	 * 
	 * @param classId
	 * @param export 是否导出
	 * @return
	 */
	List<Map<String, Object>> getStatusData(ClassHomeworkQuery query,boolean export);
	
	List<Homework> getHomeWork(ClassHomeworkQuery query);
	
}
