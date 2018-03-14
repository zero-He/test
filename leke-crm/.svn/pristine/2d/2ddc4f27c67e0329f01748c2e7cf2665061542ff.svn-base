package cn.strong.leke.scs.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.crm.PointRechangeMQ;
import cn.strong.leke.scs.service.CommissionService;

/**
 * 课点充值佣金消息监听。
 * @author  andy
 * @created 2015年6月15日 下午3:07:57
 * @since   v1.0.0
 */
public class PointRechangeListener extends AbstractRabbitMQListener {

	@Resource
	private CommissionService commissionService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.commissionService.executePointRechangeWithTx((PointRechangeMQ) object);
	}
}
