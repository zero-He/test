/**
 * 
 */
package cn.strong.leke.monitor.core.service.course;

import java.util.List;

import cn.strong.leke.model.monitor.msg.CourseAttendMsg;
import cn.strong.leke.model.monitor.msg.CourseOnlineMsg;
import cn.strong.leke.model.monitor.msg.CourseTableChangeMsg;
import cn.strong.leke.model.monitor.msg.CourseTeacherStatusMsg;
import cn.strong.leke.model.monitor.msg.CsetStusChangeMsg;
import cn.strong.leke.monitor.core.model.CurrentPlatformCourseStat;

/**
 * 课堂相关服务接口
 * 
 * @author liulongbiao
 *
 */
public interface ICourseService {

	/**
	 * 处理套课学生名单变更事件
	 * 
	 * @param msg
	 */
	void handle(CsetStusChangeMsg msg);

	/**
	 * 处理课表变更消息
	 * 
	 * @param msgs
	 */
	void handleCourseTableChangeMsgs(List<CourseTableChangeMsg> msgs);

	/**
	 * 处理课堂在线消息
	 * 
	 * @param msgs
	 */
	void handleCourseOnlineMsgs(List<CourseOnlineMsg> msgs);

	/**
	 * 处理课堂结束考勤消息
	 * 
	 * @param msg
	 */
	void handle(CourseAttendMsg msg);

	/**
	 * 处理课堂教师在线状态消息
	 * 
	 * @param msg
	 */
	void handle(CourseTeacherStatusMsg msg);

	/**
	 * 更新某天的平台课堂日统计
	 * 
	 * @param day
	 */
	void updateCoursePlatformDaily(int day);

	/**
	 * 获取当前平台课堂统计
	 * 
	 * @return
	 */
	CurrentPlatformCourseStat getCurrentPlatformCourseStat();
}
