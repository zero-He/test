package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import cn.strong.leke.homework.model.ExportStatus;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.query.ClassHomeworkQuery;

public interface HomeworkStatusExportDao {
	
	List<ExportStatus> findExportStatus(ClassHomeworkQuery query);
	
	Integer countExportStatus(ClassHomeworkQuery query);
	
	List<Homework> findHomeWorkByClassId(ClassHomeworkQuery query );

}
