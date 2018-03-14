package cn.strong.leke.scs.dao.slave;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.Slave;
import cn.strong.leke.scs.model.Commission;
import cn.strong.leke.scs.model.query.MonthCommDtlQuery;
import cn.strong.leke.scs.model.query.MonthCommQuery;
import cn.strong.leke.scs.model.view.MonthComm;
import cn.strong.leke.scs.model.view.MonthCommDtl;

@Slave
public interface CommissionDao {

	/**
	 * 根据客户ID查询某个月的佣金记录。
	 * @param customerId 客户ID
	 * @param month 月份
	 * @return
	 */
	public List<Commission> findCommissionByCondition(@Param("customerId") Long customerId, @Param("month") Date month);

	/**
	 * 查询某个月的销售佣金列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<MonthComm> findMonthCommByQuery(MonthCommQuery query, Page page);

	/**
	 * 查询某个月销售佣金汇总。
	 * @param query 条件
	 * @return
	 */
	public BigDecimal getTotalMonthCommByQuery(MonthCommQuery query);

	/**
	 * 查询某个月的销售佣金明细列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<MonthCommDtl> findMonthCommDtlByQuery(MonthCommDtlQuery query, Page page);

	/**
	 * 查询某个月销售佣金汇总。
	 * @param query 条件
	 * @return
	 */
	public BigDecimal getTotalMonthCommDtlByQuery(MonthCommDtlQuery query);
}
