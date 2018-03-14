package cn.strong.leke.homework.service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.*;
import cn.strong.leke.homework.model.query.ApiStuExamQuery;
import cn.strong.leke.homework.model.query.ApiTeaExamQuery;
import cn.strong.leke.homework.model.query.ExamStuQuery;
import cn.strong.leke.homework.model.query.ExamTeaQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在线考试接口层
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-18 13:56:07
 */
public interface IExamService {

	/**
	 * 查询老师在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.HomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 14:39
	 * @Param [query, page]
	 */
	List<HomeworkDTO> queryTeacherExamList(ExamTeaQuery query, Page page);

	/**
	 * 查询学生在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.MyHomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/27 14:02
	 * @Param [query, page]
	 */
	List<MyHomeworkDTO> queryStuOnlineExamListData(ExamStuQuery query, Page page);

	/**
	 * 根据homeworkId修改考试开始时间
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 15:50
	 * @Param [homeworkId, startTime]
	 */
	void updateTeacherExamDate(Long homeworkId, Date startTime);

	/**
	 * 在线考试定时任务提醒
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/19 17:02
	 * @Param []
	 */
	void excuteOnlineExam();

	/**
	 * 老师在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.Homework>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 14:46
	 * @Param [query]
	 */
	List<Homework> queryTeaOnlineExamList(ApiTeaExamQuery query);

	/**
	 * 老师在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 14:59
	 * @Param [userId]
	 */
	Map<String, Long> queryTeaOnlineExamTotal(Long teacherId);

	/**
	 * 学生在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.ApiStudentHomeworkListDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:13
	 * @Param [query]
	 */
	List<ApiStudentHomeworkListDTO> queryStuOnlineExamList(ApiStuExamQuery query);

	/**
	 * 学生在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:13
	 * @Param [studentId]
	 */
	Map<String, Long> queryStuOnlineExamTotal(Long studentId);
}
