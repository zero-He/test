package cn.strong.leke.scs.service;

import java.util.List;

import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.model.Seller;

/**
 * 销售佣金累计服务。
 * @author  andy
 * @created 2015年6月15日 下午6:48:09
 * @since   v1.0.0
 */
public interface SellerService {

	/**
	 * 处理销售创建消息。
	 * @param user
	 */
	public void executeSellerCreatedWithTx(List<UserMQ> userList);

	/**
	 * 执行佣金累计清零任务。
	 */
	public void executeResetMonthAmountWithTx();

	/**
	 * 根据销售ID获取销售信息。
	 * @param sellerId 销售ID
	 * @return
	 */
	public Seller getSellerBySellerId(Long sellerId);
}
