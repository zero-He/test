package cn.strong.leke.monitor.core.dao.mybatis;


import java.util.List;
import cn.strong.leke.monitor.core.model.lesson.TeacherLessonDto;


/**
 * @Description: 课堂统计数据层接口
 * @author hedan
 *
 */
public interface ILessonBehaviorDao {
	
	
	TeacherLessonDto getTeacherLessonDto(int id);
	
	List<Long> getCourseSingleIdBySchool(TeacherLessonDto query);
	
	List<TeacherLessonDto> getGradeNameBySchool(TeacherLessonDto query);
	
	List<TeacherLessonDto> getSubjectNameBySchool(TeacherLessonDto query);
}
