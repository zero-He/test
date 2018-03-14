package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.lesson.SchoolLessonDto;
import cn.strong.leke.monitor.core.model.lesson.TeacherLessonDto;

/**
 * 
 * @author hedan
 *
 */
public interface ILessonBehaviorService {

//	//学校课堂功能统计
	List<TeacherLessonDto> schoolLessonStatistics(TeacherLessonDto query,Page page);
	
	List<TeacherLessonDto> schoolLessondetail(TeacherLessonDto query);
	
	List<TeacherLessonDto>  teacherLessonStatistics(TeacherLessonDto query,Page page);
	
	List<TeacherLessonDto> teacherLessonDetail(TeacherLessonDto query);
	
	
	List<TeacherLessonDto> queryWholeNetwork(SchoolLessonDto query);
	
	List<TeacherLessonDto> getGradeNameBySchool(TeacherLessonDto query);
	
	List<TeacherLessonDto> getSubjectNameBySchool(TeacherLessonDto query);
	
	
}
