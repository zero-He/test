/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.ObjectMapperUtils;
import cn.strong.leke.monitor.core.model.AccessDailyDto;
import cn.strong.leke.monitor.core.model.AccessRangeStatDto;
import cn.strong.leke.monitor.core.service.IAccessService;
import cn.strong.leke.monitor.listener.model.AccessMsg;
import cn.strong.leke.monitor.mongo.model.AccessDaily;
import cn.strong.leke.monitor.mongo.model.AccessRecord;
import cn.strong.leke.monitor.mongo.model.AccessUrl;
import cn.strong.leke.monitor.mongo.model.query.AccessDailyQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessRangeQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;
import cn.strong.leke.monitor.mongo.model.updates.AccessDailyDelta;
import cn.strong.leke.monitor.mongo.service.IAccessDailyService;
import cn.strong.leke.monitor.mongo.service.IAccessRecordService;
import cn.strong.leke.monitor.mongo.service.IAccessUrlService;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class AccessService implements IAccessService {

	@Autowired
	private IAccessRecordService recordService;
	@Autowired
	private IAccessDailyService dailyService;
	@Autowired
	private IAccessUrlService urlService;

	@Override
	public void add(AccessMsg msg) {
		AccessRecord record = new AccessRecord();
		record.setServerName(msg.getServerName());
		record.setServletPath(msg.getServletPath());
		record.setUserId(msg.getUserId());
		record.setSchoolId(msg.getSchoolId());
		record.setTs(new Date(msg.getTime()));
		record.setExecuteTime(msg.getExecuteTime());
		record.setId(msg.getIp());
		record.setParams(msg.getParams());

		recordService.add(record);

		Date date = new Date(msg.getTime());
		int day = StatUtils.ofDay(date);
		AccessDailyDelta delta = new AccessDailyDelta();
		delta.setDay(day);
		delta.setServerName(msg.getServerName());
		delta.setServletPath(msg.getServletPath());
		delta.setExecuteTime(msg.getExecuteTime());
		dailyService.update(delta);
	}

	@Override
	public List<AccessDailyDto> findAccessDailyStats(AccessDailyQuery query) {
		if (query == null || query.getDay() == null) {
			return Collections.emptyList();
		}
		List<AccessDaily> list = dailyService.findAccessDailys(query);
		Map<String, AccessUrl> urlMap = findUrlMap();

		return list.stream().map(ad -> {
			return toAccessDailyDto(ad, urlMap);
		}).collect(Collectors.toList());
	}

	private Map<String, AccessUrl> findUrlMap() {
		List<AccessUrl> urls = urlService.findAll();
		return urls.stream().collect(Collectors.toMap(au -> {
					return au.toUrl();
				}, Function.identity()));
	}

	private static AccessDailyDto toAccessDailyDto(AccessDaily ad,
			Map<String, AccessUrl> urlMap) {
		AccessDailyDto stat = ObjectMapperUtils.convertValue(ad,
				AccessDailyDto.class);
		AccessUrl au = urlMap.get(ad.toUrl());
		if (au != null) {
			stat.setName(au.getName());
			stat.setType(au.getType());
		}
		return stat;
	}

	@Override
	public List<AccessRangeStatDto> findAccessRangeStats(AccessRangeQuery query) {
		List<AccessStat> list = dailyService.findAccessRangeStats(query);
		Map<String, AccessUrl> urlMap = findUrlMap();
		return list.stream().map(ars -> {
			return toAccessRangeStatDto(ars, urlMap);
		}).collect(Collectors.toList());
	}

	private static AccessRangeStatDto toAccessRangeStatDto(AccessStat ars,
			Map<String, AccessUrl> urlMap) {
		AccessRangeStatDto stat = ObjectMapperUtils.convertValue(ars, AccessRangeStatDto.class);
		AccessUrl au = urlMap.get(ars.toUrl());
		if (au != null) {
			stat.setName(au.getName());
			stat.setType(au.getType());
		}
		return stat;
	}

}
