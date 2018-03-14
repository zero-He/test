package cn.strong.leke.homework.service;

public interface IWeekMonthHomeworkService {

	/**
	 * 生成一个班级学生的作业
	 * @param classId
	 */
	void saveClassStuWrongHomework(Long classId,Long schoolId,Integer type);
}
