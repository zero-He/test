package cn.strong.leke.diag.manage;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.dao.lesson.mongo.LessonBehaviorDao;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;

@Component
public class LessonBehaviorService {

	@Resource
	private LessonBehaviorDao lessonBehaviorDao;

	/**
	 * 获取一个课堂中学生行为列表
	 * @param courseSingleId
	 * @return
	 */
	public List<StudentBehavior> findStudentBehaviorByLessonId(Long courseSingleId) {
		return this.lessonBehaviorDao.findStudentBehaviorByLessonId(courseSingleId);
	}

	/**
	 * 获取学生一段时间内的行为列表。
	 * @param studentId
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 */
	public List<LessonBehavior> findStudentBehaviorByStudentId(Long studentId, Date start, Date end, Page page) {
		return this.lessonBehaviorDao.findStudentBehaviorByStudentId(studentId, start, end, page);
	}

	/**
	 * 获取课堂行为信息
	 * @param lessonId
	 * @return
	 */
	public LessonBehavior getLessonBehaviorByLessonId(Long courseSingleId) {
		return this.lessonBehaviorDao.getLessonBehaviorByLessonId(courseSingleId);
	}
}
