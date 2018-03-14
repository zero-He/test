package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.slave.AccountDao;
import cn.strong.leke.scs.model.query.PointAccountQuery;
import cn.strong.leke.scs.model.view.PointAccount;
import cn.strong.leke.scs.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountDao pointAccountDao;

	@Override
	public List<PointAccount> findPointAccountByQuery(PointAccountQuery query, Page page) {
		return this.pointAccountDao.findPointAccountByQuery(query, page);
	}

}
