/**
 * 
 */
package cn.strong.leke.monitor.core.service.online.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.model.base.School;
import cn.strong.leke.monitor.core.service.online.ISchoolOnlineUserStatService;
import cn.strong.leke.monitor.mongo.online.model.OnlineUserSchoolStat;
import cn.strong.leke.monitor.mongo.online.service.IDeviceOnlineUserDao;
import cn.strong.leke.monitor.mongo.online.service.ILessonOnlineUserDao;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserDao;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserSchoolStatDao;
import cn.strong.leke.monitor.mongo.stat.dao.ISchoolUserStatDao;
import cn.strong.leke.monitor.mongo.stat.model.SchoolUserStat;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.model.user.SchoolAreaRemote;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolOnlineUserStatService implements ISchoolOnlineUserStatService {
	private static final Logger LOG = LoggerFactory.getLogger(SchoolOnlineUserStatService.class);
	@Resource
	private IOnlineUserSchoolStatDao onlineUserSchoolStatDao;
	@Resource
	private IOnlineUserDao onlineUserDao;
	@Resource
	private ILessonOnlineUserDao lessonOnlineUserDao;
	@Resource
	private IDeviceOnlineUserDao deviceOnlineUserDao;
	@Resource
	private ISchoolUserStatDao schoolUserStatDao;
	@Resource(name = "monitorExecutor")
	private ExecutorService monitorExecutor;

	@Override
	public void updateSchoolStats() {
		final Date ts = StatUtils.getCurrentOnlineTs();
		Set<Long> schoolIds = findSchoolIdsNeedUpdate(ts);
		if (schoolIds.isEmpty()) {
			LOG.info("没有学校需更新在线用户统计");
			return;
		}
		int size = schoolIds.size();
		CountDownLatch latch = new CountDownLatch(schoolIds.size());
		LOG.info("开始给 {} 所学校更新在线用户统计", size);
		try {
			schoolIds.forEach(schoolId -> {
				CompletableFuture.runAsync(() -> {
					try {
						updateSchoolStat(schoolId, ts);
					} catch(Exception e) {
						LOG.error("更新学校 {} 在线用户统计任务失败", schoolId, e);
					} finally {
						latch.countDown();
					}
				}, monitorExecutor);
			});
			latch.await();
			LOG.info("更新学校在线用户统计任务完成");
		} catch (Exception e) {
			LOG.error("更新学校在线用户统计任务失败", e);
		}
	}

	private Set<Long> findSchoolIdsNeedUpdate(Date ts) {
		Set<Long> schoolIds = new HashSet<>();
		schoolIds.addAll(onlineUserSchoolStatDao.findSchoolsHasOnline());
		schoolIds.addAll(onlineUserDao.getActiveSchoolIdsSince(ts));
		schoolIds.addAll(lessonOnlineUserDao.getActiveSchoolIdsSince(ts));
		schoolIds.addAll(deviceOnlineUserDao.getActiveSchoolIdsSince(ts));
		schoolIds.addAll(schoolUserStatDao.getActiveSchoolIdsSince(ts));
		return schoolIds;
	}

	private void updateSchoolStat(Long schoolId, Date ts) {
		if(schoolId == null) {
			return;
		}
		LOG.debug("更新学校 {} 在线用户统计", schoolId);
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		if (school == null) {
			onlineUserSchoolStatDao.removeById(schoolId);
			return;
		}
		OnlineUserSchoolStat stat = onlineUserSchoolStatDao.getById(schoolId);
		if (stat == null) { // 初始化学校
			stat = new OnlineUserSchoolStat();
		}
		
		stat.setSchoolId(schoolId);
		stat.setSchoolName(school.getName());
		stat.setSchoolCode(school.getCode());
		stat.setSchoolNature(school.getSchoolNature());
		SchoolAreaRemote sa = SchoolContext.getSchoolArea(schoolId);
		if (sa != null) {
			stat.setRegionIds(toList(sa.getRegionIds()));
		}

		// 注册用户数
		SchoolUserStat reg = schoolUserStatDao.getById(schoolId);
		stat.setRegistered(reg == null ? 0 : reg.getRegistered());

		// 在线用户数
		Set<Long> lesson = lessonOnlineUserDao.findActiveUserIdsSince(ts, schoolId);
		Set<Long> web = onlineUserDao.findActiveUserIdsSince(ts, schoolId);
		Set<Long> device = deviceOnlineUserDao.findActiveUserIdsSince(ts, schoolId);
		Set<Long> platform = new HashSet<>(web);
		platform.addAll(device);
		stat.setPlatform(platform.size());
		stat.setLesson(lesson.size());
		stat.setWeb(web.size());
		stat.setDevice(device.size());

		stat.setModifiedOn(new Date());

		// 保存
		onlineUserSchoolStatDao.save(stat);
	}

	private static <T> List<T> toList(Collection<T> coll) {
		return (coll == null || coll.isEmpty()) ? Collections.emptyList() : new ArrayList<>(coll);
	}

}
