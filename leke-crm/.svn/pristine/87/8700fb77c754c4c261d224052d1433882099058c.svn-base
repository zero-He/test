package cn.strong.leke.scs.listener;

import java.util.List;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.service.SellerService;

/**
 * 用户创建消息处理。
 * @author  andy
 * @created 2015年6月22日 上午9:41:46
 * @since   v1.0.0
 */
public class UserListener extends AbstractRabbitMQListener {

	@Resource
	private SellerService sellerService;

	@Override
	public void handleMessage(Object object) throws Exception {
		@SuppressWarnings("unchecked")
		List<UserMQ> userList = (List<UserMQ>) object;
		this.sellerService.executeSellerCreatedWithTx(userList);
	}
}
