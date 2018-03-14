package cn.strong.leke.scs.util;

import java.util.Calendar;
import java.util.Date;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.scs.model.rule.PointCommRule;

public class CommRuleUtils {

	/**
	 * 解析佣金规则。
	 * @param detail
	 * @return
	 */
	public static PointCommRule parsePointCommRuleDetail(String detail) {
		return JsonUtils.fromJSON(detail, PointCommRule.class);
	}

	/**
	 * 获取交易时间相对首次消费时间是第几年。
	 * 如果首次消息费时间为空，默认为第一年。
	 * @param tradeTime 交易时间
	 * @param consumeTime 首次消费时间
	 * @return
	 */
	public static int getSeveralYear(Date tradeTime, Date consumeTime) {
		int year = 1;
		if (consumeTime != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(consumeTime);
			while (true) {
				calendar.add(Calendar.YEAR, 1);
				if (calendar.getTimeInMillis() > tradeTime.getTime()) {
					break;
				}
				year++;
			}
		}
		return year;
	}
}
