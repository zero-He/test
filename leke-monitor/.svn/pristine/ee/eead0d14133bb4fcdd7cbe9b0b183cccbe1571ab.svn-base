/**
 * 
 */
package cn.strong.leke.monitor.core.service.online.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BrowserTypeUtils;
import cn.strong.leke.common.utils.BrowserTypeUtils.BrowseType;
import cn.strong.leke.common.utils.BrowserTypeUtils.OSType;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.user.User;
import cn.strong.leke.monitor.core.model.DeviceHeartbeat;
import cn.strong.leke.monitor.core.model.OnlineHeartbeat;
import cn.strong.leke.monitor.core.service.online.IDeviceOnlineUserService;
import cn.strong.leke.monitor.core.service.online.IWebOnlineUserService;
import cn.strong.leke.monitor.core.service.online.IspFinder;
import cn.strong.leke.monitor.mongo.online.model.DeviceCst;
import cn.strong.leke.monitor.mongo.online.model.OnlineDaily;
import cn.strong.leke.monitor.mongo.online.model.OnlineSnapshot;
import cn.strong.leke.monitor.mongo.online.model.OnlineUser;
import cn.strong.leke.monitor.mongo.online.model.query.OnlineUserQuery;
import cn.strong.leke.monitor.mongo.online.service.IOnlineDailyDao;
import cn.strong.leke.monitor.mongo.online.service.IOnlineSnapshotDao;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserDao;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class WebOnlineUserService implements IWebOnlineUserService {
	private static final Logger LOG = LoggerFactory.getLogger(WebOnlineUserService.class);
	@Resource
	private IspFinder ispFinder;
	@Resource
	private IOnlineUserDao onlineUserDao;
	@Resource
	private IOnlineSnapshotDao snapshotDao;
	@Resource
	private IOnlineDailyDao dailyDao;
	@Resource(name = "monitorExecutor")
	private ExecutorService monitorExecutor;
	@Resource
	private IDeviceOnlineUserService deviceOnlineUserService;

	@Override
	public void saveHeartbeat(OnlineHeartbeat hb) {
		CompletableFuture.runAsync(() -> {
			OnlineUser ou = toOnlineUser(hb);
			onlineUserDao.save(ou);

			DeviceHeartbeat dhb = toDeviceHeartbeat(hb.getTicket(), ou);
			deviceOnlineUserService.saveHeartbeat(dhb);
		}, monitorExecutor).whenComplete((t, ex) -> {
			if (ex != null) {
				LOG.error("保存网页心跳失败", ex);
			}
		});
	}

	private DeviceHeartbeat toDeviceHeartbeat(String ticket, OnlineUser ou) {
		DeviceHeartbeat dhb = new DeviceHeartbeat();
		dhb.setTicket(ticket);
		dhb.setD(DeviceCst.D_WEB);
		dhb.setModel(ou.getNavigate());
		dhb.setOs(ou.getOs());
		dhb.setIp(ou.getIp());
		return dhb;
	}

	private OnlineUser toOnlineUser(OnlineHeartbeat ohb) {
		User user = ohb.getUser();
		String userAgent = ohb.getUserAgent();
		String ip = ohb.getIp();
		School school = user.getCurrentSchool();

		OnlineUser hb = new OnlineUser();
		hb.setTs(new Date());
		hb.setUserId(user.getId());
		hb.setLoginName(user.getLoginName());
		hb.setOldLoginName(user.getOldLoginName());
		hb.setUserName(user.getUserName());
		if (school != null) {
			hb.setSchoolId(school.getId());
			hb.setSchoolName(school.getName());
		}

		hb.setUserAgent(userAgent);
		BrowseType bt = BrowserTypeUtils.getBrowseType(userAgent);
		hb.setNavigate(bt.type);
		hb.setVersion(bt.version);
		OSType ot = BrowserTypeUtils.getOSType(userAgent);
		hb.setOs(ot.toString());

		hb.setIp(ip);
		hb.setNetworkOperator(ispFinder.getIsp(ip));
		return hb;
	}

	@Override
	public long getCurrentOnlineUserCount() {
		return onlineUserDao.getActiveUserCountSince(StatUtils.getCurrentOnlineTs());
	}

	@Override
	public List<OnlineUser> queryOnlineUsers(OnlineUserQuery query, Page page) {
		query.setTs(StatUtils.getCurrentOnlineTs());
		return onlineUserDao.queryOnlineUsers(query, page);
	}

	@Override
	public void updateSnapshot() {
		long count = getCurrentOnlineUserCount();
		Date ts = StatUtils.getSnapshotTs();
		OnlineSnapshot snapshot = new OnlineSnapshot();
		snapshot.setCount(count);
		snapshot.setTs(ts);
		snapshotDao.save(snapshot);
	}

	@Override
	public void updateDaily() {
		Date yestoday = LocalDate.now().minusDays(1).toDate();
		long total = onlineUserDao.getActiveUserCountSince(yestoday);
		long max = snapshotDao.getMaxCountOfDay(yestoday);

		OnlineDaily daily = new OnlineDaily();
		daily.setDay(StatUtils.ofDay(yestoday));
		daily.setTotal(total);
		daily.setMax(max);
		dailyDao.save(daily);
	}

}
