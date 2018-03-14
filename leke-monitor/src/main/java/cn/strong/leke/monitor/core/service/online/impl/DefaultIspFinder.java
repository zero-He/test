/**
 * 
 */
package cn.strong.leke.monitor.core.service.online.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.strong.leke.core.ip.IPv4;
import cn.strong.leke.core.ip.IPv4Loc;
import cn.strong.leke.core.ip.IPv4Locator;
import cn.strong.leke.monitor.core.service.online.IspFinder;

/**
 * 默认网络运营商查找
 * 
 * @author liulongbiao
 *
 */
@Component
public class DefaultIspFinder implements IspFinder {
	@Resource
	private IPv4Locator ipv4Locator;

	@Override
	public String getIsp(String ip) {
		if (!StringUtils.hasText(ip)) {
			return null;
		}
		try {
			IPv4 ipv4 = IPv4.parse(ip);
			IPv4Loc loc = ipv4Locator.locate(ipv4);
			return loc.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
