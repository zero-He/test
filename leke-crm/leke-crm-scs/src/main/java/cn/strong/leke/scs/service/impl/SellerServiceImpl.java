package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.model.user.Role;
import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.dao.master.SellerMasterDao;
import cn.strong.leke.scs.dao.slave.SellerDao;
import cn.strong.leke.scs.model.Seller;
import cn.strong.leke.scs.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger logger = LoggerFactory.getLogger(SellerService.class);

	@Resource
	private SellerDao sellerDao;
	@Resource
	private SellerMasterDao sellerMasterDao;

	@Override
	public void executeSellerCreatedWithTx(List<UserMQ> userList) {
		for (UserMQ user : userList) {
			// 判断用户是否为销售
			boolean isSeller = false;
			if (CollectionUtils.isNotEmpty(user.getRoles())) {
				for (Role role : user.getRoles()) {
					if (RoleCst.SELLER.equals(role.getId())) {
						isSeller = true;
					}
				}
			}
			// 如果是销售执行业务
			if (isSeller) {
				this.sellerMasterDao.insertSeller(user.getUserId(), user.getUserName());
			}
		}
	}

	@Override
	public void executeResetMonthAmountWithTx() {
		logger.info("开始执行销售月佣金累计清零任务。");
		int month = new DateTime().getMonthOfYear();
		if (month == 1) {
			this.sellerMasterDao.resetSellerYearCommAmount();
		} else {
			this.sellerMasterDao.resetSellerMonthCommAmount();
		}
		logger.info("结束执行销售月佣金累计清零任务。");
	}

	@Override
	public Seller getSellerBySellerId(Long sellerId) {
		return this.sellerDao.getSellerBySellerId(sellerId);
	}
}
