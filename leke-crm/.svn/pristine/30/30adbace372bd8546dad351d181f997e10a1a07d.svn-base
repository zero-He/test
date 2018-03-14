package cn.strong.leke.scs.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.crm.SaleRelationMQ;
import cn.strong.leke.scs.service.CustomerService;

/**
 * 销售关系消息监听。
 * @author  andy
 * @created 2015年6月15日 下午3:24:52
 * @since   v1.0.0
 */
public class SaleRelationListener extends AbstractRabbitMQListener {

	@Resource
	private CustomerService saleRelationService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.saleRelationService.bindSaleRelationWithTx((SaleRelationMQ) object);
	}
}
