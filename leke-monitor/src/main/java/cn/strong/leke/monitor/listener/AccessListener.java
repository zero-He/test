/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.common.utils.ObjectMapperUtils;
import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.monitor.core.service.IAccessService;
import cn.strong.leke.monitor.listener.model.AccessMsg;

/**
 * 
 * 描述:模块点击监听端
 * 
 * @author WQB
 * @created 2014-8-13 下午8:09:10
 * @since v1.0.0
 */
public class AccessListener extends AbstractRabbitMQListener {
	private static Logger LOG = LoggerFactory.getLogger(AccessListener.class);

	@Autowired
	private IAccessService accessService;

	@Override
	public void handleMessage(Object obj) {
		try {
			AccessMsg access = ObjectMapperUtils.convertValue(obj, AccessMsg.class);
			accessService.add(access);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
