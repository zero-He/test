package cn.strong.leke.scs.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.model.query.PointAccountQuery;
import cn.strong.leke.scs.model.view.PointAccount;

/**
 * 账户服务。
 * @author  andy
 * @created 2015年6月24日 下午4:04:21
 * @since   v1.0.0
 */
public interface AccountService {

	/**
	 * 分页查询课点账户列表
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<PointAccount> findPointAccountByQuery(PointAccountQuery query, Page page);

}
