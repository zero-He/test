/**
 * 
 */
package cn.strong.leke.monitor.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.model.user.SchoolAreaRemote;

/**
 * 受影响学校时间点收集器
 * 
 * @author liulongbiao
 *
 */
public class SchoolSnapshotCollector {
	private final Map<Long, AffectedSnapshot> store = new HashMap<>();
	private final Map<Long, SchoolAreaRemote> saCache = new HashMap<>();

	/**
	 * 添加学校时间点
	 * 
	 * @param schoolId
	 * @param time
	 */
	public void add(Long schoolId, Date time) {
		AffectedSnapshot ast = ensureGet(schoolId);
		ast.add(time);
	}

	/**
	 * 添加学校时间段
	 * 
	 * @param schoolId
	 * @param startTime
	 * @param endTime
	 */
	public void add(Long schoolId, Date startTime, Date endTime) {
		AffectedSnapshot ast = ensureGet(schoolId);
		ast.add(startTime, endTime);
	}

	/**
	 * 添加单课相关的学校时间点
	 * 
	 * @param cs
	 */
	public void add(CourseSingle cs) {
		if (cs == null) {
			return;
		}
		add(cs.getSchoolId(), cs.getStartTime(), cs.getEndTime());
	}

	private AffectedSnapshot ensureGet(Long schoolId) {
		AffectedSnapshot ast = store.get(schoolId);
		if (ast == null) {
			ast = new AffectedSnapshot();
			store.put(schoolId, ast);
		}
		return ast;
	}

	public Map<Long, AffectedSnapshot> getStore() {
		return store;
	}

	/**
	 * 获取学校区域信息
	 * 
	 * @param schoolId
	 * @return
	 */
	public SchoolAreaRemote cacheGetSchoolArea(Long schoolId) {
		return saCache.computeIfAbsent(schoolId,
				SchoolSnapshotCollector::getSchoolArea);
	}

	/**
	 * 获取学校区域信息
	 * 
	 * @param schoolId
	 * @return
	 */
	public static SchoolAreaRemote getSchoolArea(Long schoolId) {
		return SchoolContext.getSchoolArea(schoolId);
	}

	/**
	 * 受影响时间点
	 * 
	 * @author liulongbiao
	 *
	 */
	public static class AffectedSnapshot {
		private final Set<Long> times = new HashSet<>();
		private final Set<Integer> days = new HashSet<>();

		/**
		 * 添加快照时间
		 * 
		 * @param time
		 */
		public void add(Date time) {
			Date ts = StatUtils.getSnapshotTs(time);
			int day = StatUtils.ofDay(ts);
			times.add(ts.getTime());
			days.add(day);
		}

		/**
		 * 添加快照时段
		 * 
		 * @param startTime
		 * @param endTime
		 */
		public void add(Date startTime, Date endTime) {
			Date startTs = StatUtils.getSnapshotTs(startTime);
			long start = startTs.getTime();
			int startDay = StatUtils.ofDay(startTs);
			Date endTs = StatUtils.getSnapshotTs(endTime);
			long end = endTs.getTime();
			int endDay = StatUtils.ofDay(endTs);

			for (long ts = start; ts <= end; ts += StatUtils.TS_MIN_5) {
				times.add(ts);
			}
			for (int day = startDay; day <= endDay; day++) {
				days.add(day);
			}
		}

		public Set<Long> getTimes() {
			return times;
		}

		public Set<Integer> getDays() {
			return days;
		}

	}
}
