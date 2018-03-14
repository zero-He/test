package cn.strong.leke.scs.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.service.CustomerService;

public class UpdateUserListener extends AbstractRabbitMQListener {

	@Resource
	private CustomerService customerService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.customerService.executeUserUpdatedWithTx((UserMQ) object);
	}
}
