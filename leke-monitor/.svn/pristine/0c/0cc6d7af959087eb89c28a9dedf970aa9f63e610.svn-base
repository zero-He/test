/**
 * 
 */
package cn.strong.leke.monitor.core.service.online.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.rabbit.core.MQSender;
import cn.strong.leke.model.monitor.msg.CourseOnlineMsg;
import cn.strong.leke.monitor.core.service.online.ILessonOnlineUserService;
import cn.strong.leke.monitor.mongo.online.model.LessonOnlineUser;
import cn.strong.leke.monitor.mongo.online.service.ILessonOnlineUserDao;
import cn.strong.leke.monitor.mq.model.LessonOnlineUserMsg;
import cn.strong.leke.monitor.mq.model.LessonOnlineUserMsg.UserItem;

/**
 * @author liulongbiao
 *
 */
@Service
public class LessonOnlineUserService implements ILessonOnlineUserService {
	@Resource
	private ILessonOnlineUserDao lessonOnlineUserDao;
	@Resource(name = "lessonOnlineStatSender")
	private MQSender lessonOnlineStatSender;

	@Override
	public void process(LessonOnlineUserMsg msg) {
		if (msg == null || msg.getLessonId() == null || CollectionUtils.isEmpty(msg.getUsers())) {
			return;
		}
		Date ts = msg.getTs();
		Long schoolId = msg.getSchoolId();
		long stuCount = 0;

		for (UserItem item : msg.getUsers()) {
			Long roleId = item.getRoleId();
			if (roleId.equals(RoleCst.STUDENT)) {
				stuCount++;
			}

			LessonOnlineUser result = new LessonOnlineUser();
			result.setSchoolId(schoolId);
			result.setTs(ts);
			result.setUserId(item.getUserId());
			result.setRoleId(roleId);
			result.setD(item.getD());
			lessonOnlineUserDao.save(result);
		}

		CourseOnlineMsg stat = new CourseOnlineMsg();
		stat.setTs(ts);
		stat.setCsId(msg.getLessonId());
		stat.setSchoolId(schoolId);
		stat.setStuCount(stuCount);
		lessonOnlineStatSender.send(stat);
	}

}
