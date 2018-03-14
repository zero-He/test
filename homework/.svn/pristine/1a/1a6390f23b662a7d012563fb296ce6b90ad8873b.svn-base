package cn.strong.leke.homework.dao.mybatis;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.*;
import cn.strong.leke.homework.model.query.ApiStuExamQuery;
import cn.strong.leke.homework.model.query.ApiTeaExamQuery;
import cn.strong.leke.homework.model.query.ExamStuQuery;
import cn.strong.leke.homework.model.query.ExamTeaQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在线考试Dao层
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-18 13:57:01
 */
public interface ExamDao {

	/**
	 * 查询老师在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.HomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 14:44
	 * @Param [query, page]
	 */
	List<HomeworkDTO> selectTeacherExamList(ExamTeaQuery query, Page page);

	/**
	 * 查询学生在线考试列表数据
	 * @return java.util.List<cn.strong.leke.homework.model.MyHomeworkDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/27 14:03
	 * @Param [query, page]
	 */
	List<MyHomeworkDTO> selectStuOnlineExamListData(ExamStuQuery query, Page page);

	/**
	 * 老师根据homeworkId修改考试开始和截止时间
	 * @return void
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/18 16:22
	 * @Param [startTime, closeTime]
	 */
	void updateTeacherExamDate(@Param("homeworkId") Long homeworkId, @Param("startTime") Date startTime, @Param("closeTime") Date closeTime);

	/**
	 * 扫描出所有用于在线考试的试卷
	 * @return java.util.List<cn.strong.leke.homework.model.ExamDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/22 9:34
	 * @Param []
	 */
	List<ExamDTO> selectTeaExamPaperList();

	/**
	 * 根据homeworkId查所有考试的学生
	 * @return java.util.List<java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/22 11:27
	 * @Param [homeworkId]
	 */
	List<String> selectStuExamPaperList(Long homeworkId);

	/**
	 * 老师在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.Homework>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 14:47
	 * @Param [query]
	 */
	List<Homework> selectTeaOnlineExamList(ApiTeaExamQuery query);

	/**
	 * 老师在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 15:02
	 * @Param [userId]
	 */
	Map<String, Long> selectTeaOnlineExamTotal(Long teacherId);

	/**
	 * 学生在线考试列表（API）
	 * @return java.util.List<cn.strong.leke.homework.model.ApiStudentHomeworkListDTO>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:16
	 * @Param [query]
	 */
	List<ApiStudentHomeworkListDTO> selectStuOnlineExamList(ApiStuExamQuery query);

	/**
	 * 学生在线考试列表总数（API）
	 * @return java.util.Map<java.lang.String,java.lang.Long>
	 * @Author LIU.SHITING
	 * @Version 2.7
	 * @Date 2017/5/24 17:17
	 * @Param [studentId]
	 */
	Map<String, Long> selectStuOnlineExamTotal(Long studentId);
}
