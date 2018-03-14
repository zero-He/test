package cn.strong.leke.scs.dao.slave;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.Slave;
import cn.strong.leke.scs.model.query.PointAccountQuery;
import cn.strong.leke.scs.model.view.PointAccount;

@Slave
public interface AccountDao {
	
	/**
	 * 分页查询课点账户列表
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<PointAccount> findPointAccountByQuery(PointAccountQuery query, Page page);
}
