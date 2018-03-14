/**
 * 
 */
package cn.strong.leke.monitor.core.service.stat.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.monitor.core.service.stat.ISchoolUserStatService;
import cn.strong.leke.monitor.db.tutor.mapper.IUserRoleSchoolMapper;
import cn.strong.leke.monitor.mongo.stat.dao.ISchoolUserStatDao;
import cn.strong.leke.monitor.mongo.stat.model.SchoolUserStat;

/**
 * @author liulongbiao
 *
 */
@Service
public class SchoolUserStatService implements ISchoolUserStatService {
	private static final Logger LOG = LoggerFactory.getLogger(ISchoolUserStatService.class);
	@Resource
	private IUserRoleSchoolMapper userRoleSchoolMapper;
	@Resource
	private ISchoolUserStatDao schoolUserStatDao;
	@Resource(name = "monitorExecutor")
	private ExecutorService monitorExecutor;

	@Override
	public void updateChangedSince(Date day) {
		List<Long> schoolIds = userRoleSchoolMapper.findUpdatedSchoolIds(day);
		updateSchoolUserStats(schoolIds);
	}

	private void updateSchoolUserStats(List<Long> schoolIds) {
		if (CollectionUtils.isEmpty(schoolIds)) {
			return;
		}
		CountDownLatch latch = new CountDownLatch(schoolIds.size());
		LOG.info("开始给 {} 所学校更新注册用户统计", schoolIds.size());
		try {
			schoolIds.forEach(schoolId -> {
				CompletableFuture.runAsync(() -> {
					try {
						updateSchoolUserStat(schoolId);
					} catch (Exception e) {
						LOG.error("更新学校 {} 注册用户统计任务失败", schoolId, e);
					} finally {
						latch.countDown();
					}
				}, monitorExecutor);
			});
			latch.await();
			LOG.info("更新学校注册用户统计任务完成");
		} catch (Exception e) {
			LOG.error("更新学校注册用户统计任务失败", e);
		}
	}

	private void updateSchoolUserStat(Long schoolId) {
		LOG.debug("update user stat for school {}", schoolId);
		int registered = userRoleSchoolMapper.countUsersBySchool(schoolId);
		int students = userRoleSchoolMapper.countUsersByRole(schoolId, RoleCst.STUDENT);
		int teachers = userRoleSchoolMapper.countUsersByRole(schoolId, RoleCst.TEACHER);
		SchoolUserStat stat = new SchoolUserStat();
		stat.setSchoolId(schoolId);
		stat.setRegistered(registered);
		stat.setStudents(students);
		stat.setTeachers(teachers);
		stat.setModifiedOn(new Date());
		schoolUserStatDao.save(stat);
	}

	@Override
	public void updateAll() {
		List<Long> schoolIds = userRoleSchoolMapper.findAllSchoolIds();
		updateSchoolUserStats(schoolIds);
	}

}
